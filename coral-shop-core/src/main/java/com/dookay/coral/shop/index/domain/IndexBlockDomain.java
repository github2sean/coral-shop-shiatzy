package com.dookay.coral.shop.index.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * 首页区块的domain
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_index_block")
public class IndexBlockDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**/
	@Id
	private Long id;
	
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

	/*是否压字*/
	private Integer isCover;
	
	@Transient
	private IndexBlockGroupDomain indexBlockGroupDomain;
	
	
}
