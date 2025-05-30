package com.jps.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jps.domain.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @className: ProductService
 * @author: zf
 * @date: 2025/5/15 9:53
 * @version: 1.0
 * @description:
 */
public interface ProductService  extends IService<ProductDo> {


	void saveProduct(ProductDto productDto);

	List<SaleInfoVo> getChannelPriceInfo(Long id);


	ProductVo getProDuctById(Long id);


	 void deleteProDuct(Long[] ids);

	void  updateSaleInfo( SaleInfoDto saleInfoDto);

	void importExcel( MultipartFile file,List<ProductExcelDo> excelVos);

	void  exportExcel(HttpServletResponse response);

	void saveChannel( List<ChannelExcelDto> excelVos);
}
