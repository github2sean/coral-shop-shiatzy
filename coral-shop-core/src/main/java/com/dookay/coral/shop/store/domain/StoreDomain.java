package com.dookay.coral.shop.store.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * 店铺的domain
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_store")
public class StoreDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键编号*/
	@Id
	private Long id;
	
	/*排序*/
	private Integer rank;
	
	/*是否有效*/
	private Integer isValid;
	
	/*[fk]国家id*/
	private String countryId;
	
	/*[fk]城市id*/
	private String cityId;
	
	/*门店名称*/
	private String name;
	
	/*英文名称*/
	private String enTitle;
	
	/*地址*/
	private String address;
	
	/*英文地址*/
	private String enAddress;
	
	/*电话*/
	private String tel;
	
	/*营业时间*/
	private String time;
	
	/*英文营业时间*/
	private String enTime;
	
	/*创建时间*/
	private Date createTime;

	/*位置坐标*/
	private String localPoint;

	
	/*更新时间*/
	private Date updateTime;
	@Transient
	private StoreCityDomain storeCityDomain;
	@Transient
	private StoreCountryDomain storeCountryDomain;
	
	
}
