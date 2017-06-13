package com.dookay.coral.shop.index.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 首页区块的domain
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@Table(name = "t_index_block")
public class IndexBlockDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**/
	@Id
	private Integer id;
	
	/**/
	private Integer rank;
	
	/*是否有效：1有效 0无效*/
	private Integer isValid;
	
	/*分组id*/
	private Integer groupId;
	
	/*slogan位置*/
	private String position;
	
	/*图片*/
	private String image;
	
	/*大标题*/
	private String title;
	
	/*英文大标题*/
	private String enTitle;
	
	/*小标题*/
	private String subTitle;
	
	/*英文小标题*/
	private String enSubTitle;
	
	/*链接地址*/
	private String link;
	
	/*创建时间*/
	private Date createTime;
	
	/*更新时间*/
	private Date updateTime;
	
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
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
	
	public Integer getGroupId(){
		return groupId;
	}
	
	public void setGroupId(Integer groupId){
		this.groupId = groupId;
	}
	
	public String getPosition(){
		return position;
	}
	
	public void setPosition(String position){
		this.position = position;
	}
	
	public String getImage(){
		return image;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getEnTitle(){
		return enTitle;
	}
	
	public void setEnTitle(String enTitle){
		this.enTitle = enTitle;
	}
	
	public String getSubTitle(){
		return subTitle;
	}
	
	public void setSubTitle(String subTitle){
		this.subTitle = subTitle;
	}
	
	public String getEnSubTitle(){
		return enSubTitle;
	}
	
	public void setEnSubTitle(String enSubTitle){
		this.enSubTitle = enSubTitle;
	}
	
	public String getLink(){
		return link;
	}
	
	public void setLink(String link){
		this.link = link;
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
