package com.dookay.shiatzy.web.mobile.util;

import com.alibaba.fastjson.JSON;
import com.dookay.coral.common.utils.CookieUtils;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.utils.SpringContextHolder;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Chao on 2017/5/13.
 */
public class HistoryUtil {

    private static String HISTORY ="HISTORY";

    public static void setHistory(Long goodsId){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpServletResponse response = HttpContext.current().getResponse();
        List<Long> goodsList = JSON.parseArray(CookieUtils.getCookie(request,HISTORY),Long.class);
        if(goodsList  == null){
            goodsList = new ArrayList<>();
            goodsList.add(goodsId);
            CookieUtils.setCookie(response,HISTORY,JSON.toJSONString(goodsList));
        }else{
            if(goodsList.stream().noneMatch( n -> Objects.equals(n, goodsId))){
                goodsList.add(goodsId);
                CookieUtils.setCookie(response,HISTORY,JSON.toJSONString(goodsList));
            }
        }
    }

    public static List<GoodsDomain> getHistory(){
        HttpServletRequest request = HttpContext.current().getRequest();
        IGoodsService goodsService = SpringContextHolder.getBean("goodsService");
        List<Long> goodsList =JSON.parseArray(CookieUtils.getCookie(request,HISTORY),Long.class);
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setIds(goodsList);
        return goodsService.getList(goodsQuery);
    }
}
