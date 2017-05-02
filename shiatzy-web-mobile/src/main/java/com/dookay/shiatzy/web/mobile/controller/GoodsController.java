package com.dookay.shiatzy.web.mobile.controller;


import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.goods.domain.*;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.*;
import com.dookay.shiatzy.web.mobile.form.QueryGoodsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/4/25.
 */
@Controller
@RequestMapping("goods/")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISkuService skuService;
    @Autowired
    private IGoodsPrototypeService goodsPrototypeService;
    @Autowired
    private IPrototypeAttributeService prototypeAttributeService;
    @Autowired
    private IPrototypeAttributeOptionService prototypeAttributeOptionService;
    @Autowired
    private IPrototypeSpecificationService prototypeSpecificationService;
    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ModelAndView list(@ModelAttribute QueryGoodsForm queryGoodsForm){
        GoodsQuery query = new GoodsQuery();
        query.setName(queryGoodsForm.getGoodsName());
        query.setCategoryId(queryGoodsForm.getCategoryId());
        query.setPrototypeId(queryGoodsForm.getPrototypeId());
        query.setPageIndex(queryGoodsForm.getPageIndex());
        query.setPageSize(queryGoodsForm.getPageSize());
        query.setLimit(queryGoodsForm.getLimit());
        query.setOffset(queryGoodsForm.getOffset());
        query.setOrderBy("id");
        System.out.print("query:"+query);
        PageList<GoodsDomain> goodsList =  goodsService.getGoodsList(query);
        PageList<SkuDomain> goodsSku = skuService.getPageList(query);
        System.out.print("goodsSku:"+JsonUtils.toJSONString(goodsSku));
        ModelAndView modelAndView = new ModelAndView("goods/list");
        modelAndView.addObject("goodsList",goodsList);
        modelAndView.addObject("goodsSku",goodsSku);
        return modelAndView;
    }

    @RequestMapping(value = "details" ,method = RequestMethod.GET)
    public ModelAndView details(Long goodsId){

        GoodsDomain goodsDomain = goodsService.get(goodsId);//得到商品
        SkuDomain skuDomain = skuService.getSkuByGoodsId(goodsId);//得到Sku

        Long prototypeId = goodsDomain.getPrototypeId();
        GoodsPrototypeDomain goodsPrototype = goodsPrototypeService.get(prototypeId);//得到原型

        PrototypeAttributeDomain prototypeAttribute = prototypeAttributeService.getAttributeByPrototypeId(goodsPrototype.getId());//得到原型属性
        List  attrOptionList = prototypeAttributeOptionService.getListByAttributeId(prototypeAttribute.getId());//得到原型属性选项值

        PrototypeSpecificationDomain prototypeSpecification = prototypeSpecificationService.getSpecificationByPrototypeId(goodsPrototype.getId());//得到原型规格
        List speciOptionList = prototypeSpecificationOptionService.getListBySpecificationId(prototypeAttribute.getId());//得到原型规格选项值

        ModelAndView mv = new ModelAndView("goods/details");
        mv.addObject("attrOptionList",attrOptionList);
        mv.addObject("speciOptionList",speciOptionList);
        mv.addObject("goodsDomain",goodsDomain);
        mv.addObject("skuDomain",skuDomain);
        return mv;
    }

}
