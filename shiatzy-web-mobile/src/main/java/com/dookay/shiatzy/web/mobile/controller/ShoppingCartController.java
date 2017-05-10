package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.enums.ValidEnum;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationOptionDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.query.PrototypeSpecificationOptionQuery;
import com.dookay.coral.shop.goods.query.SkuQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.ISkuService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.shiatzy.web.mobile.form.AddShoppingCartForm;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private IGoodsItemService goodsItemService;

    @RequestMapping(value = "addToCart" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addToCart(@ModelAttribute AddShoppingCartForm addShoppingCartForm){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        Integer shoppingCartType = addShoppingCartForm.getType();

         /*获取SKU*/
        Long itemId = addShoppingCartForm.getItemId();
        Long sizeId = addShoppingCartForm.getSizeId();
        System.out.println("sizeId:"+sizeId+"\nitemId:"+itemId);

        SkuQuery skuQuery = new SkuQuery();
        skuQuery.setItemId(itemId);
        skuQuery.setIsValid(ValidEnum.YES.getValue());
        System.out.println("skuQuery"+JsonUtils.toJSONString(skuQuery));
        List<SkuDomain> skuDomainList = skuService.getList(skuQuery);
        System.out.println("skuDomainList"+JsonUtils.toJSONString(skuDomainList));
        SkuDomain skuDomain =  skuDomainList.stream().filter(x-> JSONObject.fromObject(x.getSpecifications()).getLong("size")==sizeId).findFirst().orElse(null);
        if(skuDomain == null)
        {
            throw new ServiceException("参数错误");
        }
        skuDomain.setItemId(itemId);
        Integer num = addShoppingCartForm.getNum();
        if(shoppingCartType==2){
            ShoppingCartItemDomain queryShoppingCart = shoppingCartService.isExistInWish(customerDomain, skuDomain);
            if(queryShoppingCart!=null){
                return  successResult("心愿单中已经存在");
            }
        }
        shoppingCartService.addToCart(customerDomain, skuDomain, shoppingCartType,num);
        return  successResult("添加成功");
    }

    @RequestMapping(value = "removeFromCart" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult removeFromCart(Long shoppingCartItemId){
        shoppingCartService.removeFromCart(shoppingCartItemId);
        return  successResult("删除成功");
    }

    @RequestMapping(value = "removeFromWish" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult removeFromWish(@ModelAttribute AddShoppingCartForm addShoppingCartForm){
        Long accountId = UserContext.current().getAccountDomain().getId();
        Long customerId = customerService.getAccount(accountId).getId();

          /*获取SKU*/
        Long itemId = addShoppingCartForm.getItemId();
        Long sizeId = addShoppingCartForm.getSizeId();
        System.out.println("sizeId:"+sizeId+"\nitemId:"+itemId);

        SkuQuery skuQuery = new SkuQuery();
        skuQuery.setItemId(itemId);
        skuQuery.setIsValid(ValidEnum.YES.getValue());
        System.out.println("skuQuery"+JsonUtils.toJSONString(skuQuery));
        List<SkuDomain> skuDomainList = skuService.getList(skuQuery);
        SkuDomain skuDomain =  skuDomainList.stream().filter(x-> JSONObject.fromObject(x.getSpecifications()).getLong("size")==sizeId).findFirst().orElse(null);
        if(skuDomain == null)
        {
            throw new ServiceException("参数错误");
        }
        ShoppingCartItemQuery query = new ShoppingCartItemQuery();
        query.setCustomerId(customerId);
        query.setSkuId(skuDomain.getId());
        query.setShoppingCartType(2);
        query.setItemId(itemId);
        ShoppingCartItemDomain shoppingCartItemDomain = shoppingCartService.getFirst(query);
        shoppingCartService.removeFromCart(shoppingCartItemDomain.getId());
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
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        List<ShoppingCartItemDomain> cartList = shoppingCartService.listShoppingCartItemByCustomerId(customerDomain.getId(),1);
        shoppingCartService.withGoodsItem(cartList);
        ModelAndView mv = new ModelAndView("shoppingcart/list");
        mv.addObject("cartList",cartList);
        return mv;
    }

    @RequestMapping(value = "wishlist" ,method = RequestMethod.GET)
    public ModelAndView wishlist(){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        List<ShoppingCartItemDomain> wishList = shoppingCartService.listShoppingCartItemByCustomerId(customerDomain.getId(),2);
        shoppingCartService.withGoodsItem(wishList);
        ModelAndView mv = new ModelAndView("wishlist/list");
        mv.addObject("wishList",wishList);
        return mv;
    }

    @RequestMapping(value = "wishToCart" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult wishToCart(Long shoppingCartItemId){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        shoppingCartService.wishToCart(customerDomain,shoppingCartItemId);
        return  successResult("操作成功");
    }

    @RequestMapping(value = "wishToBoutique" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult wishToBoutique(Long shoppingCartItemId){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        shoppingCartService.wishToBoutique(customerDomain,shoppingCartItemId);
        return  successResult("操作成功");
    }

    @RequestMapping(value = "cartToBoutique" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult cartToBoutique(Long shoppingCartItemId){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
         ShoppingCartItemDomain shoppingCartItemDomain = shoppingCartService.get(shoppingCartItemId);
         shoppingCartItemDomain.setShoppingCartType(3);
         shoppingCartService.update(shoppingCartItemDomain);
        return  successResult("操作成功");
    }
    @RequestMapping(value = "cartToWish" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult cartToWish(Long shoppingCartItemId){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        ShoppingCartItemDomain shoppingCartItemDomain = shoppingCartService.get(shoppingCartItemId);
        shoppingCartItemDomain.setShoppingCartType(2);
        shoppingCartService.update(shoppingCartItemDomain);
        return  successResult("操作成功");
    }


    @RequestMapping(value = "createOrder" ,method = RequestMethod.GET)
    public ModelAndView createOrder(){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        List<ShoppingCartItemDomain> cartList = shoppingCartService.listShoppingCartItemByCustomerId(customerDomain.getId(),1);
        OrderDomain orderDomain =  shoppingCartService.createOrder(customerDomain,cartList);
        ModelAndView mv = new ModelAndView("checkout/confirm");
        mv.addObject("orderDomain",orderDomain);
        return mv;
    }


    @RequestMapping(value = "details" ,method = RequestMethod.GET)
    public ModelAndView settlement(){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        List<ShoppingCartItemDomain> cartList = shoppingCartService.listShoppingCartItemByCustomerId(customerDomain.getId(),1);
        ModelAndView mv = new ModelAndView("shoppingcart/orderInfo");
        mv.addObject("cartList",cartList);
        return mv;
    }


}
