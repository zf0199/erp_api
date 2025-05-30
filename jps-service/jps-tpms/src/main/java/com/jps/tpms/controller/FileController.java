package com.jps.tpms.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jps.tpms.api.domain.FileDo;
import com.jps.tpms.service.FileService;
import com.jps.common.core.util.R;
import com.jps.common.file.core.FileProperties;
import com.jps.common.file.core.FileTemplate;
import com.jps.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.jps.common.security.annotation.HasPermission;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 文件表
 *
 * @author pig
 * @date 2025-04-03 16:12:22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file" )
@Tag(description = "file" , name = "文件表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class FileController {

    private final FileService fileService;

	private final FileTemplate fileTemplate;

	private final FileProperties fileProperties;


    /**
     * 分页查询
     * @param page 分页对象
     * @param file 文件表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @HasPermission("basic_file_view")
    public R getFilePage(@ParameterObject Page page, @ParameterObject FileDo file) {
        LambdaQueryWrapper<FileDo> wrapper = Wrappers.lambdaQuery();
        return R.ok(fileService.page(page, wrapper));
    }

	@Operation(summary = "文件上传" , description = "文件上传" )
//	@HasPermission("basic_file_view")
	@RequestMapping(method = RequestMethod.POST,value = "/fileUpload")
	public R fileUpload( @RequestPart("file") MultipartFile file ){
		String bucketName = fileProperties.getBucketName();
		String fileType = file.getContentType();

		String name = file.getName();
		long size = file.getSize();

		String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String fileName;

		if (Optional.ofNullable(fileType).orElse("").startsWith("image/")){
			 fileName ="images/"+ format+FileUtil.extName(file.getOriginalFilename());
		}else {
			 fileName ="files/"+ format+FileUtil.extName(file.getOriginalFilename());
		}
		String url;
		try (InputStream inputStream = file.getInputStream()) {
			url = fileTemplate.putObject(fileProperties.getBucketName(), fileName, inputStream, fileType);
		} catch (Exception e) {
			log.error("上传失败", e);
			return R.failed(e.getLocalizedMessage());
		}
		FileDo fileDo = new FileDo();
		fileDo.setFileName(fileName);
		fileDo.setBucketName(bucketName);
		fileDo.setType(fileType);
		fileDo.setOriginalName(name);
		fileDo.setFileSize(Double.longBitsToDouble(size));
		fileDo.setFileUrl(url);
		return R.ok(fileDo);
	}


    /**
     * 通过条件查询文件表
     * @param file 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
    @HasPermission("basic_file_view")
    public R getDetails(@ParameterObject FileDo file) {
        return R.ok(fileService.list(Wrappers.query(file)));
    }

    /**
     * 新增文件表
     * @param file 文件表
     * @return R
     */
    @Operation(summary = "新增文件表" , description = "新增文件表" )
    @SysLog("新增文件表" )
    @PostMapping
//    @HasPermission("basic_file_add")
    public R save(@RequestBody FileDo file) {
        return R.ok(fileService.save(file));
    }

    /**
     * 修改文件表
     * @param file 文件表
     * @return R
     */
    @Operation(summary = "修改文件表" , description = "修改文件表" )
    @SysLog("修改文件表" )
    @PutMapping
    @HasPermission("basic_file_edit")
    public R updateById(@RequestBody FileDo file) {
        return R.ok(fileService.updateById(file));
    }

    /**
     * 通过id删除文件表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除文件表" , description = "通过id删除文件表" )
    @SysLog("通过id删除文件表" )
    @DeleteMapping
    @HasPermission("basic_file_del")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(fileService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param file 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @HasPermission("basic_file_export")
    public List<FileDo> exportExcel(FileDo file,Long[] ids) {
        return fileService.list(Wrappers.lambdaQuery(file).in(ArrayUtil.isNotEmpty(ids), FileDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param fileList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
    @HasPermission("basic_file_export")
    public R importExcel(@RequestExcel List<FileDo> fileList, BindingResult bindingResult) {
        return R.ok(fileService.saveBatch(fileList));
    }


}
