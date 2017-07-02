package com.dookay.coral.shop.goods.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * 商品的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_goods")
public class GoodsDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*商品名称*/
	private String name;

	/*商品名称*/
	private String enName;
	
	/*简单标题*/
	private String brief;
	
	/*商品编号*/
	private String code;
	
	/*商品描述*/
	private String description;

	/*拥有颜色*/
	private String colorIds;

	/*拥有尺寸*/
	private String sizeIds;

	/*商品详情*/
	private String details;

	/*商品详情*/
	private String enDetails;
	
	/*原型id*/
	private Long prototypeId;
	
	/*商品分类id*/
	private Long categoryId;
	
	/*是否上架：1是 0否*/
	private Integer isPublished;
	
	/*创建时间*/
	private Date createTime;
	
	/*修改时间*/
	private Date updateTime;
	
	/*缩略图*/
	private String thumb;

	/*商品价格*/
	private Double price;

	/*商品优惠价格*/
	private Double disPrice;

	/*是否打折*/
	private Integer isSale;

	/*是否能预约*/
	private Integer isPre;

	/*多分类*/
	private String categoryIds;

	@Transient
	private List<Long> categoryIdList;

	/*商品分类*/
	@Transient
	private GoodsCategoryDomain goodsCategory;

	@Transient
	private List<GoodsItemDomain> goodsItemList;


	@Transient
	private List<GoodsColorDomain> goodsColorDomainList;

	/*商品尺寸*/
	@Transient
	private List<PrototypeSpecificationOptionDomain> sizeDomainList;

	/*默认第一个商品尺寸*/
	@Transient
	private PrototypeSpecificationOptionDomain firstSizeDomain;


	/*商品规格*/
	@Transient
	private List<PrototypeSpecificationDomain> specificationList;

	/*商品原型*/
	@Transient
	private List<PrototypeAttributeDomain> attributeDomains;

	/*商品材质*/
	@Transient
	private List<Long> attributeIds;
}
