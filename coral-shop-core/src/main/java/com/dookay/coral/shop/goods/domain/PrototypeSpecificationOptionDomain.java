package com.dookay.coral.shop.goods.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * 原型规格选项的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_goods_prototype_specification_option")
public class PrototypeSpecificationOptionDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	/*主键id*/
	@Id
	private Long id;
	
	/*原型规格id*/
	private Long specificationId;
	
	/*选项名称*/
	private String name;
	
	/*英文名称*/
	private String enName;
	
	/*显示顺序*/
	private Integer rank;

	/*库存*/
	@Transient
	private Long stock;

}
