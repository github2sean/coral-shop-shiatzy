package com.dookay.coral.shop.goods.model;

import com.dookay.coral.common.utils.ExcelColumn;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 商品项目的domain
 * @author : luxor
 * @since : 2017年05月06日
 * @version : v0.0.1
 */
@Data
public class CreateGoodModel implements Serializable {

	private static final long serialVersionUID = 1L;



	/*[fk]原型id*/
	@ExcelColumn(name = "原型")
	private Long prototypeId;

	/*[fk]分类ids*/
	@ExcelColumn(name = "分类")
	private String categoryIds;

	/*[fk]颜色id*/
	@ExcelColumn(name = "颜色")
	private Long colorId;

	/*[fk]尺寸*/
	@ExcelColumn(name = "尺寸")
	private String sizeIds;

	/*商品编号*/
	@ExcelColumn(name = "商品款号")
	private String goodsNo;
	
	/*商品名*/
	@ExcelColumn(name = "商品名称")
	private String name;
	
	/*商品英文名*/
	@ExcelColumn(name = "商品英文名称")
	private String enName;

	
	/*价格*/
	@ExcelColumn(name = "价格")
	private Double price;
	
	/*折扣价*/
	@ExcelColumn(name = "折扣价格")
	private Double discountPrice;

	
	/*描述*/
	@ExcelColumn(name = "详情")
	private String description;
	
	/*英文描述*/
	@ExcelColumn(name = "英文详情")
	private String enDescription;


	/*规格说明*/
	@ExcelColumn(name = "规格说明")
	private String format;

	/*英文规格说明*/
	@ExcelColumn(name = "英文规格说明")
	private String enFormat;

}
