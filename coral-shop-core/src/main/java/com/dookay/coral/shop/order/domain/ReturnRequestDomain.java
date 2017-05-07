package com.dookay.coral.shop.order.domain;

import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.store.domain.StoreDomain;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * 订单退货申请的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_order_return_request")
public class ReturnRequestDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*订单id*/
	private Long orderId;
	
	/*订单号*/
	private String orderNo;
	
	/*下单时间*/
	private Date orderTime;
	
	/*创建时间*/
	private Date createTime;
	
	/*退货方式：1快递取件 2退回门店*/
	private Integer returnShippingMethod;
	
	/*备注*/
	private String memo;
	
	/*收货人姓名*/
	private String shipName;
	
	/*收货人地址*/
	private String shipAddress;
	
	/*退货门店id*/
	private Long returnShopId;
	@Transient
	private StoreDomain storeDomain;

	/*客户id*/
	private Long customerId;
	@Transient
	private CustomerDomain customerDomain;

	/*退货商品列表 */
	@Transient
	private List<ReturnRequestItemDomain> returnRequestItemDomainList;

}
