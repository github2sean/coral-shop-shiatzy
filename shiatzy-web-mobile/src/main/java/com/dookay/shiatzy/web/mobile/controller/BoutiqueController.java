package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.enums.ValidEnum;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.query.SkuQuery;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.ISkuService;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.coral.shop.store.domain.StoreCityDomain;
import com.dookay.coral.shop.store.domain.StoreCountryDomain;
import com.dookay.coral.shop.store.domain.StoreDomain;
import com.dookay.coral.shop.store.query.StoreCityQuery;
import com.dookay.coral.shop.store.query.StoreCountryQuery;
import com.dookay.coral.shop.store.query.StoreQuery;
import com.dookay.coral.shop.store.service.IStoreCityService;
import com.dookay.coral.shop.store.service.IStoreCountryService;
import com.dookay.coral.shop.store.service.IStoreService;
import com.dookay.shiatzy.web.mobile.form.AddShoppingCartForm;
import com.dookay.shiatzy.web.mobile.model.PreOderItem;
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
        List<PreOderItem> preOderItemList = new ArrayList<PreOderItem>();
        for (int i=0;cartList!=null&&cartList.size()>0 && i<cartList.size();i++){
            if(i!=0&&i%2!=0){
                continue;
            }
            PreOderItem preOderItem = i+1<cartList.size()?new PreOderItem(cartList.get(i),cartList.get(i+1)):new PreOderItem(cartList.get(i),null);
            preOderItemList.add(preOderItem);
        }
        ModelAndView mv = new ModelAndView("boutique/list");
        mv.addObject("preOderItemList",preOderItemList);
        return mv;
    }


    @RequestMapping(value = "addToBoutique" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addToBoutique(@ModelAttribute AddShoppingCartForm addShoppingCartForm){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);

        /*获取SKU*/
        Long itemId = addShoppingCartForm.getItemId();
        Long sizeId = addShoppingCartForm.getSizeId();

        SkuQuery skuQuery = new SkuQuery();
        skuQuery.setItemId(itemId);
        skuQuery.setIsValid(ValidEnum.YES.getValue());
        List<SkuDomain> skuDomainList = skuService.getList(skuQuery);

        SkuDomain skuDomain =  skuDomainList.stream().filter(x-> JSONObject.fromObject(x.getSpecifications()).getLong("size")==sizeId).findFirst().orElse(null);
        if(skuDomain == null)
        {
            throw new ServiceException("参数错误");
        }
        skuDomain.setItemId(itemId);
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


}
