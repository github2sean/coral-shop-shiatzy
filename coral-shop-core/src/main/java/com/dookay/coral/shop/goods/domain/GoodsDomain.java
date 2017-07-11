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

	public GoodsDomain() {}

	public GoodsDomain(String name, String enName, String brief, String code, String description, String colorIds, String sizeIds, String skinIds, String details, String enDetails, Long prototypeId, Long categoryId, Integer isPublished, Date createTime, Date updateTime, String thumb, Double price, Double disPrice, Integer isSale, Integer isPre, String categoryIds, Integer rank) {
		this.name = name;//名字
		this.enName = enName;//英文名字
		this.code = code;//款号
		this.description = description;
		this.colorIds = colorIds;
		this.sizeIds = sizeIds;//尺寸
		this.details = details;//详情
		this.enDetails = enDetails;//英文详情
		this.createTime = createTime;
		this.price = price;//价格
		this.disPrice = disPrice;//折扣价
		this.isSale = isSale;
		this.isPre = isPre;
		this.categoryIds = categoryIds;//分类
		this.rank = rank;
	}

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

	/*拥有材质*/
	private String skinIds;

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

	/*排序*/
	private Integer rank;

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
