package com.dookay.coral.shop.content.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 内容的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_push_content")
@Data
public class PushContentDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*推送时间*/
	private Date time;
	
	/*创建人id*/
	private Long accountId;
	
	/*内容*/
	private String content;

	/*英文内容*/
	private String enContent;

	/*排序*/
	private Long displayOrder;

	/*是否启用*/
	private Integer isValid;
}
