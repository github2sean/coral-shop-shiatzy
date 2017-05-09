package com.dookay.coral.shop.store.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * 店铺城市的domain
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_store_city")
public class StoreCityDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键编号*/
	@Id
	private Long id;
	
	/*排序*/
	private Long rank;
	
	/*是否有效*/
	private Integer isValid;
	
	/*[fk]国家id*/
	private String countryId;
	
	/*名称*/
	private String name;
	
	/*英文名称*/
	private String enName;
	
	/*创建时间*/
	private Date createTime;
	
	/*更新时间*/
	private Date updateTime;
	
	@Transient
	private StoreCountryDomain storeCountryDomain;
	
	
}
