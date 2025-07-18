package com.jps.tpms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jps.tpms.api.domain.StyleDo;
import com.jps.tpms.api.dto.StyleDto;

public interface StyleService extends IService<StyleDo> {


	/**
	 *   新增款类
	 * @param styleDto 新增参数
	 */
	void add(StyleDto styleDto);


	void removeAll(Long[] ids);

	void update(StyleDto styleDto);

	StyleDto getDetails(StyleDo styleDo);

}
