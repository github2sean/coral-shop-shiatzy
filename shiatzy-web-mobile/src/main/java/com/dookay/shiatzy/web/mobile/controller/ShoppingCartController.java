package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.order.domain.OrderShoppingCartItemDomain;
import com.dookay.coral.shop.order.service.IOrderShoppingCartItemService;
import com.dookay.shiatzy.web.mobile.form.AddShoppingCartForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private IOrderShoppingCartItemService orderShoppingCartItemService;

    @RequestMapping(value = "add" ,method = RequestMethod.POST)
    public JsonResult add(@ModelAttribute AddShoppingCartForm addShoppingCartForm,Long goodsId){

        int type = addShoppingCartForm.getType();

        OrderShoppingCartItemDomain getShoppingCart = orderShoppingCartItemService.get(goodsId);

        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);

        OrderShoppingCartItemDomain shoppingCart = new OrderShoppingCartItemDomain();
        shoppingCart.setShoppingCartType(addShoppingCartForm.getType());
        shoppingCart.setCustomerId(customerDomain.getId());
        shoppingCart.setNum(1);
        shoppingCart.setSkuId(addShoppingCartForm.getSkuId());
        shoppingCart.setGoodsName(addShoppingCartForm.getGoodsName());
        shoppingCart.setGoodsCode(addShoppingCartForm.getGoodsCode());
        shoppingCart.setGoodsPrice(addShoppingCartForm.getGoodsPrice());

        orderShoppingCartItemService.create(shoppingCart);





        return  successResult("添加成功",1);
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
