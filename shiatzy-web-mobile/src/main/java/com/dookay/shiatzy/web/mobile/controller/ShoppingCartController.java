package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.ISkuService;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.shiatzy.web.mobile.form.AddShoppingCartForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by admin on 2017/4/27.
 */
@Controller
@RequestMapping("cart/")
public class ShoppingCartController extends BaseController{
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private ISkuService skuService;

    @RequestMapping(value = "addToCart" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addToCart(@ModelAttribute AddShoppingCartForm addShoppingCartForm){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        Integer shoppingCartType = addShoppingCartForm.getType();
        SkuDomain skuDomain =  skuService.get(addShoppingCartForm.getSkuId());
        Integer num = addShoppingCartForm.getNum();
        shoppingCartService.addToCart(customerDomain, skuDomain, shoppingCartType,num);
        return  successResult("添加成功");
    }

    @RequestMapping(value = "removeFromCart" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult removeFromCart(Long shoppingCartItemId){
        shoppingCartService.removeFromCart(shoppingCartItemId);
        return  successResult("删除成功");
    }

    @RequestMapping(value = "updateCart" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateCart(Long shoppingCartItemId,Integer num){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        shoppingCartService.updateShoppingCartItem(customerDomain,shoppingCartItemId,num);
        return  successResult("修改成功");
    }

    @RequestMapping(value = "list" ,method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView("shoppingcart/list");
        return mv;
    }

    @RequestMapping(value = "wishlist" ,method = RequestMethod.GET)
    public ModelAndView wishlist(){
        ModelAndView mv = new ModelAndView("wishlist/list");
        return mv;
    }

}
