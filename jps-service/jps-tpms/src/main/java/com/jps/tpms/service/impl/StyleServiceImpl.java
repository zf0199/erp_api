package com.jps.tpms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.tpms.service.StyleService;
import com.jps.tpms.api.domain.StyleDo;
import com.jps.tpms.api.domain.StyleMaterialDo;
import com.jps.tpms.api.domain.StylePartDo;
import com.jps.tpms.api.domain.StyleProcedureDo;
import com.jps.tpms.api.dto.StyleDto;
import com.jps.tpms.mapper.StyleMapper;
import com.jps.tpms.mapper.StyleMaterialMapper;
import com.jps.tpms.mapper.StylePartMapper;
import com.jps.tpms.mapper.StyleProcedureMapper;
import com.jps.tpms.service.StyleMaterialService;
import com.jps.tpms.service.StylePartService;
import com.jps.tpms.service.StyleProcedureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * @className: StyleServiceImpl
 * @author: zf
 * @date: 2025/3/28 14:38
 * @version: 1.0
 * @description: 款类service
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class StyleServiceImpl extends ServiceImpl<StyleMapper, StyleDo> implements StyleService {

	private final StyleProcedureService styleProcedureService;

	private final StyleMaterialService styleMaterialService;

	private final StylePartService stylePartService;

	private final StyleProcedureMapper styleProcedureMapper;

	private final StyleMaterialMapper styleMaterialMapper;

	private final StylePartMapper stylePartMapper;

	/**
	 * 新增款类
	 *
	 * @param styleDto 新增参数
	 */
	@Override
	public void add(StyleDto styleDto) {
		StyleDo style = new StyleDo();
		BeanUtils.copyProperties(styleDto, style);
		boolean save = this.save(style);
		Long id = style.getId();

		Optional.ofNullable(styleDto.getStyleProcedures())
				.ifPresent(e -> {
					e.forEach(t -> t.setStyleId(id));
					styleProcedureService.saveBatch(e);
				});

		Optional.ofNullable(styleDto.getStyleParts())
				.ifPresent(e -> {
					e.forEach(t -> t.setStyleId(id));
					stylePartService.saveBatch(e);
				});

		Optional.ofNullable(styleDto.getStyleMaterial())
				.ifPresent(e -> {
					e.forEach(t -> t.setStyleId(id));
					styleMaterialService.saveBatch(e);
				});
	}


	/**
	 * 删除所有
	 *
	 * @param ids id集合
	 */
	@Override
	public void removeAll(Long[] ids) {
		List<Long> listIds = Arrays.asList(ids);
		this.removeBatchByIds(listIds);
		LambdaQueryWrapper<StyleProcedureDo> lambdaQueryWrapper = Wrappers.lambdaQuery();
		styleProcedureMapper.delete(lambdaQueryWrapper.in(StyleProcedureDo::getStyleId, listIds));
		LambdaQueryWrapper<StyleMaterialDo> styleMaterialWrapper = Wrappers.lambdaQuery();
		styleMaterialMapper.delete(styleMaterialWrapper.in(StyleMaterialDo::getStyleId, listIds));
		LambdaQueryWrapper<StylePartDo> stylePartWrapper = Wrappers.lambdaQuery();
		stylePartMapper.delete(stylePartWrapper.in(StylePartDo::getStyleId, listIds));
	}

	@Override
	public void update(StyleDto styleDto) {
		StyleDo styleDo = new StyleDo();
		BeanUtils.copyProperties(styleDto, styleDo);
		this.updateById(styleDo);

		List<StyleProcedureDo> styleProcedures = styleDto.getStyleProcedures();
		LambdaQueryWrapper<StyleProcedureDo> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(StyleProcedureDo::getStyleId, styleDto.getId());
		styleProcedureService.remove(wrapper);

		styleProcedures.forEach(e -> {
//			e.setId(IdWorker.getId());
			e.setStyleId(styleDto.getId());
		});

		// TAG update :  插入或更新方法 报主键重复
		styleProcedureService.saveOrUpdateBatch(styleProcedures);

		List<StylePartDo> styleParts = styleDto.getStyleParts();

		LambdaQueryWrapper<StylePartDo> wrapper1 = Wrappers.lambdaQuery();
		wrapper1.eq(StylePartDo::getStyleId, styleDto.getId());
		stylePartService.remove(wrapper1);
		styleParts.forEach(e -> {
//			e.setId(IdWorker.getId());
			e.setStyleId(styleDto.getId());
		});
		stylePartService.saveBatch(styleParts);


		List<StyleMaterialDo> styleMaterial = styleDto.getStyleMaterial();


		LambdaQueryWrapper<StyleMaterialDo> wrapper2 = Wrappers.lambdaQuery();
		wrapper2.eq(StyleMaterialDo::getStyleId, styleDto.getId());
		styleMaterialService.remove(wrapper2);

		styleMaterial.forEach(e -> {
//			e.setId(IdWorker.getId());
			e.setStyleId(styleDto.getId());
		});
		styleMaterialService.saveBatch(styleMaterial);


	}

	@Override
	public StyleDto getDetails(StyleDo styleDo) {
		Long id = styleDo.getId();

		StyleDo byId = this.getById(id);
		StyleDto styleDto = new StyleDto();
		BeanUtils.copyProperties(byId, styleDto);

		LambdaQueryWrapper<StyleMaterialDo> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(StyleMaterialDo::getStyleId, id);
		List<StyleMaterialDo> list = styleMaterialService.list(wrapper);

		LambdaQueryWrapper<StylePartDo> wrapper1 = Wrappers.lambdaQuery();
		wrapper1.eq(StylePartDo::getStyleId, id);
		List<StylePartDo> list1 = stylePartService.list(wrapper1);

		LambdaQueryWrapper<StyleProcedureDo> wrapper2 = Wrappers.lambdaQuery();
		wrapper2.eq(StyleProcedureDo::getStyleId, id);
		List<StyleProcedureDo> list2 = styleProcedureService.list(wrapper2);
		styleDto.setStyleProcedures(list2);

		styleDto.setStyleMaterial(list);
		styleDto.setStyleParts(list1);
		return styleDto;
	}
}
