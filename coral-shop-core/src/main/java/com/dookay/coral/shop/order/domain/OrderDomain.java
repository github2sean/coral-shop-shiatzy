package com.dookay.coral.shop.order.domain;

import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.store.domain.StoreDomain;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * 商品sku规格值的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_order")
public class 	OrderDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*订单编号*/
	private String orderNo;
	
	/*客户id*/
	private Long customerId;
	
	/*店铺id*/
	private Long storeId;
	
	/*订单状态：1待支付 2已支付 3已发货 4已收货 -1已取消*/
	private Integer status;
	
	/*下单时间*/
	private Date orderTime;
	
	/*配送方式：1快递运送 2门店取货*/
	private Integer shippingMethod;
	
	/*发货时间*/
	private Date shippedTime;
	
	/*快递公司*/
	private String shipperCompany;
	
	/*快递单号*/
	private String trackingNumber;
	
	/*取货门店id*/
	private Long shipShopId;

	/*配送国家id*/
	private Long shippingCountryId;

	/*收货地址id*/
	private Long shipAddressId;

	/*收货人名*/
	private String shipFirstName;

	/*收货人姓*/
	private String shipLastName;

	/*收货人称呼*/
	private String shipTitle;
	
	/*收货人电话*/
	private String shipPhone;
	
	/*收货人国家*/
	private String shipCountry;
	
	/*收货人省份*/
	private String shipProvince;
	
	/*收货人城市*/
	private String shipCity;

	/*收货人地址*/
	private String shipPostalCode;

	/*收货人地址*/
	private String shipAddress;
	
	/*收货人备注*/
	private String shipMemo;
	
	/*支付方式: 1支付宝 2银联在线 3VISA*/
	private Integer paymentMethod;
	
	/*付款时间*/
	private Date paidTime;
	
	/*是否需要发票：1是 0否*/
	private Integer billRequired;
	
	/*发票抬头*/
	private String billTitle;
	
	/*优惠券id*/
	private Long couponId;
	
	/*优惠金额*/
	private Double couponDiscount;
	
	/*会员优惠*/
	private Double memberDiscount;
	
	/*运费*/
	private Double shipFee;

	/*商品金额*/
	private Double goodsTotal;

	/*订单应收金额*/
	private Double orderTotal;

	/**
	 * 订单明细
	 */
	@Transient
	private List<OrderItemDomain> orderItemDomainList;
	@Transient
	private StoreDomain storeDomain;
	@Transient
	private CustomerAddressDomain customerAddressDomain;
	@Transient
	private Integer canReturnNum;
	

}
