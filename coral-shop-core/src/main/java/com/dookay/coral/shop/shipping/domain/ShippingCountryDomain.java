package com.dookay.coral.shop.shipping.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 配送国家的domain
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Table(name = "t_shipping_country")
public class ShippingCountryDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键编号*/
	@Id
	private Long id;
	
	/*排序*/
	private Long rank;
	
	/*是否有效*/
	private Integer isValid;
	
	/*是否默认*/
	private Integer isDefault;
	
	/*名称*/
	private String name;
	
	/*英文名称*/
	private String enName;
	
	/*运费*/
	private String shippingCost;
	
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
	
	public Long getRank(){
		return rank;
	}
	
	public void setRank(Long rank){
		this.rank = rank;
	}
	
	public Integer getIsValid(){
		return isValid;
	}
	
	public void setIsValid(Integer isValid){
		this.isValid = isValid;
	}
	
	public Integer getIsDefault(){
		return isDefault;
	}
	
	public void setIsDefault(Integer isDefault){
		this.isDefault = isDefault;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getEnName(){
		return enName;
	}
	
	public void setEnName(String enName){
		this.enName = enName;
	}
	
	public String getShippingCost(){
		return shippingCost;
	}
	
	public void setShippingCost(String shippingCost){
		this.shippingCost = shippingCost;
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
