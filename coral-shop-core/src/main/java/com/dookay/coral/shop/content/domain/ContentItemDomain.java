package com.dookay.coral.shop.content.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * 内容的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_content_item")
@Data
public class ContentItemDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*标题*/
	private String title;
	/*英文标题*/
	private String en_title;
	/*缩略图*/
	private String thumb;
	
	/*分类id*/
	private Long categoryId;
	
	/*创建时间*/
	private Date createTime;
	
	/*创建人id*/
	private Long creatorId;
	
	/*内容*/
	private String content;
	/*英文内容*/
	private String en_content;
	/*排序*/
	private Long displayOrder;

	@Transient
	private ContentCategoryDomain category;
}
