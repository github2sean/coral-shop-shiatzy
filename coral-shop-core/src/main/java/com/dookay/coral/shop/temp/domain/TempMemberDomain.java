package com.dookay.coral.shop.temp.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 临时会员的domain
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
@Table(name = "t_temp_member")
public class TempMemberDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*卡号*/
	private String cardNo;
	
	/*卡类型*/
	private String cardType;
	
	/*会员姓名*/
	private String name;
	
	/*会员id*/
	private Long memberId;
	
	/*折扣*/
	private Double discount;
	
	/*手机号*/
	private String mobile;
	
	/*积分*/
	private Integer point;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getCardNo(){
		return cardNo;
	}
	
	public void setCardNo(String cardNo){
		this.cardNo = cardNo;
	}
	
	public String getCardType(){
		return cardType;
	}
	
	public void setCardType(String cardType){
		this.cardType = cardType;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public Long getMemberId(){
		return memberId;
	}
	
	public void setMemberId(Long memberId){
		this.memberId = memberId;
	}
	
	public Double getDiscount(){
		return discount;
	}
	
	public void setDiscount(Double discount){
		this.discount = discount;
	}
	
	public String getMobile(){
		return mobile;
	}
	
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	
	public Integer getPoint(){
		return point;
	}
	
	public void setPoint(Integer point){
		this.point = point;
	}
	
	
}
