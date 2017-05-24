package com.dookay.coral.shop.goods.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 商品属性值的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_goods_attribute_value")
public class GoodsAttributeValueDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*商品id*/
	private Long goodsId;
	
	/*原型属性id*/
	private Long prototypeAttributeId;
	
	/*原型属性选项id*/
	private Long prototypeAttributeOptionId;
	
	/*商品属性值*/
	private String value;
	

	
	
}
