package com.jps.tpms.api.dto;

import com.jps.tpms.api.domain.StyleDo;
import com.jps.tpms.api.domain.StyleMaterialDo;
import com.jps.tpms.api.domain.StylePartDo;
import com.jps.tpms.api.domain.StyleProcedureDo;
import lombok.Data;

import java.util.List;

/**
 * @className: StyleDto
 * @author: zf
 * @date: 2025/3/27 15:35
 * @version: 1.0
 * @description:
 */
@Data
public class StyleDto extends StyleDo {

	/**
	 *  款类工序
	 */
	private List<StyleProcedureDo> styleProcedures;

	/**
	 *   款类部位
	 */
	private List<StylePartDo> styleParts;

	/**
	 *  款类物料
	 */
	private List<StyleMaterialDo> styleMaterial;

}
