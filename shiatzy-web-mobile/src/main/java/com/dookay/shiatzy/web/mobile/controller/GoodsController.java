package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by admin on 2017/4/25.
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;
    @ResponseBody
    @RequestMapping(value = "/test")
    public List<GoodsDomain> test(){
        return  goodsService.getList(new GoodsQuery());
    }
}
