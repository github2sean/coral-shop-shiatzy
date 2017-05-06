package com.dookay.coral.shop.customer.domain;

import com.dookay.coral.host.user.domain.AccountDomain;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * 客户的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_customer")
public class CustomerDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*名*/
	private String firstName;
	
	/*姓*/
	private String lastName;
	
	/*邮件*/
	private String email;
	
	/*电话*/
	private String phone;
	
	/*会员等级*/
	private Integer customerLevel;
	
	/*是否club会员：1是 0否*/
	private Integer isArtClubMember;
	
	/*账号id*/
	private Long accountId;
	
	/*创建时间*/
	private Date createTime;
	
	/*订阅类型*/
	private Integer subscribeType;
	
	/*对应帐号 */
	@Transient
	private AccountDomain accountDomain;

}
