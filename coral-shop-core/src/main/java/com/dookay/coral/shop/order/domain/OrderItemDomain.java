package com.dookay.coral.shop.order.domain;

import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * 订单明细的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_order_item")
public class OrderItemDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*订单id*/
	private Long orderId;
	
	/*skuId*/
	private Long skuId;

	private Long itemId;

	/*数量*/
	private Integer num;
	
	/*商品名称*/
	private String goodsName;
	
	/*商品编号*/
	private String goodsCode;
	
	/*商品价格*/
	private Double goodsPrice;
	
	/*sku规格json*/
	private String skuSpecifications;


	@Transient
	private GoodsItemDomain goodsItemDomain;
	
	
}
