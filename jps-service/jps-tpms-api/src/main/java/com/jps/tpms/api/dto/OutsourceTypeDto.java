package com.jps.tpms.api.dto;

import com.baomidou.mybatisplus.annotation.*;
import com.jps.tpms.api.domain.CustomerDo;
import com.jps.tpms.api.domain.OutsourceTypeDo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 外发类型
 *
 * @author pig
 * @date 2025-04-08 15:31:25
 */
@Data
@TableName("outsource_type")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "外发类型")
public class OutsourceTypeDto extends OutsourceTypeDo {

	private CustomerDo customerDo;

}
