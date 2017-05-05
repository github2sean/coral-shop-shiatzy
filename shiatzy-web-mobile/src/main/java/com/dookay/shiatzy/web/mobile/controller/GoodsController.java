package com.dookay.shiatzy.web.mobile.controller;


import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.goods.domain.*;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.query.SkuQuery;
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
        PageList<GoodsDomain> goodsList =  goodsService.getGoodsList(query);
        PageList<SkuDomain> goodsSku = skuService.getPageList(query);
        System.out.print("goodsSku:"+JsonUtils.toJSONString(goodsSku));
        ModelAndView modelAndView = new ModelAndView("goods/list");
        modelAndView.addObject("goodsList",goodsList);
        modelAndView.addObject("goodsSku",goodsSku);
        return modelAndView;
    }

    @RequestMapping(value = "listByCategory", method = RequestMethod.GET)
    public ModelAndView listByCategory(Long categoryId){
        GoodsQuery query = new GoodsQuery();
        query.setCategoryId(categoryId);
        PageList<GoodsDomain> goodsList =  goodsService.getGoodsList(query);
        System.out.println("goodsList:"+JsonUtils.toJSONString(goodsList.getList()));
        ModelAndView modelAndView = new ModelAndView("goods/list");
        List skuList = new ArrayList();
        for(GoodsDomain line:goodsList.getList()){
            SkuQuery skuQuery = new SkuQuery();
            skuQuery.setGoodsId(line.getId());
            skuList.add(skuService.getList(skuQuery));
            System.out.println("nowSkuList:"+JsonUtils.toJSONString(skuService.getList(skuQuery)));
        }
        modelAndView.addObject("goodsList",goodsList);
        modelAndView.addObject("skuList",skuList);
        return modelAndView;
    }

    @RequestMapping(value = "details" ,method = RequestMethod.GET)
    public ModelAndView details(Long goodsId,Long skuId){

        GoodsDomain goodsDomain = goodsService.get(goodsId);//得到商品
        List<SkuDomain> skuDomainList = new ArrayList<SkuDomain>();//得到Sku
        if(goodsId!=null&&skuId==null){
            skuDomainList = skuService.getSkuByGoodsId(goodsId);
        }else if (skuId!=null && goodsId !=null){
            skuDomainList.clear();
            skuDomainList.add(skuService.get(skuId));
        }
        Long prototypeId = goodsDomain.getPrototypeId();
        GoodsPrototypeDomain goodsPrototype = goodsPrototypeService.get(prototypeId);//得到原型
        /*PrototypeAttributeDomain prototypeAttribute = prototypeAttributeService.getAttributeByPrototypeId(goodsPrototype.getId());//得到原型属性
        List  attrOptionList = prototypeAttributeOptionService.getListByAttributeId(prototypeAttribute.getId());//得到原型属性选项值
        PrototypeSpecificationDomain prototypeSpecification = prototypeSpecificationService.getSpecificationByPrototypeId(goodsPrototype.getId());//得到原型规格
        List specificationOptionList = prototypeSpecificationOptionService.getListBySpecificationId(prototypeSpecification.getId());//得到原型规格选项值*/

        ModelAndView mv = new ModelAndView("goods/details");
       /* mv.addObject("attrOptionList",attrOptionList);
        mv.addObject("specificationOptionList",specificationOptionList);*/
        mv.addObject("goodsDomain",goodsDomain);
        mv.addObject("skuDomainList",skuDomainList);
        return mv;
    }

}
