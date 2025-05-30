package com.jps.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.config.AutoColumnWidthHandler;
import com.jps.domain.*;
import com.jps.mapper.ChannelPriceMapper;
import com.jps.mapper.ProductMapper;
import com.jps.mapper.SaleInfoMapper;
import com.jps.service.ChannelPriceService;
import com.jps.service.ProductService;
import com.jps.util.MySAXParserHandler;
import com.jps.common.file.core.FileProperties;
import com.jps.common.file.core.FileTemplate;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.util.PackageHelper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @className: ProductService
 * @author: zf
 * @date: 2025/5/15 9:53
 * @version: 1.0
 * @description:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDo> implements ProductService {

	private final SaleInfoMapper saleInfoMapper;

	private final ChannelPriceMapper channelPriceMapper;

	private final FileTemplate fileTemplate;

	private final FileProperties fileProperties;

	private final ChannelPriceService channelPriceService;


	@Override
	public void saveProduct(ProductDto productDto) {
		ProductDo productDo = new ProductDo();
		BeanUtils.copyProperties(productDto, productDo);
		this.save(productDo);
		Long id = productDo.getId();

		SaleInfoDo saleInfoDo = new SaleInfoDo();
		saleInfoDo.setProductId(id);
		saleInfoMapper.insert(saleInfoDo);

		Long id1 = saleInfoDo.getId();
		List<ChannelPriceDo> channelDo = productDto.getChannelPriceDo();
		channelDo.forEach(e -> {
			e.setProductId(id);
			e.setSaleInfoId(id1);
			channelPriceMapper.insert(e);
		});
	}


	@Override
	public List<SaleInfoVo> getChannelPriceInfo(Long id) {
		ProductDo byId = this.getById(id);

		LambdaQueryWrapper<SaleInfoDo> saleInfoDoWrapper = Wrappers.lambdaQuery();

		saleInfoDoWrapper.eq(SaleInfoDo::getProductId, byId.getId());

		List<SaleInfoDo> saleInfoDos = saleInfoMapper.selectList(saleInfoDoWrapper);

		List<SaleInfoVo> list = saleInfoDos.stream().map(e -> {
			SaleInfoVo saleInfoVo = new SaleInfoVo();
			BeanUtils.copyProperties(e, saleInfoVo);
			LambdaQueryWrapper<ChannelPriceDo> channelPriceDoWrapper = Wrappers.lambdaQuery();
			channelPriceDoWrapper.eq(ChannelPriceDo::getSaleInfoId, e.getId());
			List<ChannelPriceDo> channelPriceDos = channelPriceMapper.selectList(channelPriceDoWrapper);
			saleInfoVo.setChannelPriceDo(channelPriceDos);
			return saleInfoVo;
		}).toList();
		return list;
	}

	@Override
	public ProductVo getProDuctById(Long id) {

		ProductDo byId = this.getById(id);

		ProductVo productVo = new ProductVo();

		BeanUtils.copyProperties(byId, productVo);

		LambdaQueryWrapper<SaleInfoDo> saleInfoDoWrapper = Wrappers.lambdaQuery();

		saleInfoDoWrapper.eq(SaleInfoDo::getProductId, id)
				.orderByDesc(SaleInfoDo::getCreateTime);

		List<SaleInfoDo> saleInfoDos = saleInfoMapper.selectList(saleInfoDoWrapper);
		SaleInfoVo saleInfoVo = new SaleInfoVo();
		if (saleInfoDos.isEmpty()) {
			saleInfoVo.setChannelPriceDo(Collections.emptyList());
			productVo.setSaleInfoVo(saleInfoVo);
			return productVo;
		}
		SaleInfoDo saleInfoDo = saleInfoDos.get(0);
		Long saleInfoId = saleInfoDo.getId();
		BeanUtils.copyProperties(saleInfoDo, saleInfoVo);
		LambdaQueryWrapper<ChannelPriceDo> channelPriceDoWrapper = Wrappers.lambdaQuery();
		channelPriceDoWrapper.eq(ChannelPriceDo::getSaleInfoId, saleInfoId);
		List<ChannelPriceDo> channelPriceDos = channelPriceMapper.selectList(channelPriceDoWrapper);
		saleInfoVo.setChannelPriceDo(channelPriceDos);
		productVo.setSaleInfoVo(saleInfoVo);
		return productVo;
	}

	@Override
	public void deleteProDuct(Long[] ids) {
		List<Long> list = Arrays.asList(ids);
		this.removeBatchByIds(list);
		LambdaQueryWrapper<SaleInfoDo> saleInfoDoWrapper = Wrappers.lambdaQuery();
		saleInfoDoWrapper.in(SaleInfoDo::getProductId, list);
		saleInfoMapper.delete(saleInfoDoWrapper);
		LambdaQueryWrapper<ChannelPriceDo> channelPriceDoWrapper = Wrappers.lambdaQuery();
		channelPriceDoWrapper.in(ChannelPriceDo::getProductId, list);
		channelPriceMapper.delete(channelPriceDoWrapper);
	}


	@Override
	public void updateSaleInfo(SaleInfoDto saleInfoDto) {

		SaleInfoDo saleInfoDo = new SaleInfoDo();

		BeanUtils.copyProperties(saleInfoDto, saleInfoDo);
		saleInfoMapper.insert(saleInfoDo);

		// 更新产品更新时间
		LambdaUpdateWrapper<ProductDo> productDoWrapper = Wrappers.lambdaUpdate();
		productDoWrapper.set(ProductDo::getUpdateTime, LocalDateTime.now());
		this.update(productDoWrapper);

		Long saleInfoId = saleInfoDo.getId();

		List<ChannelPriceDo> channelPriceDos = saleInfoDto.getChannelPriceDos();

		channelPriceDos.forEach(e -> {
			e.setProductId(saleInfoDto.getProductId());
			e.setSaleInfoId(saleInfoId);
			channelPriceMapper.insert(e);
		});
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void importExcel(MultipartFile file, List<ProductExcelDo> excelVos) {
//		this.imageProcess(file, excelVos);


		try {
			read(file, excelVos);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		List<ProductDo> list = excelVos.stream()
				.filter(e -> ObjectUtils.isNotEmpty(e.getCustomerStyleNo()))
				.map(e -> {
					ProductDo productDo = new ProductDo();
					BeanUtils.copyProperties(e, productDo);
					return productDo;
				}).toList();
		this.saveBatch(list);

	}

	@Override
	public void exportExcel(HttpServletResponse response) {

		List<ProductDo> list = this.list();

		List<ProductExcelVo> list2 = list.stream().map(e -> {
			LambdaQueryWrapper<SaleInfoDo> saleInfoWrapper = Wrappers.lambdaQuery();
			saleInfoWrapper.eq(SaleInfoDo::getProductId, e.getId())
					.orderByDesc(SaleInfoDo::getCreateTime)
					.last("LIMIT 1");
			SaleInfoDo saleInfoDo = saleInfoMapper.selectOne(saleInfoWrapper);
			List<ChannelPriceDo> channelPriceDos = Collections.emptyList();
			if (ObjectUtils.isNotEmpty(saleInfoDo)) {
				LambdaQueryWrapper<ChannelPriceDo> channelPriceWrapper = Wrappers.lambdaQuery();
				channelPriceWrapper.eq(ChannelPriceDo::getSaleInfoId, saleInfoDo.getId());
				channelPriceDos = channelPriceMapper.selectList(channelPriceWrapper);
			}


			ProductExcelVo productExcelVo = new ProductExcelVo();

			BeanUtils.copyProperties(e, productExcelVo);
			productExcelVo.setChannelPriceDos(channelPriceDos);
			return productExcelVo;
		}).toList();


		// 1. 获取所有渠道名称（动态列）
		Set<String> allChannels = list2.stream()
				.flatMap(p -> Optional.ofNullable(p.getChannelPriceDos()).orElse(List.of()).stream())
				.map(ChannelPriceDo::getChannelName)
				.collect(Collectors.toCollection(LinkedHashSet::new));


		f1(response, allChannels, list2);

	}

	@Override
	public void saveChannel(List<ChannelExcelDto> excelVos) {
		excelVos.stream().forEach(e -> {
			String styleNo = e.getStyleNo();
			LambdaQueryWrapper<ProductDo> productDoWrapper = Wrappers.lambdaQuery();
			productDoWrapper.eq(ProductDo::getCustomerStyleNo, styleNo);
			ProductDo one = this.getOne(productDoWrapper);
			Long id = one.getId();

			SaleInfoDo saleInfoDo = new SaleInfoDo();
			saleInfoDo.setProductId(id);
			saleInfoMapper.insert(saleInfoDo);
			Long saleId = saleInfoDo.getId();

			ArrayList<ChannelPriceDo> channelPriceDos = new ArrayList<>();
			// 快手价格
			ChannelPriceDo channelPriceDo1 = new ChannelPriceDo();
			channelPriceDo1.setProductId(id);
			channelPriceDo1.setSaleInfoId(saleId);
			channelPriceDo1.setChannelName("唯品会");
			channelPriceDo1.setChannelPrice(e.getWeiPin());
			channelPriceDos.add(channelPriceDo1);
			// 快手价格
			ChannelPriceDo channelPriceDo2 = new ChannelPriceDo();
			channelPriceDo2.setProductId(id);
			channelPriceDo2.setSaleInfoId(saleId);
			channelPriceDo2.setChannelName("爱库存");
			channelPriceDo2.setChannelPrice(e.getAKuCun());
			channelPriceDos.add(channelPriceDo2);

			// 快手价格
			ChannelPriceDo channelPriceDo3 = new ChannelPriceDo();
			channelPriceDo3.setProductId(id);
			channelPriceDo3.setSaleInfoId(saleId);
			channelPriceDo3.setChannelName("抖音");
			channelPriceDo3.setChannelPrice(e.getAKuCun());
			channelPriceDos.add(channelPriceDo3);

			// 快手价格
			ChannelPriceDo channelPriceDo4 = new ChannelPriceDo();
			channelPriceDo4.setProductId(id);
			channelPriceDo4.setSaleInfoId(saleId);
			channelPriceDo4.setChannelName("拼多多");
			channelPriceDo4.setChannelPrice(e.getAKuCun());
			channelPriceDos.add(channelPriceDo4);

			// 快手价格
			ChannelPriceDo channelPriceDo5 = new ChannelPriceDo();
			channelPriceDo5.setProductId(id);
			channelPriceDo5.setSaleInfoId(saleId);
			channelPriceDo5.setChannelName("京东");
			channelPriceDo5.setChannelPrice(e.getAKuCun());
			channelPriceDos.add(channelPriceDo5);

			// 快手价格
			ChannelPriceDo channelPriceDo6 = new ChannelPriceDo();
			channelPriceDo6.setProductId(id);
			channelPriceDo6.setSaleInfoId(saleId);
			channelPriceDo6.setChannelName("天猫");
			channelPriceDo6.setChannelPrice(e.getAKuCun());
			channelPriceDos.add(channelPriceDo6);

			// 快手价格
			ChannelPriceDo channelPriceDo7 = new ChannelPriceDo();
			channelPriceDo7.setProductId(id);
			channelPriceDo7.setSaleInfoId(saleId);
			channelPriceDo7.setChannelName("淘宝");
			channelPriceDo7.setChannelPrice(e.getAKuCun());
			channelPriceDos.add(channelPriceDo7);

			// 快手价格
			ChannelPriceDo channelPriceDo8 = new ChannelPriceDo();
			channelPriceDo8.setProductId(id);
			channelPriceDo8.setSaleInfoId(saleId);
			channelPriceDo8.setChannelName("微信视频号");
			channelPriceDo8.setChannelPrice(e.getAKuCun());
			channelPriceDos.add(channelPriceDo8);

			// 快手价格
			ChannelPriceDo channelPriceDo9 = new ChannelPriceDo();
			channelPriceDo9.setProductId(id);
			channelPriceDo9.setSaleInfoId(saleId);
			channelPriceDo9.setChannelName("快手");
			channelPriceDo9.setChannelPrice(e.getAKuCun());
			channelPriceDos.add(channelPriceDo9);


			// 快手价格
			ChannelPriceDo channelPriceDo10 = new ChannelPriceDo();
			channelPriceDo10.setProductId(id);
			channelPriceDo10.setSaleInfoId(saleId);
			channelPriceDo10.setChannelName("其他");
			channelPriceDo10.setChannelPrice(e.getAKuCun());
			channelPriceDos.add(channelPriceDo10);
			channelPriceService.saveBatch(channelPriceDos);
		});


	}

	public void f2(HttpServletResponse response, Set<String> allChannels, List<ProductExcelVo> list2) {
		// 2. 构造表头
		List<List<String>> headList = new ArrayList<>();
		headList.add(List.of("大货款号"));
		headList.add(List.of("设计师"));
		headList.add(List.of("诚博款号"));
		headList.add(List.of("商品图片"));
		headList.add(List.of("颜色"));
		headList.add(List.of("大类目"));
		for (String channel : allChannels) {
			headList.add(List.of(channel + "价格"));
		}
		// 3. 构造每一行数据
		List<List<Object>> rowList = new ArrayList<>();
		for (ProductExcelVo p : list2) {
			List<Object> row = new ArrayList<>();
			row.add(p.getCustomerStyleNo());
			row.add(p.getDesignerName());
			row.add(p.getInterStyleNo());
			String photo1 = p.getPhoto1();
			if (StringUtils.isEmpty(photo1)) {
				row.add("");
			} else {
				byte[] imageBytes = downloadImage(p.getPhoto1());


			}
			row.add(p.getColor());
			row.add(p.getFirstCategory());

			Map<String, Object> priceMap = Optional.ofNullable(p.getChannelPriceDos())
					.orElse(List.of())
					.stream()
					.collect(Collectors.toMap(ChannelPriceDo::getChannelName, ChannelPriceDo::getChannelPrice, (a, b) -> a));

			for (String channel : allChannels) {
				row.add(priceMap.getOrDefault(channel, ""));
			}

			rowList.add(row);
		}
		// 4. 设置响应头（下载 Excel）
		String fileName = URLEncoder.encode("商品导出", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		ServletOutputStream outputStream;
		try {
			outputStream = response.getOutputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// 5. 写出 Excel
		EasyExcel.write(outputStream)
				.head(headList)
				.registerWriteHandler(new AutoColumnWidthHandler()) // 注册列宽自适应处理器
				.sheet("商品数据")
				.doWrite(rowList);
	}

	public static byte[] downloadImage(String imageUrl) {
		try (InputStream input = new URL(imageUrl).openStream()) {
			return input.readAllBytes();
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}
	}


	public void f1(HttpServletResponse response, Set<String> allChannels, List<ProductExcelVo> list2) {
		try (OutputStream outputStream = response.getOutputStream();
			 Workbook workbook = new XSSFWorkbook()) {

			Sheet sheet = workbook.createSheet("商品数据");


			// 创建列头样式
			CellStyle headerStyle = workbook.createCellStyle();

			// 设置字体
			Font font = workbook.createFont();
			font.setBold(true);          // 加粗
			font.setFontHeightInPoints((short) 12); // 字体大小
			headerStyle.setFont(font);
			// 设置背景颜色（灰色）
			headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// 设置居中对齐
			headerStyle.setAlignment(HorizontalAlignment.CENTER);
			headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// 设置边框
			headerStyle.setBorderBottom(BorderStyle.THIN);
			headerStyle.setBorderTop(BorderStyle.THIN);
			headerStyle.setBorderLeft(BorderStyle.THIN);
			headerStyle.setBorderRight(BorderStyle.THIN);
			// 设置自动换行 ✅✅✅
			headerStyle.setWrapText(true);

			// ✅ 新增：创建数据单元格样式（文字居中）
			CellStyle dataStyle = workbook.createCellStyle();
			dataStyle.setAlignment(HorizontalAlignment.CENTER);
			dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			dataStyle.setBorderBottom(BorderStyle.THIN);
			dataStyle.setBorderTop(BorderStyle.THIN);
			dataStyle.setBorderLeft(BorderStyle.THIN);
			dataStyle.setBorderRight(BorderStyle.THIN);
			// 创建表头
			Row header = sheet.createRow(0);
			header.setHeightInPoints(40);

			int colIndex = 0;

			Cell cell1 = header.createCell(colIndex++);
			cell1.setCellValue("大货款号");
			cell1.setCellStyle(headerStyle);

			Cell cell2 = header.createCell(colIndex++);
			cell2.setCellValue("设计师");
			cell2.setCellStyle(headerStyle);

			Cell cell3 = header.createCell(colIndex++);
			cell3.setCellValue("诚博款号");
			cell3.setCellStyle(headerStyle);

			Cell cell4 = header.createCell(colIndex++);
			cell4.setCellValue("商品图片");
			cell4.setCellStyle(headerStyle);


			Cell cell5 = header.createCell(colIndex++);
			cell5.setCellValue("颜色");
			cell5.setCellStyle(headerStyle);

			Cell cell6 = header.createCell(colIndex++);
			cell6.setCellValue("大类目");
			cell6.setCellStyle(headerStyle);

			for (String ch : allChannels) {
				Cell cell = header.createCell(colIndex++);
				cell.setCellValue(ch + "价格");
				cell.setCellStyle(headerStyle);
			}

			for (int i = 0; i < colIndex; i++) {
				// 普通列宽固定为20字符
				sheet.setColumnWidth(i, 15 * 256);
			}
			sheet.setColumnWidth(3, 10 * 200);

			int rowIndex = 1;
			for (ProductExcelVo p : list2) {
				Row row = sheet.createRow(rowIndex);
				row.setHeightInPoints(50); // 让图片可见
				int c = 0;

				Cell dataCel1 = row.createCell(c++);
				dataCel1.setCellValue(p.getCustomerStyleNo());
				dataCel1.setCellStyle(dataStyle);

				Cell dataCel2 = row.createCell(c++);
				dataCel2.setCellValue(p.getDesignerName());
				dataCel2.setCellStyle(dataStyle);

				Cell dataCel3 = row.createCell(c++);
				dataCel3.setCellValue(p.getInterStyleNo());
				dataCel3.setCellStyle(dataStyle);


				// 下载图片 & 插入
				byte[] imageBytes = downloadImage(p.getPhoto1()); // 自定义工具方法
				if (imageBytes != null) {
					int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
					Drawing<?> drawing = sheet.createDrawingPatriarch();
					CreationHelper helper = workbook.getCreationHelper();
					ClientAnchor anchor = helper.createClientAnchor();
					anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
					anchor.setCol1(c);
					anchor.setRow1(rowIndex);
					anchor.setCol2(c + 1);
					anchor.setRow2(rowIndex + 1);

					// 设置偏移，防止图片贴边（可微调）
					anchor.setDx1(100); // 左边距
					anchor.setDy1(30);  // 上边距
					anchor.setDx2(100); // 右边距
					anchor.setDy2(30);  // 下边距

					Picture picture = drawing.createPicture(anchor, pictureIdx);
				}
				c++; // 图片列占一个

				Cell dataCel4 = row.createCell(c++);
				dataCel4.setCellValue(p.getColor());
				dataCel4.setCellStyle(dataStyle);

				Cell dataCel5 = row.createCell(c++);
				dataCel5.setCellValue(p.getFirstCategory());
				dataCel5.setCellStyle(dataStyle);

				// 渠道价格列
				Map<String, Object> priceMap = Optional.ofNullable(p.getChannelPriceDos())
						.orElse(List.of())
						.stream()
						.collect(Collectors.toMap(ChannelPriceDo::getChannelName, ChannelPriceDo::getChannelPrice, (a, b) -> a));
				for (String ch : allChannels) {
					Cell dataCel6 = row.createCell(c++);
					dataCel6.setCellValue(priceMap.getOrDefault(ch, "").toString());
					dataCel6.setCellStyle(dataStyle);
				}

				rowIndex++;
			}

			// 响应头
			String fileName = URLEncoder.encode("商品数据", "UTF-8").replaceAll("\\+", "%20");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
			workbook.write(outputStream);
			outputStream.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	//插入图片
	private String imgxx(String img, Sheet sheet, int i, int j, Workbook excel) {
		URL photoFile = null;
		try {
			photoFile = new URL(img);

			// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			//将图片读入BufferedImage对象
			BufferedImage bufferImg = ImageIO.read(photoFile);
			// 将图片写入流中
			ImageIO.write(bufferImg, "jpg", byteArrayOut);
			// 利用HSSFPatriarch将图片写入EXCEL
			Drawing<?> patriarch = sheet.createDrawingPatriarch();
			// 图片一导出到单元格I3-5中 列开始：8 行开始：2 列结束：9 行结束：5
			XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, j, i + 1, j, i + 1);
			anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
			// 插入图片内容
			Picture picture = patriarch.createPicture(anchor, excel.addPicture(byteArrayOut
					.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
			picture.resize(1, 1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	// 浮动图片
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
							String fileType = picData.suggestFileExtension();

							//保存图片到本地
							byte[] data = picData.getData();
							ByteArrayInputStream in = new ByteArrayInputStream(data);
							ByteArrayOutputStream out = new ByteArrayOutputStream();

							// 使用 Thumbnails 压缩（可按需设置尺寸、质量）
							Thumbnails.of(in)
									.size(800, 800) // 限制最大尺寸，等比例缩放（不裁剪）
									.outputQuality(0.6) // 压缩质量，1.0 = 原图，0.1 = 极限压缩
									.outputFormat(picData.suggestFileExtension()) // 保持原格式
									.toOutputStream(out);

							// 压缩后的图片数据
							byte[] compressedData = out.toByteArray();
							//TODO 这里上传文件至oss并生成链接，这里不做过多描述，有疑问请参照oss服务调用
//							String fileName =   DateUtil.today() + "/" + IdUtil.simpleUUID() + "/" + picData.suggestFileExtension();
							String fileName = DateUtil.today() + "/" + IdUtil.simpleUUID() + "." + picData.suggestFileExtension();
							String url = fileTemplate.putObject(fileProperties.getBucketName(), fileName, new ByteArrayInputStream(compressedData), picData.getMimeType());
							//TODO 放入excel集合，这里行数要减去1，获取图片是从表头开始（表头位置为0），获取excelVos是从数据开始（第一条数据位置为0）他们相差一个表头，所以要减去1才能对应
							excelVos.get(r1 - 1).setPhoto1(url);
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


	public static void main(String[] args) throws Exception {
//		File file = new File("D:/CB/xslx/产品价格表格式20250429.xlsx");
//		read((MultipartFile) ,null);
	}


	public void read(MultipartFile file, List<ProductExcelDo> excelVos) throws Exception {
		//包管理工具打开压缩包
		OPCPackage opc = PackageHelper.open(file.getInputStream());
		//获取所有包文件
		List<PackagePart> parts = opc.getParts();
		//获取每个工作表中的包文件
		Map<Integer, List<PackagePart>> picturePath = getEmbedPictures(parts);
		for (Integer key : picturePath.keySet()) {
			List<PackagePart> rows = picturePath.get(key);
			for (int i = 1; i < rows.size(); i++) {
				PackagePart part = rows.get(i);
				//System.out.println("sheetNo" + key + "\t第" + i + "行\t" + part);
				if (part != null) {
					InputStream imgIs = part.getInputStream();
					String name = part.getPartName().getName();
					String extension = name.substring(name.lastIndexOf('.') + 1);

					ByteArrayOutputStream out = new ByteArrayOutputStream();
					Thumbnails.of(imgIs)
							.size(600, 800)
//							.scale(1)    // 原图比例
							.outputQuality(0.8)
							.outputFormat(extension)
							.toOutputStream(out);
					byte[] byteArray = out.toByteArray();
					String fileName = DateUtil.today() + name;
					String url = fileTemplate.putObject(fileProperties.getBucketName(), fileName, new ByteArrayInputStream(byteArray), part.getContentType());
					excelVos.get(i - 1).setPhoto1(url);
					File dir = new File("D:/CB/xslx/test/");
					if (!dir.exists()) {
						dir.mkdirs();
					}
					FileUtils.copyInputStreamToFile(new ByteArrayInputStream(byteArray), new File(dir.getPath() + "/工作表" + key + "," + i + "行_" + name.substring(name.lastIndexOf("/") + 1)));
				} else {
					excelVos.get(i - 1).setPhoto1("");
				}
			}
		}
		try {
			opc.close();
		} catch (NullPointerException | IOException e) {

		}
	}


	private static Map<Integer, List<PackagePart>> getEmbedPictures(List<PackagePart> parts) throws IOException, ParserConfigurationException, SAXException, JDOMException {
		Map<String, Set<String>> mapImg = new HashMap<>();
		Map<String, String> mapImgPath = new HashMap<>();
		Map<Integer, List<String>> dataMap = new HashMap<>();

		for (PackagePart part : parts) {
//            System.out.println(part.getPartName());
			PackagePartName partName = part.getPartName();
			String name = partName.getName();
			if ("/xl/cellimages.xml".equals(name)) {
				SAXBuilder builder = new SAXBuilder();
				// 获取文档
				Document doc = builder.build(part.getInputStream());
				// 获取根节点
				Element root = doc.getRootElement();
				List<Element> cellImageList = root.getChildren();
				for (Element imgEle : cellImageList) {
					Element xdrPic = imgEle.getChildren().get(0);
					Element xdrNvPicPr = xdrPic.getChildren().get(0);
					Element xdrBlipFill = xdrPic.getChildren().get(1);
					Element aBlip = xdrBlipFill.getChildren().get(0);
					Attribute attr = aBlip.getAttributes().get(0);
					String imgId = xdrNvPicPr.getChildren().get(0).getAttributeValue("name");
					String id = attr.getValue();
//                    if (id.equals("rId12")) {
//                        System.out.println(attr.getValue() + "\t" + imgId);
//                    }
					if (mapImg.containsKey(id)) {
						mapImg.get(id).add(imgId);
					} else {
						Set<String> set = new HashSet<>();
						set.add(imgId);
						mapImg.put(id, set);
					}
				}
			}

			if ("/xl/_rels/cellimages.xml.rels".equals(name)) {
				SAXBuilder builder = new SAXBuilder();
				// 获取文档
				Document doc = builder.build(part.getInputStream());
				// 获取根节点
				Element root = doc.getRootElement();
				List<Element> relationshipList = root.getChildren();
                /*
                  <Relationship Id="rId999" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image" Target="media/image1000.jpeg"/>
                 */
				for (Element relationship : relationshipList) {
					String id = relationship.getAttributeValue("Id");
					String target = relationship.getAttributeValue("Target");
					mapImgPath.put(id, target);
//                    if (id.equals("rId12")) {
//                        System.out.println(id + "\t" + target);
//                    }
				}
			}

			if (name.contains("/xl/worksheets/sheet")) {
//                SAXBuilder builder = new SAXBuilder();
				// 获取文档
				String sheetNoStr = name.replace("/xl/worksheets/sheet", "").replace(".xml", "");
				Integer sheetNo = Integer.valueOf(sheetNoStr) - 1;
				// 步骤1：创建SAXParserFactory实例
				SAXParserFactory factory = SAXParserFactory.newInstance();
				// 步骤2：创建SAXParser实例
				SAXParser parser = factory.newSAXParser();
				MySAXParserHandler handler = new MySAXParserHandler();
				parser.parse(part.getInputStream(), handler);

				List<String> rows = handler.getRows();

				dataMap.put(sheetNo, rows);
			}

		}

//        for (Integer sheetNo : dataMap.keySet()) {
//            System.out.println(sheetNo + "\t" + dataMap.get(sheetNo).size());
//        }

		Map<String, String> imgMap = new HashMap<>();
		for (String id : mapImg.keySet()) {
			Set<String> imgIds = mapImg.get(id);
			String path = mapImgPath.get(id);
			for (String imgId : imgIds) {
				imgMap.put(imgId, path);
			}
		}
		for (Integer key : dataMap.keySet()) {
			List<String> rows = dataMap.get(key);
			for (int i = 0; i < rows.size(); i++) {
				String imgId = rows.get(i);
				if (imgMap.containsKey(imgId)) {
					rows.set(i, imgMap.get(imgId));
				}
			}
		}

		Map<Integer, List<PackagePart>> map = new HashMap<>();
		for (Integer key : dataMap.keySet()) {
			List<PackagePart> list = new ArrayList<>();
			map.put(key, list);
			List<String> pathList = dataMap.get(key);
			for (int i = 0; i < pathList.size(); i++) {
				list.add(i, null);
				String path = pathList.get(i);
				if (StringUtils.isNotEmpty(path)) {
					for (PackagePart part : parts) {
						PackagePartName partName = part.getPartName();
						String name = partName.getName();
						// /xl/media/image373.jpeg = media/image702.jpeg
						if (name.contains(path)) {
							list.set(i, part);
							break;
						}
					}
				}

			}
		}
		return map;
	}


}





