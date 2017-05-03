package com.dookay.coral.shop.order.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 预约单的domain
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Table(name = "t_order_reservation")
public class ReservationDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键*/
	@Id
	private Long id;
	
	/*排序*/
	private Integer rank;
	
	/*是否已看過*/
	private Integer isVisible;
	
	/*状态*/
	private Integer status;
	
	/*顾客id*/
	private Long customerId;
	
	/*预约编号*/
	private String reservationNo;
	
	/*预约分店*/
	private String storeTitle;
	
	/*电话*/
	private String tel;
	
	/*地址*/
	private String address;
	
	/*开店时间*/
	private String time;
	
	/*备注*/
	private String note;
	
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
	
	public Integer getStatus(){
		return status;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Long getCustomerId(){
		return customerId;
	}
	
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	
	public String getReservationNo(){
		return reservationNo;
	}
	
	public void setReservationNo(String reservationNo){
		this.reservationNo = reservationNo;
	}
	
	public String getStoreTitle(){
		return storeTitle;
	}
	
	public void setStoreTitle(String storeTitle){
		this.storeTitle = storeTitle;
	}
	
	public String getTel(){
		return tel;
	}
	
	public void setTel(String tel){
		this.tel = tel;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	public String getNote(){
		return note;
	}
	
	public void setNote(String note){
		this.note = note;
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
