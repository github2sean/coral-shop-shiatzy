package com.dookay.coral.shop.goods.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * 商品的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_goods")
public class GoodsDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*商品名称*/
	private String name;
	
	/*简单标题*/
	private String brief;
	
	/*商品编号*/
	private String code;
	
	/*商品描述*/
	private String description;
	
	/*商品详情*/
	private String details;
	
	/*原型id*/
	private Long prototypeId;
	
	/*商品分类id*/
	private Long categoryId;
	
	/*是否上架：1是 0否*/
	private Integer isPublished;
	
	/*创建时间*/
	private Date createTime;
	
	/*修改时间*/
	private Date updateTime;
	
	/*缩略图*/
	private String thumb;

	/*商品分类*/
	@Transient
	private GoodsCategoryDomain goodsCategory;

}
