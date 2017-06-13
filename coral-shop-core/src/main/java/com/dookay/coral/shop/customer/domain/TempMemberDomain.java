package com.dookay.coral.shop.customer.domain;

import com.dookay.coral.host.user.domain.AccountDomain;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 客户的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_temp_member")
public class TempMemberDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*卡号*/
	private String cardNo;
	
	/*卡类别*/
	private String cardType;
	
	/*会员名*/
	private String name;
	
	/*会员Id*/
	private Long memberId;
	
	/*会员折扣*/
	private Double discount;
	
	/*会员手机*/
	private String mobile;
	
	/*会员积分*/
	private Integer point;


}
