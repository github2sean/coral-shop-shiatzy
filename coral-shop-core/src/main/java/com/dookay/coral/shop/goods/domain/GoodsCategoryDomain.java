package com.dookay.coral.shop.goods.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * 商品分类的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_goods_category")
public class GoodsCategoryDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*父id*/
	private Long parentId;
	
	/*原型id*/
	private Long prototypeId;
	
	/*分类名称*/
	private String name;

	/*英文分类名称*/
	private String enName;
	
	/*描述*/
	private String description;

	/*英文描述*/
	private String enDescription;

	/*缩略图*/
	private String thumb;
	
	/*是否有效：1是 0否*/
	private Integer isValid;
	
	/*显示顺序*/
	private Integer rank;
	
	/*创建时间*/
	private Date createTime;

	/*更新时间*/
	private Long updateTime;

	/*分类等级*/
	private Integer level;

	/*子分类*/
	@Transient
	private List<GoodsCategoryDomain> children;

	/*父分类*/
	@Transient
	private GoodsCategoryDomain parent;

}
