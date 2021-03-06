package com.dookay.coral.shop.goods.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dookay.coral.common.enums.ValidEnum;
import com.dookay.coral.common.utils.StringUtils;
import com.dookay.coral.shop.base.BaseTest;
import com.dookay.coral.shop.goods.domain.*;
import com.dookay.coral.shop.goods.query.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/8
 */
public class IGoodsServiceTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISkuService skuService;
    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
    @Autowired
    private IGoodsColorService goodsColorService;
    @Autowired
    private IGoodsItemService goodsItemService;
    @Autowired
    private IGoodsItemPhotoService goodsItemPhotoService;
    @Autowired
    private IGoodsItemFormatService goodsItemFormatService;

    @Test
    public void updateGoodsSizes() throws Exception {
        List<GoodsDomain> goodsList =  goodsService.getList(new GoodsQuery());
        for (GoodsDomain goodsDomain:goodsList){
            goodsService.updateSizes(goodsDomain);

            logger.info(goodsDomain.getSizeIds());
        }
    }

    @Test
    public void updateGoodsColor() throws Exception {
        List<GoodsDomain> goodsList =  goodsService.getList(new GoodsQuery());
        for (GoodsDomain goodsDomain:goodsList){

            goodsService.updateColors(goodsDomain);
            logger.info(goodsDomain.getSizeIds());
        }
    }

    @Test
    public void  updateSkuSize(){
        List<SkuDomain> skuDomainList = skuService.getList(new SkuQuery());

        for(SkuDomain skuDomain :skuDomainList){
            GoodsDomain goodsDomain = goodsService.get(skuDomain.getGoodsId());
            if(goodsDomain == null)
                continue;
            PrototypeSpecificationOptionQuery  query = new PrototypeSpecificationOptionQuery();
            query.setSpecificationId(goodsDomain.getPrototypeId());
            query.setName(skuDomain.getSize());
            PrototypeSpecificationOptionDomain optionDomain = prototypeSpecificationOptionService.getFirst(query);
            if(optionDomain == null){
                optionDomain = new PrototypeSpecificationOptionDomain();
                optionDomain.setName(skuDomain.getSize());
                optionDomain.setEnName(skuDomain.getSize());
                optionDomain.setSpecificationId(goodsDomain.getPrototypeId());
                optionDomain.setRank(1);
                prototypeSpecificationOptionService.create(optionDomain);
            }
            Long sizeId =optionDomain.getId();
            skuDomain.setSpecifications("{\"size\":"+sizeId+"}");
            skuService.update(skuDomain);
        }
    }

    @Test
    public void  updateCategoryIds(){
        List<GoodsDomain> goodsList =  goodsService.getList(new GoodsQuery());
        for (GoodsDomain goodsDomain:goodsList){
            List<Long> idList = JSON.parseArray(goodsDomain.getCategoryIds(),Long.class);

            List<Long> newIdList = idList.stream().map(x->x+15L).collect(Collectors.toList());

            goodsDomain.setCategoryIdList(newIdList);
            goodsDomain.setCategoryIds(JSON.toJSONString(newIdList));
            goodsService.update(goodsDomain);
        }
    }


    @Test
    public void  updateGoodsItemColor(){
        GoodsItemQuery goodsItemQuery = new GoodsItemQuery();
        goodsItemQuery.setIsValid(ValidEnum.YES.getValue());
        List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(goodsItemQuery);
        for(GoodsItemDomain goodsItemDomain :goodsItemDomainList){
            GoodsColorQuery goodsColorQuery = new GoodsColorQuery();
            goodsColorQuery.setName(goodsItemDomain.getName());
            GoodsColorDomain goodsColorDomain = goodsColorService.getFirst(goodsColorQuery);
            if(goodsColorDomain == null){
                goodsColorDomain = new GoodsColorDomain();
                goodsColorDomain.setName(goodsItemDomain.getName());
                goodsColorDomain.setEnName(goodsItemDomain.getEnName());
                goodsColorDomain.setIsValid(ValidEnum.YES.getValue());
                goodsColorDomain.setColor(goodsItemDomain.getColorValue());
                goodsColorDomain.setCreateTime(new Date());
                goodsColorDomain.setUpdateTime(new Date());
                goodsColorDomain.setRank(1);
                goodsColorService.create(goodsColorDomain);
            }
            goodsItemDomain.setColorId(goodsColorDomain.getId());
            goodsItemService.update(goodsItemDomain);
        }
    }

    @Test
    public void  updateGoodsItemPhoto(){
        List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(new GoodsItemQuery());
        for(GoodsItemDomain goodsItemDomain :goodsItemDomainList){
            GoodsItemPhotoQuery goodsItemPhotoQuery = new GoodsItemPhotoQuery();
            goodsItemPhotoQuery.setItemId(goodsItemDomain.getId());
            List<GoodsItemPhotoDomain> goodsItemPhotoDomainList = goodsItemPhotoService.getList(goodsItemPhotoQuery);
            JSONArray jsonArray = new JSONArray();

            for(GoodsItemPhotoDomain goodsItemPhotoDomain :goodsItemPhotoDomainList){
                JSONObject jsonObject =  new JSONObject();
                jsonObject.put("alt","thumb");
                jsonObject.put("file",goodsItemPhotoDomain.getBigImage());
                jsonArray.add(jsonObject);
            }
            goodsItemDomain.setPhotos(JSON.toJSONString(jsonArray));
            goodsItemService.update(goodsItemDomain);
        }
    }

    @Test
    public void  updateGoodsItemThumb(){
        List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(new GoodsItemQuery());
        for(GoodsItemDomain goodsItemDomain :goodsItemDomainList){
            GoodsItemPhotoQuery goodsItemPhotoQuery = new GoodsItemPhotoQuery();
            goodsItemPhotoQuery.setItemId(goodsItemDomain.getId());
            goodsItemPhotoQuery.setOrderBy("id");
            goodsItemPhotoQuery.setDesc(false);
            List<GoodsItemPhotoDomain> goodsItemPhotoDomainList = goodsItemPhotoService.getList(goodsItemPhotoQuery);
            GoodsItemPhotoDomain goodsItemPhotoDomain =  new GoodsItemPhotoDomain();
            if(goodsItemPhotoDomainList != null && goodsItemPhotoDomainList.size()>0)
            {
                goodsItemPhotoDomain = goodsItemPhotoDomainList.get(0);
            }
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject =  new JSONObject();
            jsonObject.put("alt","thumb");
            jsonObject.put("file",goodsItemPhotoDomain.getImage());
            jsonArray.add(jsonObject);
            goodsItemDomain.setThumb(JSON.toJSONString(jsonArray));
            goodsItemService.update(goodsItemDomain);
        }
    }

    @Test
    public void updateGoodsItemFormat(){
        List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(new GoodsItemQuery());
        for(GoodsItemDomain goodsItemDomain :goodsItemDomainList){
            GoodsItemFormatQuery goodsItemFormatQuery = new GoodsItemFormatQuery();
            goodsItemFormatQuery.setItemId(goodsItemDomain.getId());
            goodsItemFormatQuery.setOrderBy("id");
            goodsItemFormatQuery.setDesc(false);
            List<GoodsItemFormatDomain> goodsItemFormatDomainList = goodsItemFormatService.getList(goodsItemFormatQuery);
            String enFormat ="<ul>";
            String format ="<ul>";
            for(GoodsItemFormatDomain goodsItemFormatDomain:goodsItemFormatDomainList){
                format = format+"<li>"+goodsItemFormatDomain.getName()+"</li>";
                enFormat = enFormat+"<li>"+goodsItemFormatDomain.getEnName()+"</li>";
            }
            format = format+"</ul>";
            enFormat = enFormat+"</ul>";
            goodsItemDomain.setFormat(format);
            goodsItemDomain.setEnFormat(enFormat);
            goodsItemService.update(goodsItemDomain);
        }
    }
}