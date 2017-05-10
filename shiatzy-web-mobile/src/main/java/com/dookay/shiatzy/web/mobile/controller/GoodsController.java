package com.dookay.shiatzy.web.mobile.controller;


import com.alibaba.fastjson.JSONArray;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.shop.goods.domain.*;
import com.dookay.coral.shop.goods.query.GoodsColorQuery;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.query.PrototypeSpecificationOptionQuery;
import com.dookay.coral.shop.goods.query.SkuQuery;
import com.dookay.coral.shop.goods.service.*;
import com.dookay.shiatzy.web.mobile.form.QueryGoodsForm;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/4/25.
 */
@Controller
@RequestMapping("/goods/")
public class GoodsController extends BaseController{

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
    @Autowired
    private IGoodsCategoryService goodsCategoryService;
    @Autowired
    private IGoodsColorService goodsColorService;
    @Autowired
    private IGoodsItemService goodsItemService;

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public ModelAndView search(@ModelAttribute QueryGoodsForm queryGoodsForm){
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

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(GoodsQuery query){
        ModelAndView modelAndView = new ModelAndView("goods/list");
        Long categoryId = query.getCategoryId();
        modelAndView.addObject("categoryId",categoryId);
        //商品列表
        query.setCategoryId(categoryId);
        List<GoodsDomain> goodsList =  goodsService.getList(query);
        goodsService.withGoodsItemList(goodsList);
        System.out.println(" goodsList:"+JsonUtils.toJSONString(goodsList));
        //商品分类
        GoodsCategoryDomain goodsCategoryDomain = goodsCategoryService.getCategory(categoryId);
        modelAndView.addObject("categoryName",goodsCategoryDomain.getName());
        //分类列表
        List<GoodsCategoryDomain> goodsCategoryDomainList = goodsCategoryService.listCategoryByParentId(goodsCategoryDomain.getParentId());
        modelAndView.addObject("categoryList",goodsCategoryDomainList);
        System.out.println(" goodsCategoryDomainList:"+JsonUtils.toJSONString(goodsCategoryDomainList));
        //材质列表
        //颜色列表
        List<Long> colorIds = new ArrayList<>();
        goodsList.forEach(x->colorIds.addAll(JsonUtils.toLongArray(x.getColorIds())));
        List<Long> newColorIds = colorIds.stream().distinct().collect(Collectors.toList());
        GoodsColorQuery goodsColorQuery = new GoodsColorQuery();
        goodsColorQuery.setIds(newColorIds);
        List<GoodsColorDomain> goodsColorDomainList = goodsColorService.getList(goodsColorQuery);
        modelAndView.addObject("colorList",goodsColorDomainList);
        System.out.println(" colorList:"+JsonUtils.toJSONString(goodsColorDomainList));
        //尺寸列表
        List<Long> sizeIds = new ArrayList<>();
        goodsList.forEach(x->sizeIds.addAll(JsonUtils.toLongArray(x.getSizeIds())));
        List<Long> newSizeIds = sizeIds.stream().distinct().collect(Collectors.toList());
        PrototypeSpecificationOptionQuery prototypeSpecificationOptionQuery = new PrototypeSpecificationOptionQuery();
        prototypeSpecificationOptionQuery.setIds(newSizeIds);
        List<PrototypeSpecificationOptionDomain> sizeList = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery);
        modelAndView.addObject("sizeList",sizeList);
        System.out.println(" sizeList:"+JsonUtils.toJSONString(sizeList));
        PageList<GoodsDomain> goodsDomainPageList = new PageList<>(goodsList,query.getPageIndex(),query.getPageSize(),goodsList.size());

        System.out.println(" list:"+JsonUtils.toJSONString(goodsDomainPageList));
        modelAndView.addObject("goodsDomainPageList",goodsDomainPageList);
        return modelAndView;
    }

    @RequestMapping(value = "details/{itemId}" ,method = RequestMethod.GET)
    public ModelAndView details(@PathVariable Long itemId){
        GoodsItemDomain goodsItemDomain =  goodsItemService.get(itemId);
        Long goodsId = goodsItemDomain.getGoodsId();
        goodsItemService.withColor(goodsItemDomain);
        GoodsDomain goodsDomain = goodsService.get(goodsId);//得到商品
        goodsService.withGoodsItemList(goodsDomain);

        List<Long> sizeIds =JsonUtils.toLongArray(goodsDomain.getSizeIds());
        PrototypeSpecificationOptionQuery prototypeSpecificationOptionQuery = new PrototypeSpecificationOptionQuery();
        prototypeSpecificationOptionQuery.setIds(sizeIds);
        List<PrototypeSpecificationOptionDomain> sizeList = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery);

        ModelAndView mv = new ModelAndView("goods/details");
        mv.addObject("goodsItemDomain",goodsItemDomain);
        mv.addObject("goodsDomain",goodsDomain);
        mv.addObject("sizeList",sizeList);
        return mv;
    }

}
