package com.dookay.coral.shop.order.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 商品sku规格值的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_order")
public class OrderDomain implements Serializable {

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
	
	/*收货人姓名*/
	private String shipName;
	
	/*收货人称呼*/
	private String shipTitle;
	
	/*收货人电话*/
	private String shipPhone;
	
	/*收货人国家*/
	private String shipContry;
	
	/*收货人省份*/
	private String shipProvince;
	
	/*收货人城市*/
	private String shipCity;
	
	/*收货人地址*/
	private String shipAddress;
	
	/*收货人备注*/
	private String shipMemo;
	
	/*支付方式: 1支付宝 2银联在线 3VISA*/
	private Integer pyamentMethod;
	
	/*付款时间*/
	private Date paidTime;
	
	/*是否需要发票：1是 0否*/
	private Integer billRequired;
	
	/*发票抬头*/
	private String billTitle;
	
	/*优惠券id*/
	private String couponId;
	
	/*优惠金额*/
	private Double couponDiscount;
	
	/*会员优惠*/
	private Double memberDiscount;
	
	/*运费*/
	private Double shipFee;
	
	/*订单应收金额*/
	private Double orderTotal;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public Long getCustomerId(){
		return customerId;
	}
	
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	
	public Long getStoreId(){
		return storeId;
	}
	
	public void setStoreId(Long storeId){
		this.storeId = storeId;
	}
	
	public Integer getStatus(){
		return status;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Date getOrderTime(){
		return orderTime;
	}
	
	public void setOrderTime(Date orderTime){
		this.orderTime = orderTime;
	}
	
	public Integer getShippingMethod(){
		return shippingMethod;
	}
	
	public void setShippingMethod(Integer shippingMethod){
		this.shippingMethod = shippingMethod;
	}
	
	public Date getShippedTime(){
		return shippedTime;
	}
	
	public void setShippedTime(Date shippedTime){
		this.shippedTime = shippedTime;
	}
	
	public String getShipperCompany(){
		return shipperCompany;
	}
	
	public void setShipperCompany(String shipperCompany){
		this.shipperCompany = shipperCompany;
	}
	
	public String getTrackingNumber(){
		return trackingNumber;
	}
	
	public void setTrackingNumber(String trackingNumber){
		this.trackingNumber = trackingNumber;
	}
	
	public Long getShipShopId(){
		return shipShopId;
	}
	
	public void setShipShopId(Long shipShopId){
		this.shipShopId = shipShopId;
	}
	
	public String getShipName(){
		return shipName;
	}
	
	public void setShipName(String shipName){
		this.shipName = shipName;
	}
	
	public String getShipTitle(){
		return shipTitle;
	}
	
	public void setShipTitle(String shipTitle){
		this.shipTitle = shipTitle;
	}
	
	public String getShipPhone(){
		return shipPhone;
	}
	
	public void setShipPhone(String shipPhone){
		this.shipPhone = shipPhone;
	}
	
	public String getShipContry(){
		return shipContry;
	}
	
	public void setShipContry(String shipContry){
		this.shipContry = shipContry;
	}
	
	public String getShipProvince(){
		return shipProvince;
	}
	
	public void setShipProvince(String shipProvince){
		this.shipProvince = shipProvince;
	}
	
	public String getShipCity(){
		return shipCity;
	}
	
	public void setShipCity(String shipCity){
		this.shipCity = shipCity;
	}
	
	public String getShipAddress(){
		return shipAddress;
	}
	
	public void setShipAddress(String shipAddress){
		this.shipAddress = shipAddress;
	}
	
	public String getShipMemo(){
		return shipMemo;
	}
	
	public void setShipMemo(String shipMemo){
		this.shipMemo = shipMemo;
	}
	
	public Integer getPyamentMethod(){
		return pyamentMethod;
	}
	
	public void setPyamentMethod(Integer pyamentMethod){
		this.pyamentMethod = pyamentMethod;
	}
	
	public Date getPaidTime(){
		return paidTime;
	}
	
	public void setPaidTime(Date paidTime){
		this.paidTime = paidTime;
	}
	
	public Integer getBillRequired(){
		return billRequired;
	}
	
	public void setBillRequired(Integer billRequired){
		this.billRequired = billRequired;
	}
	
	public String getBillTitle(){
		return billTitle;
	}
	
	public void setBillTitle(String billTitle){
		this.billTitle = billTitle;
	}
	
	public String getCouponId(){
		return couponId;
	}
	
	public void setCouponId(String couponId){
		this.couponId = couponId;
	}
	
	public Double getCouponDiscount(){
		return couponDiscount;
	}
	
	public void setCouponDiscount(Double couponDiscount){
		this.couponDiscount = couponDiscount;
	}
	
	public Double getMemberDiscount(){
		return memberDiscount;
	}
	
	public void setMemberDiscount(Double memberDiscount){
		this.memberDiscount = memberDiscount;
	}
	
	public Double getShipFee(){
		return shipFee;
	}
	
	public void setShipFee(Double shipFee){
		this.shipFee = shipFee;
	}
	
	public Double getOrderTotal(){
		return orderTotal;
	}
	
	public void setOrderTotal(Double orderTotal){
		this.orderTotal = orderTotal;
	}
	
	
}
