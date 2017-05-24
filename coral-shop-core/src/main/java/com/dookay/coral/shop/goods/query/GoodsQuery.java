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
	private  Long categoryId;
	private  Long prototypeId;
	private  Integer priceWay;//价格排序 0：高-低，1：低-高
	private  List<Long> colorIds;//颜色
	private  List<Long> sizeIds;//尺寸
    private  List<Long> attributeIds;//材质

	private final static String PRICE_FIELD = "price";
	private  List<Long> ids;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if (valid(name)){
			criteria.andLike("name","%"+name+"%");
		}

		if (valid(categoryId)){
			criteria.andEqualTo("categoryId",categoryId);
		}

		if (valid(prototypeId)){
			criteria.andEqualTo("prototypeId",prototypeId);
		}
		if(valid(ids)){
			criteria.andIn("id",ids);
		}
		if (valid(priceWay)){
			setOrderBy(PRICE_FIELD);
			if (priceWay == 1){
				setDesc(false);
			}
		}
		return queryCriteria;
	}

}
