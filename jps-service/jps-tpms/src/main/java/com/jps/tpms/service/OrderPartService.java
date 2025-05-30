package com.jps.tpms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jps.tpms.api.domain.OrderPartDo;

/**
 * @className: OrderPartService
 * @author: zf
 * @date: 2025/4/23 15:51
 * @version: 1.0
 * @description:
 */
public interface OrderPartService extends IService<OrderPartDo> {

	void deleteById(Long id);
}
