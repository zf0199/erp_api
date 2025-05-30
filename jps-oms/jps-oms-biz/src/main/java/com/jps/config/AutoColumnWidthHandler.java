package com.jps.config;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;

import java.util.List;
import java.util.regex.Pattern;

/**
 * EasyExcel自适应列宽处理器
 * 自动计算单元格内容宽度并设置合适的列宽
 */
public class AutoColumnWidthHandler implements CellWriteHandler {

	// 中文字符匹配正则
	private static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]");
	// 最大列宽限制
	private static final int MAX_COLUMN_WIDTH = 50;
	// 表头额外增加的宽度
	private static final int HEADER_EXTRA_WIDTH = 4;
	// 内容额外增加的宽度
	private static final int CONTENT_EXTRA_WIDTH = 2;






	@Override
	public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
		Sheet sheet = writeSheetHolder.getSheet();
		int columnIndex = cell.getColumnIndex();

		// 获取当前列宽
		int currentColumnWidth = sheet.getColumnWidth(columnIndex) / 256;

		// 计算内容宽度
		String cellValue = getCellValue(cell);
		int contentWidth = calculateContentWidth(cellValue);

		// 根据是否为表头调整宽度
		int targetWidth = isHead ?
				contentWidth + HEADER_EXTRA_WIDTH :
				contentWidth + CONTENT_EXTRA_WIDTH;

		// 取较大值并限制最大宽度
		targetWidth = Math.max(currentColumnWidth, targetWidth);
		targetWidth = Math.min(targetWidth, MAX_COLUMN_WIDTH);

		// 设置列宽
		sheet.setColumnWidth(columnIndex, targetWidth * 256);
	}

	/**
	 * 计算内容的显示宽度
	 * 中文字符宽度按2计算，其他字符按1计算
	 */
	private int calculateContentWidth(String content) {
		if (content == null || content.isEmpty()) {
			return 0;
		}

		int width = 0;
		for (char c : content.toCharArray()) {
			// 判断是否为中文字符
			if (CHINESE_PATTERN.matcher(String.valueOf(c)).matches()) {
				width += 2;
			} else {
				width += 1;
			}
		}
		return width;
	}

	/**
	 * 获取单元格的值
	 */
	private String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue().toString();
				} else {
					return String.valueOf(cell.getNumericCellValue());
				}
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case FORMULA:
				return cell.getCellFormula();
			default:
				return "";
		}
	}
}