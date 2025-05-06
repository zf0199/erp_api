package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 文件表
 *
 * @author pig
 * @date 2025-04-03 16:12:22
 */
@Data
@TableName("file")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文件表")
public class FileDo extends BaseDo {


	/**
	* id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="id")
    private Long id;

	/**
	* 文件名称
	*/
    @Schema(description="文件名称")
    private String fileName;

	/**
	* 源文件名
	*/
    @Schema(description="源文件名")
    private String originalName;

	/**
	* 桶名
	*/
    @Schema(description="桶名")
    private String bucketName;

	/**
	* 文件类型
	*/
    @Schema(description="文件类型")
    private String type;

	/**
	* 文件大小
	*/
    @Schema(description="文件大小")
    private Double fileSize;

	/**
	* 目标id
	*/
    @Schema(description="目标id")
    private Long sourceId;

	/**
	* 文件路径
	*/
    @Schema(description="文件路径")
    private String fileUrl;


}
