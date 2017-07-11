package com.dookay.shiatzy.web.admin.model;

import com.dookay.coral.common.utils.ExcelColumn;
import com.dookay.coral.shop.goods.domain.GoodsColorDomain;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationOptionDomain;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品项目的domain
 * @author : luxor
 * @since : 2017年05月06日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_goods_item")
public class GoodsItemModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelColumn(name = "")
	private String code = "";

	/*[fk]原型id*/
	private String prototypeId;

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
