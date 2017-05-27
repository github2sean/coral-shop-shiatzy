package com.dookay.coral.shop.goods.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * 商品项目的domain
 * @author : luxor
 * @since : 2017年05月06日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_goods_item")
public class GoodsItemDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*排序*/
	private Integer rank;
	
	/*是否有效*/
	private Integer isValid;
	
	/*[fk]商品id*/
	private Long goodsId;
	
	/*[fk]颜色id*/
	private Long colorId;

	/*[fk]材质id*/
	private Long skinId;

	/*商品编号*/
	private String goodsNo;
	
	/*标题*/
	private String name;
	
	/*英文标题*/
	private String enName;
	
	/*图片*/
	private String thumb;
	
	/*商品照片*/
	private String photos;
	
	/*颜色值*/
	private String colorValue;
	
	/*价格*/
	private Double price;
	
	/*折扣价*/
	private Double discountPrice;
	
	/*描述*/
	private String description;
	
	/*英文描述*/
	private String enDescription;
	
	/*关联商品*/
	private String relatedGoods;
	
	/*创建时间*/
	private Date createTime;
	
	/*更新时间*/
	private Date updateTime;

	/*是否SALE*/
	private Integer isSale;

	@Transient
	/*库存*/
	private Integer quantity;

	/*商品*/
	@Transient
	private GoodsDomain goods;

	/*商品颜色*/
	@Transient
	private GoodsColorDomain goodsColor;

	
}
