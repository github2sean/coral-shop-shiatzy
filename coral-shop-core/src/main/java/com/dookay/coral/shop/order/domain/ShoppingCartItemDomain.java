package com.dookay.coral.shop.order.domain;

import com.dookay.coral.shop.goods.domain.*;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * 购物车的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_order_shopping_cart_item")
public class ShoppingCartItemDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*购物车类型：1购物车 2心愿单*/
	private Integer shoppingCartType;
	
	/*客户id*/
	private Long customerId;
	
	/*sukId*/
	private Long skuId;

	private Long itemId;

	/*数量*/
	private Integer num;
	
	/*商品名称*/
	private String goodsName;

	/*商品名称*/
	private String goodsEnName;
	
	/*商品编号*/
	private String goodsCode;
	
	/*商品价格*/
	private Double goodsPrice;

	/*商品价格*/
	private Double goodsDisPrice;
	
	/*sku规格json*/
	private String skuSpecifications;

	@Transient
	/*sku库存数量*/
	private Integer quantity;

	@Transient
	/*temp库存数量*/
	private Long stock;

	@Transient
	private GoodsItemDomain goodsItemDomain;

	@Transient
	private GoodsDomain goodsDomain;

	@Transient
	private Long categoryId;

	/*尺寸*/
	@Transient
	private String size;
	/*尺寸对象*/
	@Transient
	private PrototypeSpecificationOptionDomain sizeDomain;

	/*同类商品尺寸*/
	@Transient
	private List<PrototypeSpecificationOptionDomain> sizeDomins;

	/*同类商品颜色*/
	@Transient
	private List<GoodsColorDomain> colorDomains;

	/*与seseion中的数据绑定*/
	@Transient
	private String formId;

	@Transient
	private SkuDomain skuDomain;
	
}
