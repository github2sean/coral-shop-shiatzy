package com.dookay.coral.shop.content.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 订阅的domain
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_content_subscribe")
public class SubscribeDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*邮件*/
	private String email;
	
	/*创建时间*/
	private Date createTime;

	
}
