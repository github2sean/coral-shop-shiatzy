package com.dookay.coral.shop.goods.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 原型属性的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_goods_prototype_attribute")
public class PrototypeAttributeDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*属性名称*/
	private String name;
	
	/*原型id*/
	private Long prototypeId;
	
	/*是否筛选： 1是 0否*/
	private Integer isFilter;
	
	/*是否多选：1是 0否*/
	private Integer isMultiple;
	
	/**/
	private Integer isRequired;
	
	/*是否输入：1是 0否*/
	private Integer isInput;
	
	/*显示顺序*/
	private Integer displayOrder;
	

	
}
