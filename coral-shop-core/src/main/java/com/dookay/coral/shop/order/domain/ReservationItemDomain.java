package com.dookay.coral.shop.order.domain;

import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * 预约单明细的domain
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_order_reservation_item")
public class ReservationItemDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键编号*/
	@Id
	private Long id;
	
	/*排序*/
	private Integer rank;
	
	/*是否显示*/
	private Integer isVisible;
	
	/*预约单id*/
	private Long reservationId;
	
	/*商品名称*/
	private String goodsName;
	
	/*商品规格编号*/
	private String skuCode;

	/*商品项目Id*/
	private Long itemId;
	
	/*数量*/
	private Integer num;
	
	/*商品规格json*/
	private String specifications;
	
	/*创建时间*/
	private Date createTime;
	
	/*更新时间*/
	private Date updateTime;

	/*状态*/
	private Integer status;

	@Transient
	private GoodsItemDomain goodsItemDomain;

	
}
