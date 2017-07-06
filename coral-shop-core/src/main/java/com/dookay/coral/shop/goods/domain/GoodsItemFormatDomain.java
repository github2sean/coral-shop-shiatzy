package com.dookay.coral.shop.goods.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 商品项目参数的domain
 * @author : luxor
 * @since : 2017年07月06日
 * @version : v0.0.1
 */
@Table(name = "t_goods_item_format")
public class GoodsItemFormatDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*排序*/
	private Integer rank;
	
	/*是否有效*/
	private Integer isValid;
	
	/*[fk]所属项目*/
	private Integer itemId;
	
	/*名称*/
	private String name;
	
	/*英文名称*/
	private String enName;
	
	/*建立時間*/
	private Date createTime;
	
	/*更新時間*/
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
	
	public Integer getItemId(){
		return itemId;
	}
	
	public void setItemId(Integer itemId){
		this.itemId = itemId;
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
