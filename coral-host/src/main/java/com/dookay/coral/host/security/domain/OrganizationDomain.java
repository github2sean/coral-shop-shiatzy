package com.dookay.coral.host.security.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 部门公司组织表的domain
 * @author : luxor
 * @since : 2017年03月02日
 * @version : v0.0.1
 */
@Table(name = "t_security_organization")
public class OrganizationDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*组织名称*/
	private String organizationName;
	
	/*分公司名称*/
	private String filialeName;
	
	/*组织地址*/
	private String address;
	
	/*父组织id  无父组织则为0*/
	private Long parentId;
	
	/*组织类型 1部门 2分公司*/
	private Integer type;
	
	/*是否合伙人 0是 1否 默认1否*/
	private Integer partner;
	
	/*邮政编码*/
	private String postcode;
	
	/*城市id*/
	private Long cityId;
	
	/*每次上门费用*/
	private Double doorFee;
	
	/*一次性上门费用*/
	private Double disposableDoorFee;
	
	/*创建时间*/
	private Date createTime= new Date();
	
	/*修改时间*/
	private Date updateTime= new Date();
	
	/*联系电话*/
	private String phone;
	
	/**/
	private String legalPersonSignatureImg;
	
	/**/
	private String officialSealImg;
	
	/**/
	private Integer status;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getOrganizationName(){
		return organizationName;
	}
	
	public void setOrganizationName(String organizationName){
		this.organizationName = organizationName;
	}
	
	public String getFilialeName(){
		return filialeName;
	}
	
	public void setFilialeName(String filialeName){
		this.filialeName = filialeName;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public Long getParentId(){
		return parentId;
	}
	
	public void setParentId(Long parentId){
		this.parentId = parentId;
	}
	
	public Integer getType(){
		return type;
	}
	
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getPartner(){
		return partner;
	}
	
	public void setPartner(Integer partner){
		this.partner = partner;
	}
	
	public String getPostcode(){
		return postcode;
	}
	
	public void setPostcode(String postcode){
		this.postcode = postcode;
	}
	
	public Long getCityId(){
		return cityId;
	}
	
	public void setCityId(Long cityId){
		this.cityId = cityId;
	}
	
	public Double getDoorFee(){
		return doorFee;
	}
	
	public void setDoorFee(Double doorFee){
		this.doorFee = doorFee;
	}
	
	public Double getDisposableDoorFee(){
		return disposableDoorFee;
	}
	
	public void setDisposableDoorFee(Double disposableDoorFee){
		this.disposableDoorFee = disposableDoorFee;
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
	
	public String getPhone(){
		return phone;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getLegalPersonSignatureImg(){
		return legalPersonSignatureImg;
	}
	
	public void setLegalPersonSignatureImg(String legalPersonSignatureImg){
		this.legalPersonSignatureImg = legalPersonSignatureImg;
	}
	
	public String getOfficialSealImg(){
		return officialSealImg;
	}
	
	public void setOfficialSealImg(String officialSealImg){
		this.officialSealImg = officialSealImg;
	}
	
	public Integer getStatus(){
		return status;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
	
}
