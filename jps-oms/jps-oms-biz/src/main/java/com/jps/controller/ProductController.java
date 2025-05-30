package com.jps.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jps.domain.*;
import com.jps.service.ProductService;
import com.jps.common.core.util.R;
import com.jps.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

/**
 * @className: ProductController
 * @author: zf
 * @date: 2025/5/17 10:37
 * @version: 1.0
 * @description:
 */

/**
 * 商品管理
 */
@RestController
@AllArgsConstructor
@Slf4j
public class ProductController {

	private final ProductService productService;


	/**
	 * 商品管理分页查询
	 *
	 * @param page      分页对象
	 * @return 分页数据
	 */
	@RequestMapping(value = "/product/page", method = GET)
	public R getProductPage(@ParameterObject Page<ProductDo> page, @ParameterObject ProductQueryDto productQueryDto) {

		LambdaQueryWrapper<ProductDo> productDoWrapper = Wrappers.lambdaQuery();

		Optional.ofNullable(productQueryDto).ifPresent(e->{
			productDoWrapper.like(ObjectUtils.isNotEmpty(productQueryDto.getCustomerStyleNo()),ProductDo::getCustomerStyleNo,e.getCustomerStyleNo())
					.or()
					.like(ObjectUtils.isNotEmpty(productQueryDto.getCustomerStyleNo()),ProductDo::getInterStyleNo,e.getCustomerStyleNo())

					.eq(ObjectUtils.isNotEmpty(productQueryDto.getFirstCategory()),ProductDo::getFirstCategory,e.getFirstCategory())

					.eq(ObjectUtils.isNotEmpty(productQueryDto.getSecondCategory()),ProductDo::getSecondCategory,e.getSecondCategory())

					.eq(ObjectUtils.isNotEmpty(productQueryDto.getDesignerName()),ProductDo::getDesignerName,e.getDesignerName())

					.like(ObjectUtils.isNotEmpty(productQueryDto.getColor()),ProductDo::getColor,e.getColor())

					.eq(ObjectUtils.isNotEmpty(productQueryDto.getIsVisualImage()),ProductDo::getIsVisualImage,e.getIsVisualImage())

					.eq(ObjectUtils.isNotEmpty(productQueryDto.getYear()),ProductDo::getYear,e.getYear())

					.eq(ObjectUtils.isNotEmpty(productQueryDto.getSeason()),ProductDo::getSeason,e.getSeason())

					.eq(ObjectUtils.isNotEmpty(productQueryDto.getWaveBand()),ProductDo::getWaveBand,e.getWaveBand())

					.eq(ObjectUtils.isNotEmpty(productQueryDto.getStatus()),ProductDo::getStatus,e.getStatus())

					.eq(ObjectUtils.isNotEmpty(productQueryDto.getProductLevel()),ProductDo::getFirstCategory,e.getFirstCategory())

					.like(ObjectUtils.isNotEmpty(productQueryDto.getSellingPoints()),ProductDo::getSellingPoints,e.getSellingPoints())

					.between(ObjectUtils.isNotEmpty(productQueryDto.getStartTime()),ProductDo::getUpdateTime,e.getStartTime(),e.getEndTime())
					.orderByDesc(ProductDo::getCreateTime);
		});
		return R.ok(productService.page(page,productDoWrapper));
	}


	/**
	 * 通过id查询商品 附带最新价格信息
	 *
	 * @param id id
	 * @return R
	 */
	@RequestMapping(value = "/product/{id}", method = GET)
	public R getById(@PathVariable("id") Long id) {
		return R.ok(productService.getProDuctById(id));
	}


	/**
	 * 通过id查询渠道价格变化信息
	 *
	 * @param id id
	 * @return R
	 */
	@RequestMapping(value = "/saleInfo/{id}", method = GET)
	public R getChannelPriceInfo(@PathVariable("id") Long id) {
		return R.ok(productService.getChannelPriceInfo(id));
	}

	/**
	 * 新增商品
	 *
	 * @param productDto 商品信息 带有渠道价格
	 * @return R
	 */
//	@SysLog("新增报表" )
	@RequestMapping(value = "/product", method = POST)
	public R save(@RequestBody ProductDto productDto) {
		productService.saveProduct(productDto);
		return R.ok();
	}


	/**
	 * 修改商品
	 *
	 * @param productDo 商品信息
	 * @return R
	 */
	@SysLog("修改商品")
	@RequestMapping(value = "/product", method = PUT)
	public R updateById(@RequestBody ProductDo productDo) {
		return R.ok(productService.updateById(productDo));
	}


	/**
	 * 修改价格
	 *
	 * @param saleInfoDto 渠道价格
	 * @return R
	 */
	@SysLog("修改价格")
	@RequestMapping(value = "/product/updateSale", method = PUT)
	public R updateSaleInfo(@RequestBody SaleInfoDto saleInfoDto) {
		productService.updateSaleInfo(saleInfoDto);

		return R.ok();
	}


