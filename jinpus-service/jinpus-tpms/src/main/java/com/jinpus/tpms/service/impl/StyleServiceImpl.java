package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.StyleDo;
import com.jinpus.tpms.api.domain.StyleMaterialDo;
import com.jinpus.tpms.api.domain.StylePartDo;
import com.jinpus.tpms.api.domain.StyleProcedureDo;
import com.jinpus.tpms.api.dto.StyleDto;
import com.jinpus.tpms.mapper.StyleMapper;
import com.jinpus.tpms.mapper.StyleMaterialMapper;
import com.jinpus.tpms.mapper.StylePartMapper;
import com.jinpus.tpms.mapper.StyleProcedureMapper;
import com.jinpus.tpms.service.StyleMaterialService;
import com.jinpus.tpms.service.StylePartService;
import com.jinpus.tpms.service.StyleProcedureService;
import com.jinpus.tpms.service.StyleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
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
	 *    新增款类
	 * @param styleDto 新增参数
	 */
	@Override
	public void add(StyleDto styleDto)  {
		StyleDo style = new StyleDo();
		BeanUtils.copyProperties(style,styleDto);
		boolean save = this.save(style);
		Long id = style.getId();
		Optional.ofNullable( styleDto.getStyleProcedures()).ifPresent(e -> {
			e.forEach(t -> t.setStyleId(id));
			styleProcedureService.saveBatch(e);
		});
		Optional.ofNullable( styleDto.getStyleParts()).ifPresent(e -> {
			e.forEach(t -> t.setStyleId(id));
			stylePartService.saveBatch(e);
		});
		Optional.ofNullable( styleDto.getStyleMaterial()).ifPresent(e -> {
			e.forEach(t -> t.setStyleId(id));
			styleMaterialService.saveBatch(e);
		});
	}


	/**
	 *   删除所有
	 * @param ids id集合
	 */
	@Override
	public void removeAll(Long[] ids) {
		List<Long> listIds = Arrays.asList(ids);
		this.removeBatchByIds(listIds);
		LambdaQueryWrapper<StyleProcedureDo> lambdaQueryWrapper = Wrappers.lambdaQuery();
		styleProcedureMapper.delete(lambdaQueryWrapper.in(StyleProcedureDo::getStyleId,listIds));
		LambdaQueryWrapper<StyleMaterialDo> styleMaterialWrapper = Wrappers.lambdaQuery();
		styleMaterialMapper.delete(styleMaterialWrapper.in(StyleMaterialDo::getStyleId,listIds));
		LambdaQueryWrapper<StylePartDo> stylePartWrapper = Wrappers.lambdaQuery();
		stylePartMapper.delete(stylePartWrapper.in(StylePartDo::getStyleId,listIds));
	}
}
