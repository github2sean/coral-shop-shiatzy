package com.dookay.coral.shop.order.domain;

import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.store.domain.StoreDomain;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * 预约单的domain
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_order_reservation")
public class ReservationDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键*/
	@Id
	private Long id;
	
	/*排序*/
	private Integer rank;
	
	/*是否已看過*/
	private Integer isVisible;
	
	/*状态*/
	private Integer status;
	
	/*顾客id*/
	private Long customerId;
	
	/*预约编号*/
	private String reservationNo;
	
	/*预约分店*/
	private String storeTitle;
	
	/*电话*/
	private String tel;
	
	/*地址*/
	private String address;
	
	/*开店时间*/
	private String time;
	
	/*备注*/
	private String note;
	
	/*创建时间*/
	private Date createTime;
	
	/*更新时间*/
	private Date updateTime;

	/*币种*/
	private String currentCode;

	@Transient
	private Double rate;
	
	@Transient
	private List<ReservationItemDomain> reservationItemDomainList;

	@Transient
	private CustomerDomain customerDomain;

	@Transient
	private StoreDomain storeDomain;

	public Double getTotal(){
		Double total = 0D;
		if(reservationItemDomainList== null)
			return total;
		for(ReservationItemDomain reservationItemDomain :reservationItemDomainList){
			total =total+reservationItemDomain.getNum() * reservationItemDomain.getGoodsPrice();
		}

		return total;
	}
	
}
