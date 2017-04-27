package com.dookay.shiatzy.web.mobile.controller;


import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.ISkuService;
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

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ModelAndView list(@ModelAttribute QueryGoodsForm queryGoodsForm){
       /* private Integer pageIndex = 1;//当前页码
        private Integer pageSize = 20; //页面大小，默认20
        private Integer offset = 0; // 行偏移
        private Integer limit;    //获取最大数量
        private String orderBy;// 排序字段
        private Boolean isDesc = true;// 是否倒序，默认是
        private QueryCriteria queryCriteria;*/

        GoodsQuery query = new GoodsQuery();
        query.setName(queryGoodsForm.getGoodsName());
        query.setCategoryId(queryGoodsForm.getCategoryId());
        query.setPageIndex(queryGoodsForm.getPageIndex());
        query.setPageSize(queryGoodsForm.getPageSize());
        query.setLimit(queryGoodsForm.getLimit());
        query.setOffset(queryGoodsForm.getOffset());
        query.setOrderBy("id");

        System.out.print("query:"+query);
        List<GoodsDomain> goodsList =  goodsService.getGoodsList(query);
        /*List<SkuDomain> skuList = new ArrayList<SkuDomain>();
        for (int i=0;goodsList!=null&&goodsList.size()>0&&i<goodsList.size();i++){

            skuList.add(skuService.getSkuByGoodsId(goodsList.get(i).getId()));
        }*/

        System.out.print("goodsList:"+JsonUtils.toJSONString(goodsList));



        ModelAndView modelAndView = new ModelAndView("goods/list");
        modelAndView.addObject("goodsList",goodsList);
        return modelAndView;
    }


}
