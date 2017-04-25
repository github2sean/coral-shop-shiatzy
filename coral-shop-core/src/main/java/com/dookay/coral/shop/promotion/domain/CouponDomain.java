package com.dookay.coral.shop.promotion.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 优惠券的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_promotion_coupon")
public class CouponDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*优惠券名称*/
	private String name;
	
	/*优惠券代码*/
	private String code;
	
	/*使用次数限制*/
	private Integer limit;
	
	/*剩余次数*/
	private Integer left;
	
	/*创建时间*/
	private Date createTime;
	
	/*开始时间*/
	private Date startTime;
	
	/*结束时间*/
	private Date endTime;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getCode(){
		return code;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public Integer getLimit(){
		return limit;
	}
	
	public void setLimit(Integer limit){
		this.limit = limit;
	}
	
	public Integer getLeft(){
		return left;
	}
	
	public void setLeft(Integer left){
		this.left = left;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	public Date getEndTime(){
		return endTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	
	
}
