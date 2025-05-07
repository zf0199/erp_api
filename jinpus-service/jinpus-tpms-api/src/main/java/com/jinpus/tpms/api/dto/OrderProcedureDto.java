package com.jinpus.tpms.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinpus.tpms.api.domain.BaseDo;
import com.jinpus.tpms.api.domain.OrderProcedureDetailDo;
import com.jinpus.tpms.api.domain.OrderProcedureDo;
import lombok.Data;

import java.util.List;

/**
 * @className: OrderProcedure
 * @author: zf
 * @date: 2025/5/5 17:23
 * @version: 1.0
 * @description:
 */

@Data
public class OrderProcedureDto extends OrderProcedureDo {


	private List<OrderProcedureDetailDo> orderProcedureDetailDos;


}
