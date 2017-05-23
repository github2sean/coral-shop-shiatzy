package com.dookay.coral.shop.promotion.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 优惠券的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_promotion_coupon")
public class CouponDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*优惠券名称*/
	private String name;
	
	/*优惠券代码*/
	private String code;
	
	/*使用次数限制*/
	private Integer limitTimes;
	
	/*剩余次数*/
	private Integer leftTimes;
	
	/*创建时间*/
	private Date createTime;

	/*开始时间*/
	private Date startTime;
	
	/*结束时间*/
	private Date endTime;

	/*折扣额度*/
	private  Double discount;

	/*优惠价格*/
	private Double discountPrice;

	/*首页显示*/
	private Integer indexShow;

	private String title;

	private String enTitle;

}
