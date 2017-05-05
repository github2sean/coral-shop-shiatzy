package com.dookay.coral.shop.store.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 店铺的domain
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Table(name = "t_store")
public class StoreDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键编号*/
	@Id
	private Long id;
	
	/*排序*/
	private Integer rank;
	
	/*是否有效*/
	private Integer isValid;
	
	/*[fk]国家id*/
	private String countryId;
	
	/*[fk]城市id*/
	private String cityId;
	
	/*门店名称*/
	private String name;
	
	/*英文名称*/
	private String enTitle;
	
	/*地址*/
	private String address;
	
	/*英文地址*/
	private String enAddress;
	
	/*电话*/
	private String tel;
	
	/*营业时间*/
	private String time;
	
	/*英文营业时间*/
	private String enTime;
	
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
	
	public String getCityId(){
		return cityId;
	}
	
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getEnTitle(){
		return enTitle;
	}
	
	public void setEnTitle(String enTitle){
		this.enTitle = enTitle;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getEnAddress(){
		return enAddress;
	}
	
	public void setEnAddress(String enAddress){
		this.enAddress = enAddress;
	}
	
	public String getTel(){
		return tel;
	}
	
	public void setTel(String tel){
		this.tel = tel;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	public String getEnTime(){
		return enTime;
	}
	
	public void setEnTime(String enTime){
		this.enTime = enTime;
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