	/**
	 * 删除商品
	 *
	 * @param ids 商品id列表
	 * @return R
	 */
//	@Operation(summary = "通过id删除单位表" , description = "通过id删除单位表" )
	@SysLog("通过id删除商品")
	@RequestMapping(value = "/product", method = DELETE)
	public R removeById(@RequestBody Long[] ids) {
		productService.deleteProDuct(ids);
		return R.ok();
	}


	/**
	 *    导入数据
	 * @param file
	 * @param excelVos
	 * @return
	 */
	@PostMapping("/import")
	public R importExcel(@RequestParam("file") MultipartFile file, @RequestExcel List<ProductExcelDo> excelVos) {

		log.info(">>> 开始导入 **********************");
		productService.importExcel(file, excelVos);
		log.info(">>> 导入结束 **********************");
		return R.ok();

	}

	/**
	 *    导入商品价格明细
	 * @param file
	 * @param excelVos
	 * @return
	 */
	@PostMapping("/import/channel")
	public R importChannelExcel(@RequestParam("file") MultipartFile file, @RequestExcel List<ChannelExcelDto> excelVos) {

		productService.saveChannel(excelVos);
		return R.ok();
	}

	/**
	 *  导出数据
	 * @return
	 */
	@ResponseExcel
	@PostMapping("/export")
	public void exportExcel(HttpServletResponse response) {

		productService.exportExcel(response);

	}

	public void imageProcess(MultipartFile file, List<ProductExcelDo> excelVos) {
		try {
			XSSFWorkbook book = new XSSFWorkbook(file.getInputStream());
			//方式1 获取sheet数量，采用下标方式遍历读取每个工作表数据
			int sheetsNos = book.getNumberOfSheets();
			for (int sheetNo = 0; sheetNo < sheetsNos; sheetNo++) {
				Sheet sheet = book.getSheetAt(sheetNo);
				//...省略，内容为方式2
			}
			//方式2 获取sheet数量，直接遍历读取每个工作表数据
			for (Sheet sheet : book) {
				XSSFSheet xssSheet = (XSSFSheet) sheet;
				//获取工作表中绘图包
				XSSFDrawing drawing = xssSheet.getDrawingPatriarch();
				if (drawing == null) {
					break;
				}
				//获取所有图像形状
				List<XSSFShape> shapes = drawing.getShapes();
				//遍历所有形状
				for (XSSFShape shape : shapes) {
					//获取形状在工作表中的顶点位置信息（anchor锚点）
					XSSFClientAnchor anchor = (XSSFClientAnchor) shape.getAnchor();
					//图片形状在工作表中的位置, 所在行列起点和终点位置
					short c1 = anchor.getCol1();
					int r1 = anchor.getRow1();
					String key = r1 + "行," + c1 + "列";
					if (shape instanceof XSSFPicture) {
						try {
							XSSFPicture pic = (XSSFPicture) shape;
							//形状获取对应的图片数据
							XSSFPictureData picData = pic.getPictureData();
							//保存图片到本地
							byte[] data = picData.getData();
							//TODO 这里上传文件至oss并生成链接，这里不做过多描述，有疑问请参照oss服务调用
							String fileName = "https://oss.cn/" + DateUtil.today() + "/" + IdUtil.simpleUUID() + "/" + picData.suggestFileExtension();
							//fileTemplate.putObject(properties.getBucketName(), fileName, new ByteArrayInputStream(data));
							//TODO 放入excel集合，这里行数要减去1，获取图片是从表头开始（表头位置为0），获取excelVos是从数据开始（第一条数据位置为0）他们相差一个表头，所以要减去1才能对应
							excelVos.get(r1 - 1).setPhoto1(fileName);
						} catch (Exception e) {
							log.error("asyncImportList XSSFClientAnchor key|{} error|{}", key, e.getMessage());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("asyncImportList XSSFWorkbook error|{}", e.getMessage());
		}
	}

	/**
	 * 数据处理
	 */
	public void dataProcess(MultipartFile file, List<ProductExcelDo> excelVos) {
		// 这里默认读取第一个sheet
		try {
			EasyExcel.read(file.getInputStream(), ProductExcelDo.class, new ReadListener() {
				/**
				 * 单次缓存的数据量
				 */
				public static final int BATCH_COUNT = 100;
				/**
				 *临时存储
				 */
				private List<ProductExcelDo> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

				@Override
				public void invoke(Object object, AnalysisContext context) {
					ProductExcelDo data = (ProductExcelDo) object;
					cachedDataList.add(data);
					if (cachedDataList.size() >= BATCH_COUNT) {
						saveData();
						// 存储完成清理 list
						cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
					}
				}

				@Override
				public void doAfterAllAnalysed(AnalysisContext context) {
					saveData();
				}

				/**
				 * 加上存储数据库
				 */
				private void saveData() {
					log.info("已获取数据|{}条", cachedDataList.size());
					excelVos.addAll(cachedDataList);
				}
			}).sheet().doRead();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
