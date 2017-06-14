package com.dookay.coral.shop.index.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * 首页区块分组的domain
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_index_block_group")
public class IndexBlockGroupDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**/
	@Id
	private Long id;
	
	/*排序*/
	private Integer rank;
	
	/*是否有效：1有效 0无效*/
	private Integer isValid;
	
	/*标题*/
	private String title;
	
	/*排版类型：1单图 2双图*/
	private String type;
	
	/*创建时间*/
	private Date createTime;
	
	/*更新时间*/
	private Date updateTime;
	

	@Transient
	private List<IndexBlockDomain> indexBlockDomainList;
	
}
