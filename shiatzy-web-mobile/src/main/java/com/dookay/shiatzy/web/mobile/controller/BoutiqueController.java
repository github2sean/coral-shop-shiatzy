package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.ISkuService;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.shiatzy.web.mobile.form.AddShoppingCartForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by admin on 2017/4/27.
 */

@Controller
@RequestMapping("boutique/")
public class BoutiqueController extends BaseController{


    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IShoppingCartService shoppingCartService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ISkuService skuService;


    private static int  SHOPPINGCART_TYPE = 3;//精品店



    @RequestMapping(value = "list" ,method = RequestMethod.GET)
    public ModelAndView list(){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        List<ShoppingCartItemDomain> cartList = shoppingCartService.listShoppingCartItemByCustomerId(customerDomain.getId(),SHOPPINGCART_TYPE);
        ModelAndView mv = new ModelAndView("boutique/list");
        mv.addObject("cartList",cartList);
        return mv;
    }


    @RequestMapping(value = "addToBoutique" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addToBoutique(@ModelAttribute AddShoppingCartForm addShoppingCartForm){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        SkuDomain skuDomain =  skuService.get(addShoppingCartForm.getSkuId());
        Integer num = addShoppingCartForm.getNum();
        ShoppingCartItemDomain shoppingCartItemDomain = shoppingCartService.isExistInCart(customerDomain,skuDomain,SHOPPINGCART_TYPE);
        if(shoppingCartItemDomain!=null){
            return  successResult("精品店已存在此商品");
        }else{
            shoppingCartService.addToCart(customerDomain, skuDomain, SHOPPINGCART_TYPE,num);
        }
        return  successResult("添加成功");
    }

    @RequestMapping(value = "removeFromBoutique" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult removeFromBoutique(Long shoppingcartId){
        shoppingCartService.removeFromCart(shoppingcartId);
        return  successResult("删除成功");
    }

    @RequestMapping(value = "chooseShop" ,method = RequestMethod.GET)
    public ModelAndView chooseShop(){

        ModelAndView mv = new ModelAndView("");

        return mv;

    }


}
