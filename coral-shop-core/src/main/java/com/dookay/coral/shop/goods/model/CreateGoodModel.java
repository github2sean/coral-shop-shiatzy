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

	@ExcelColumn(name = "")
	private String code = "";

	/*[fk]原型id*/
	private Long prototypeId;

	/*[fk]分类ids*/
	private String categoryIds;

	/*[fk]颜色id*/
	private Long colorId;

	/*[fk]尺寸*/
	private String sizeIds;

	/*商品编号*/
	private String goodsNo;
	
	/*商品名*/
	private String name;
	
	/*商品英文名*/
	private String enName;

	
	/*价格*/
	private Double price;
	
	/*折扣价*/
	private Double discountPrice;

	
	/*描述*/
	private String description;
	
	/*英文描述*/
	private String enDescription;


	/*规格说明*/
	private String format;

	/*英文规格说明*/
	private String enFormat;

}
