package com.jps.config;

import com.alibaba.excel.write.style.row.AbstractRowHeightStyleStrategy;
import org.apache.poi.ss.usermodel.Row;

public class RowHeightWithImageHandler extends AbstractRowHeightStyleStrategy {
	private final int imageColumnIndex;

	public RowHeightWithImageHandler(int imageColumnIndex) {
		this.imageColumnIndex = imageColumnIndex;
	}

	@Override
	protected void setHeadColumnHeight(Row row, int i) {
		row.setHeight((short) (20 * 2)); // 表头行高
	}

	@Override
	protected void setContentColumnHeight(Row row, int i) {
		if (row.getCell(imageColumnIndex) != null) {
			row.setHeight((short) (100 * 20)); // 图片所在行设高
		} else {
			row.setHeight((short) -1); // 默认
		}
	}
}
