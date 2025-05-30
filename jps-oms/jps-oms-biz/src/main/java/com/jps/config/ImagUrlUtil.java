package com.common.util;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.IoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
@Slf4j
public class ImagUrlUtil implements Converter<String> {
	public static int urlConnectTimeout = 2000;
	public static int urlReadTimeout = 6000;

	@Override
	public Class<?> supportJavaTypeKey() {
		return String.class;
	}

	@Override
	public WriteCellData<?> convertToExcelData(String url, ExcelContentProperty contentProperty,GlobalConfiguration globalConfiguration) throws IOException {
		InputStream inputStream = null;
		try {
			URL value = new URL(url);
			if (ObjectUtils.isEmpty(value)){
				return new WriteCellData<>("图片链接为空");
			}
			URLConnection urlConnection = value.openConnection();
			urlConnection.setConnectTimeout(urlConnectTimeout);
			urlConnection.setReadTimeout(urlReadTimeout);
			inputStream = urlConnection.getInputStream();
			byte[] bytes = IoUtils.toByteArray(inputStream);
			return new WriteCellData<>(bytes);
		}catch (Exception e){
			log.info("图片获取异常",e);
			return new WriteCellData<>("图片获取异常");
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}