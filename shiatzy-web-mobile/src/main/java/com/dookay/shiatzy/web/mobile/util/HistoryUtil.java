package com.dookay.shiatzy.web.mobile.util;

import com.alibaba.fastjson.JSON;
import com.dookay.coral.common.utils.CookieUtils;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.utils.SpringContextHolder;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import com.dookay.coral.shop.order.service.IShoppingCartService;

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
    private static String CART_HISTORY ="CART_HISTORY";

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

    public static List<ShoppingCartItemDomain> getCartHistory(Integer cartType){
        HttpServletRequest request = HttpContext.current().getRequest();
        IShoppingCartService shoppingCartService = SpringContextHolder.getBean("goodsService");
        List<Long> cartList =JSON.parseArray(CookieUtils.getCookie(request,CART_HISTORY),Long.class);
        ShoppingCartItemQuery query = new ShoppingCartItemQuery();
        query.setShoppingCartType(cartType);
        query.setIds(cartList);
        return shoppingCartService.getList(query);
    }
    public static void setCartHistory(Long cartId){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpServletResponse response = HttpContext.current().getResponse();
        List<Long> cartList =JSON.parseArray(CookieUtils.getCookie(request,CART_HISTORY),Long.class);
        if(cartList  == null){
            cartList = new ArrayList<>();
            cartList.add(cartId);
            CookieUtils.setCookie(response,CART_HISTORY,JSON.toJSONString(cartList));
        }else{
            if(cartList.stream().noneMatch( n -> Objects.equals(n, cartId))){
                cartList.add(cartId);
                CookieUtils.setCookie(response,CART_HISTORY,JSON.toJSONString(cartList));
            }
        }
    }

}
