package com.dookay.coral.shop.customer.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 客户地址的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_customer_address")
public class CustomerAddressDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键*/
	@Id
	private Long id;
	
	/*名*/
	private String firstName;
	
	/*姓*/
	private String lastName;
	
	/*称呼*/
	private String title;
	
	/*电话*/
	private String phone;
	
	/*国家id*/
	private Long countryId;
	
	/*省id*/
	private Long provinceId;
	
	/*城市id*/
	private Long cityId;
	
	/*详细地址*/
	private String adress;
	
	/*备注*/
	private String memo;
	
	/*客户id*/
	private Long customerId;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public Long getCountryId(){
		return countryId;
	}
	
	public void setCountryId(Long countryId){
		this.countryId = countryId;
	}
	
	public Long getProvinceId(){
		return provinceId;
	}
	
	public void setProvinceId(Long provinceId){
		this.provinceId = provinceId;
	}
	
	public Long getCityId(){
		return cityId;
	}
	
	public void setCityId(Long cityId){
		this.cityId = cityId;
	}
	
	public String getAdress(){
		return adress;
	}
	
	public void setAdress(String adress){
		this.adress = adress;
	}
	
	public String getMemo(){
		return memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	
	public Long getCustomerId(){
		return customerId;
	}
	
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	
	
}
