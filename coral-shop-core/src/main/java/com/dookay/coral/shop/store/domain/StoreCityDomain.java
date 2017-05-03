package com.dookay.coral.shop.store.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 店铺城市的domain
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Table(name = "t_store_city")
public class StoreCityDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键编号*/
	@Id
	private Long id;
	
	/*排序*/
	private Long rank;
	
	/*是否有效*/
	private Integer isValid;
	
	/*[fk]国家id*/
	private String countryId;
	
	/*名称*/
	private String name;
	
	/*英文名称*/
	private String enName;
	
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
	
	public String getCountryId(){
		return countryId;
	}
	
	public void setCountryId(String countryId){
		this.countryId = countryId;
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
