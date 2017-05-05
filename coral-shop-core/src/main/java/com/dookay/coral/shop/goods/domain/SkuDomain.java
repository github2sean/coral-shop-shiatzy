package com.dookay.coral.shop.goods.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * 商品sku的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_goods_sku")
public class SkuDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*商品id*/
	private Long goodsId;
	
	/*商品名称*/
	private String name;
	
	/*sku编码*/
	private String code;
	
	/*单价*/
	private Double price;
	
	/*库存数量*/
	private Integer quantity;

	/*商品规格json*/
	private String specifications;

	/*创建时间*/
	private Date createTime;

	/*商品*/
	@Transient
	private GoodsDomain goods;

}
