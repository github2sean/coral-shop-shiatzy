package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 商品的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class GoodsQuery extends Query {

	private  String name;
	private  String equalName;
	private  String enName;
	private  Long categoryId;
	private  Long prototypeId;
	private  List<Long> colorIds;//颜色
	private  List<Long> sizeIds;//尺寸
    private  List<Long> attributeIds;//材质
	private final static String PRICE_FIELD = "price";
	private  List<Long> ids;
	private  List<Long> categoryIds;
	private  Integer isSale;
	private  String code;

	private Integer isPublished;

	private List<Long> colorSeriesIds;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if (valid(name)){
			String likeStr = "%" + name + "%";
			queryCriteria.or().andLike("name",likeStr);
			queryCriteria.or().andLike("enName",likeStr);
		}
		if (valid(enName)){
			criteria.andLike("enName","%"+enName+"%");
		}
		if (valid(equalName)){
			criteria.andEqualTo("name",equalName);
		}
		if (valid(categoryId)){
			criteria.andEqualTo("categoryId",categoryId);
		}

		if (valid(prototypeId)){
			criteria.andEqualTo("prototypeId",prototypeId);
		}
		if(valid(ids)) {
			criteria.andIn("id", ids);
		}
		if(valid(categoryIds)) {
			criteria.andIn("categoryId", categoryIds);
		}
		if(valid(isSale)){
			criteria.andEqualTo("isSale",isSale);
		}
		if(valid(code)){
			criteria.andEqualTo("code",code);
		}
		if(valid(isPublished)){
			criteria.andEqualTo("isPublished",isPublished);
		}
		return queryCriteria;
	}

}
