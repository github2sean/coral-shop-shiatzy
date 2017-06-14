package com.dookay.coral.shop.temp.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 临时库存的domain
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
@Table(name = "t_temp_stock")
public class TempStockDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*款号*/
	private String productNo;
	
	/*颜色*/
	private String color;
	
	/*尺码*/
	private String size;
	
	/*数量*/
	private Integer num;
	
	/*更新时间*/
	private Date updateTime;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getProductNo(){
		return productNo;
	}
	
	public void setProductNo(String productNo){
		this.productNo = productNo;
	}
	
	public String getColor(){
		return color;
	}
	
	public void setColor(String color){
		this.color = color;
	}
	
	public String getSize(){
		return size;
	}
	
	public void setSize(String size){
		this.size = size;
	}
	
	public Integer getNum(){
		return num;
	}
	
	public void setNum(Integer num){
		this.num = num;
	}
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	
}
