package com.dookay.coral.host.user.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 账户第三方扩展的domain
 * @author : stone
 * @since : 2017年03月02日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_user_account_binding")
public class AccountBindingDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	/*账户id*/
	private Long accountId;
	
	/*客户端*/
	private String client;
	
	/*openid*/
	private String openId;
	
	/*token*/
	private String accessToken;
	
	/*第三方昵称*/
	private String nickname;
	
	/*存储第三方json数据*/
	private String rawData;

}
