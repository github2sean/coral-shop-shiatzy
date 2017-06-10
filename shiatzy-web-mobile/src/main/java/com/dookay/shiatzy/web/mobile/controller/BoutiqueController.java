package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.enums.ValidEnum;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.query.SkuQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.ISkuService;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Autowired
    private IGoodsItemService goodsItemService;



    private static int  SHOPPINGCART_TYPE = 3;//精品店
    //Session 购物车
    private static final String SESSION_CART ="session_cart";


    @RequestMapping(value = "list" ,method = RequestMethod.GET)
    public ModelAndView list(){
        UserContext userContext = UserContext.current();

        List<ShoppingCartItemDomain> sessionCartList = new ArrayList<>();
        if(userContext.isGuest()){
            //构建虚拟购物车商品
            HttpServletRequest request = HttpContext.current().getRequest();
            HttpSession session = request.getSession();
            List<AddShoppingCartForm> listCart  = (List<AddShoppingCartForm>)session.getAttribute(SESSION_CART);
            if(listCart!=null&&listCart.size()>0) {
                for(AddShoppingCartForm form:listCart){
                    System.out.println("from:"+form);
                    if(form.getType()== ShoppingCartTypeEnum.RESERVATION.getValue()){
                        SkuDomain skuDomain = shoppingCartService.getSkubySizeAndItem(form.getItemId(),form.getSizeId());
                           if(skuDomain==null){
                               continue;
                           }
                        GoodsDomain goodsDomain = goodsService.get(skuDomain.getGoodsId());
                        GoodsItemDomain goodsItemDomain = goodsItemService.get(skuDomain.getItemId());
                        ShoppingCartItemDomain shoppingCartItemDomain = new ShoppingCartItemDomain();
                        shoppingCartItemDomain.setGoodsCode(goodsDomain.getCode());
                        shoppingCartItemDomain.setGoodsName(goodsDomain.getName());
                        shoppingCartItemDomain.setGoodsEnName(goodsDomain.getEnName());
                        shoppingCartItemDomain.setGoodsPrice(goodsItemDomain.getPrice());
                        shoppingCartItemDomain.setSkuId(skuDomain.getId());
                        shoppingCartItemDomain.setItemId(skuDomain.getItemId());
                        shoppingCartItemDomain.setShoppingCartType(form.getType());
                        shoppingCartItemDomain.setNum(form.getNum());
                        shoppingCartItemDomain.setSkuSpecifications(skuDomain.getSpecifications());
                        shoppingCartItemDomain.setFormId(form.getId());
                        System.out.println("shoppingCartItemDomain:"+shoppingCartItemDomain);
                        sessionCartList.add(shoppingCartItemDomain);
                        shoppingCartService.withGoodsItem(sessionCartList);
                        shoppingCartService.withSku(sessionCartList);
                    }
                }
            }
        }
        List<ShoppingCartItemDomain> cartList = userContext.isGuest()?
                sessionCartList:shoppingCartService.listShoppingCartItemByCustomerId(customerService.getAccount(userContext.getAccountDomain().getId()).getId(),SHOPPINGCART_TYPE);
        shoppingCartService.withGoodsItem(cartList);
        List<PreOderItem> preOderItemList = new ArrayList<PreOderItem>();
        for (int i=0;cartList!=null&&cartList.size()>0 && i<cartList.size();i++){
            if(i!=0&&i%2!=0){
                continue;
            }
            PreOderItem preOderItem = i+1<cartList.size()?new PreOderItem(cartList.get(i),cartList.get(i+1)):new PreOderItem(cartList.get(i),null);
            preOderItemList.add(preOderItem);
        }
        System.out.println("cartList:"+JsonUtils.toJSONString(cartList));
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
        System.out.println("sizeId:"+sizeId+"\nitemId:"+itemId);
        SkuQuery skuQuery = new SkuQuery();
        skuQuery.setItemId(itemId);
        skuQuery.setIsValid(ValidEnum.YES.getValue());
        System.out.println("skuQuery"+JsonUtils.toJSONString(skuQuery));
        List<SkuDomain> skuDomainList = skuService.getList(skuQuery);
        System.out.println("skuDomainList:"+JsonUtils.toJSONString(skuDomainList));
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
