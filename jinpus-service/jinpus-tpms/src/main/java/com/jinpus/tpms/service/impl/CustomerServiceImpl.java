package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.CustomerDo;
import com.jinpus.tpms.mapper.CustomerMapper;
import com.jinpus.tpms.service.CustomerService;
import org.springframework.stereotype.Service;
/**
 * 客户表
 *
 * @author pig
 * @date 2025-03-17 15:59:23
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, CustomerDo> implements CustomerService {
}