package com.dookay.coral.shop.goods.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * 原型规格的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_goods_prototype_specification")
public class PrototypeSpecificationDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*规格名称*/
	private String name;

	/*英文名称*/
	private String EnName;

	/*规格说明*/
	private String description;

	/*显示顺序*/
	private Integer rank;
	
	/*原型id*/
	private Long prototypeId;
	
	/*是否颜色：1是 0否*/
	private Integer isColor;

	@Transient
	private List<PrototypeSpecificationOptionDomain> prototypeSpecificationOptionList;

}
