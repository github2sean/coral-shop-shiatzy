package com.dookay.coral.shop.content.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * 内容分类的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_content_category")
@Data
public class ContentCategoryDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*分类标题*/
	private String title;
	
	/*slug*/
	private String slug;
	
	/*父id*/
	private Long parentId;
	
	/*创建时间*/
	private Date createTime;

	/*分类等级*/
	private Integer level;
	/*子分类*/
	@Transient
	private List<ContentCategoryDomain> children;

	/*父分类*/
	@Transient
	private ContentCategoryDomain parent;
}
