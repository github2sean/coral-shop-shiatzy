package com.dookay.coral.shop.goods.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 商品项目照片的domain
 * @author : luxor
 * @since : 2017年07月02日
 * @version : v0.0.1
 */
@Table(name = "t_goods_item_photo")
public class GoodsItemPhotoDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*序號*/
	@Id
	private Long id;
	
	/*是否顯示*/
	private Integer isValid;
	
	/*[fk]所屬規格*/
	private Long itemId;
	
	/*照片*/
	private String image;
	
	/*大圖*/
	private String bigImage;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Integer getIsValid(){
		return isValid;
	}
	
	public void setIsValid(Integer isValid){
		this.isValid = isValid;
	}
	
	public Long getItemId(){
		return itemId;
	}
	
	public void setItemId(Long itemId){
		this.itemId = itemId;
	}
	
	public String getImage(){
		return image;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public String getBigImage(){
		return bigImage;
	}
	
	public void setBigImage(String bigImage){
		this.bigImage = bigImage;
	}
	
	
}
