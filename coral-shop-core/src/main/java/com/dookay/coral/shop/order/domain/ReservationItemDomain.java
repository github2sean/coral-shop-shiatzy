package com.dookay.coral.shop.order.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 预约单明细的domain
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
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
	
	/*数量*/
	private Integer num;
	
	/*商品规格json*/
	private String specifications;
	
	/*创建时间*/
	private Date createTime;
	
	/*更新时间*/
	private Date updateTime;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Integer getRank(){
		return rank;
	}
	
	public void setRank(Integer rank){
		this.rank = rank;
	}
	
	public Integer getIsVisible(){
		return isVisible;
	}
	
	public void setIsVisible(Integer isVisible){
		this.isVisible = isVisible;
	}
	
	public Long getReservationId(){
		return reservationId;
	}
	
	public void setReservationId(Long reservationId){
		this.reservationId = reservationId;
	}
	
	public String getGoodsName(){
		return goodsName;
	}
	
	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}
	
	public String getSkuCode(){
		return skuCode;
	}
	
	public void setSkuCode(String skuCode){
		this.skuCode = skuCode;
	}
	
	public Integer getNum(){
		return num;
	}
	
	public void setNum(Integer num){
		this.num = num;
	}
	
	public String getSpecifications(){
		return specifications;
	}
	
	public void setSpecifications(String specifications){
		this.specifications = specifications;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	
}
