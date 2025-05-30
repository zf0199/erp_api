package com.jps.tpms.api.vo;

import com.jps.tpms.api.domain.CustomerDo;
import com.jps.tpms.api.domain.OutsourceTypeDo;
import lombok.Data;

/**
 * @className: OutsourceTypeVo
 * @author: zf
 * @date: 2025/5/19 17:47
 * @version: 1.0
 * @description:
 */
@Data
public class OutsourceTypeVo  extends OutsourceTypeDo {

	private CustomerDo customerDo;

}
