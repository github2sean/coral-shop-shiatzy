package com.dookay.coral.shop.goods.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 商品颜色的domain
 * @author : luxor
 * @since : 2017年05月06日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_goods_color")
public class GoodsColorDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*排序*/
	private Integer rank;
	
	/*是否有效*/
	private Integer isValid;
	
	/*名称*/
	private String name;
	
	/*英文名称*/
	private String enName;
	
	/*颜色*/
	private String color;
	
	/*创建时间*/
	private Date createTime;
	
	/*更新时间*/
	private Date updateTime;

}
