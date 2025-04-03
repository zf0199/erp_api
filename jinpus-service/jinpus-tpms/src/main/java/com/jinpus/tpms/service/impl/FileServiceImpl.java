package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.FileDo;
import com.jinpus.tpms.mapper.FileMapper;
import com.jinpus.tpms.service.FileService;
import org.springframework.stereotype.Service;

/**
 * 文件表
 *
 * @author pig
 * @date 2025-04-03 16:12:22
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileDo> implements FileService {

}
