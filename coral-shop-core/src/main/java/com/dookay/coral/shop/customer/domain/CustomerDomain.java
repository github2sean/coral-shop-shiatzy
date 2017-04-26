package com.dookay.coral.shop.customer.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 客户的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_customer")
public class CustomerDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*名*/
	private String firstName;
	
	/*姓*/
	private String lastName;
	
	/*邮件*/
	private String email;
	
	/*电话*/
	private String phone;
	
	/*会员等级*/
	private Integer customerLevel;
	
	/*是否club会员：1是 0否*/
	private Integer isArtClubMember;
	
	/*账号id*/
	private Long accountId;
	
	/*创建时间*/
	private Date createTime;
	
	/*订阅类型*/
	private Integer subscribeType;
	
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
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public Integer getCustomerLevel(){
		return customerLevel;
	}
	
	public void setCustomerLevel(Integer customerLevel){
		this.customerLevel = customerLevel;
	}
	
	public Integer getIsArtClubMember(){
		return isArtClubMember;
	}
	
	public void setIsArtClubMember(Integer isArtClubMember){
		this.isArtClubMember = isArtClubMember;
	}
	
	public Long getAccountId(){
		return accountId;
	}
	
	public void setAccountId(Long accountId){
		this.accountId = accountId;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Integer getSubscribeType(){
		return subscribeType;
	}
	
	public void setSubscribeType(Integer subscribeType){
		this.subscribeType = subscribeType;
	}

	@Override
	public String toString() {
		return "CustomerDomain{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", customerLevel=" + customerLevel +
				", isArtClubMember=" + isArtClubMember +
				", accountId=" + accountId +
				", createTime=" + createTime +
				", subscribeType=" + subscribeType +
				'}';
	}
}
