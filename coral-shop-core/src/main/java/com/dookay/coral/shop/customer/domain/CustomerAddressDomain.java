package com.dookay.coral.shop.customer.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 客户地址的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_customer_address")
public class CustomerAddressDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键*/
	@Id
	private Long id;
	
	/*名*/
	private String firstName;
	
	/*姓*/
	private String lastName;
	
	/*称呼*/
	private String title;
	
	/*电话*/
	private String phone;
	
	/*国家id*/
	private Long countryId;
	
	/*省id*/
	private Long provinceId;
	
	/*城市id*/
	private Long cityId;
	
	/*详细地址*/
	private String address;
	
	/*备注*/
	private String memo;
	
	/*客户id*/
	private Long customerId;

}
