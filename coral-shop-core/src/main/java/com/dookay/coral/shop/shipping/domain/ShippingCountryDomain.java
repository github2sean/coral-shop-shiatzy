package com.dookay.coral.shop.shipping.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 配送国家的domain
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_shipping_country")
public class ShippingCountryDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键编号*/
	@Id
	private Long id;
	
	/*排序*/
	private Long rank;
	
	/*是否有效*/
	private Integer isValid;
	
	/*是否默认*/
	private Integer isDefault;
	
	/*名称*/
	private String name;
	
	/*英文名称*/
	private String enName;
	
	/*运费*/
	private Double shippingCost;
	
	/*创建时间*/
	private Date createTime;
	
	/*更新时间*/
	private Date updateTime;

	/*汇率*/
	private Double rate;

}
