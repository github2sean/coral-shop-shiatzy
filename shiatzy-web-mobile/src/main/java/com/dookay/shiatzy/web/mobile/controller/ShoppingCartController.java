package com.dookay.shiatzy.web.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.dookay.coral.common.enums.ValidEnum;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.utils.RandomUtils;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.*;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.query.PrototypeSpecificationOptionQuery;
import com.dookay.coral.shop.goods.query.SkuQuery;
import com.dookay.coral.shop.goods.service.*;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.shiatzy.web.mobile.form.AddShoppingCartForm;
import com.dookay.shiatzy.web.mobile.util.HistoryUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/4/27.
 */
@Controller
@RequestMapping("cart/")
public class ShoppingCartController extends  BaseController{
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
    @Autowired
    private IGoodsCategoryService goodsCategoryService;
    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;


    //Session 购物车
    private static final String SESSION_CART ="session_cart";
    private static final String IS_GUEST ="isGuest";

    @RequestMapping(value = "addToCart" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addToCart(@ModelAttribute AddShoppingCartForm addShoppingCartForm){

        UserContext userContext = UserContext.current();
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();

        Integer shoppingCartType = addShoppingCartForm.getType();
         /*获取SKU*/
        Long itemId = addShoppingCartForm.getItemId();
        Long sizeId = addShoppingCartForm.getSizeId();
        System.out.println("sizeId:"+sizeId+"\nitemId:"+itemId);
        SkuDomain skuDomain =  shoppingCartService.getSkubySizeAndItem(itemId,sizeId);
        GoodsDomain goodsDomain = goodsService.get(skuDomain.getGoodsId());
        if(skuDomain == null) {
            return  errorResult("无此商品");
        }

        if(userContext.isGuest()){//游客先保存到session中
            addShoppingCartForm.setId(RandomUtils.buildNo());
            List<AddShoppingCartForm> listCart = (List<AddShoppingCartForm>)session.getAttribute(SESSION_CART);
            System.out.println("addShoppingCartForm:"+JsonUtils.toJSONString(addShoppingCartForm));
            if(listCart!=null&&listCart.size()>0){
                if(!isExistInSessionCart(addShoppingCartForm.getType(),addShoppingCartForm.getItemId(),addShoppingCartForm.getSizeId())){
                    listCart.add(addShoppingCartForm);
                }else{
                    if(addShoppingCartForm.getType().equals(1)){
                        return  errorResult("该商品购物车中已存在");
                    }else if(addShoppingCartForm.getType().equals(3)){
                        return  errorResult("该商品精品店中已存在");
                    }else if(addShoppingCartForm.getType().equals(2)){
                        return  errorResult("该商品心愿单中已存在");
                    }

                }
            }else{
                listCart = new ArrayList<>();
                listCart.add(addShoppingCartForm);
            }
            if(shoppingCartType==ShoppingCartTypeEnum.RESERVATION.getValue()&&goodsDomain.getIsPre()==0){
                return  errorResult("该商品无法预约");
            }
            session.setAttribute(SESSION_CART,listCart);
            return successResult("添加成功");
        }
        Long accountId = userContext.getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        skuDomain.setItemId(itemId);
        Integer num = addShoppingCartForm.getNum();
        if(shoppingCartType==2){
            ShoppingCartItemDomain queryShoppingCart = shoppingCartService.isExistInWish(customerDomain, skuDomain);
            if(queryShoppingCart!=null){
                return  errorResult("心愿单中已经存在");
            }
        }
        shoppingCartService.addToCart(customerDomain, skuDomain, shoppingCartType,num);
        return  successResult("添加成功");
    }

    @RequestMapping(value = "querySessionCartNum" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult querySessionCartNum(Integer type){
        if(type==null){
            errorResult("参数为空");
        }
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        List<AddShoppingCartForm> listCart = (List<AddShoppingCartForm>)session.getAttribute(SESSION_CART);
        Integer retNum = 0;
        if(listCart!=null&&listCart.size()>0){
            for(AddShoppingCartForm form :listCart){
             if(form.getType()==type){
                 retNum +=1;
             }
            }
        }
        return  successResult("操作成功",retNum);
    }


    @RequestMapping(value = "removeFromCart" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult removeFromCart(Long shoppingCartItemId){
        if(shoppingCartItemId==null){
            return errorResult("参数为空");
        }
        shoppingCartService.removeFromCart(shoppingCartItemId);
        return  successResult("删除成功");
    }
    @RequestMapping(value = "removeFromSessionCart" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult removeFromSessionCart(String formId){
        if(formId==null){
            return errorResult("参数为空");
        }
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        List<AddShoppingCartForm> listCart  = (List<AddShoppingCartForm>)session.getAttribute(SESSION_CART);
        System.out.println("oldlistCart:"+JsonUtils.toJSONString(listCart));
        if(listCart!=null&&listCart.size()>0) {
            Iterator<AddShoppingCartForm> it = listCart.iterator();
            while(it.hasNext()){
                AddShoppingCartForm now = it.next();
                if(now.getId().equals(formId)){
                    System.out.println("dellistCart:"+JsonUtils.toJSONString(now));
                    it.remove();
                }
            }
        }
        System.out.println("nowlistCart:"+JsonUtils.toJSONString(listCart));
        session.setAttribute(SESSION_CART,listCart);
        return  successResult("删除成功");
    }

    public Boolean isExistInSessionCart(Integer cartType,Long itemId,Long sizeId) {
        Boolean ret = false;
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        List<AddShoppingCartForm> listCart  = (List<AddShoppingCartForm>)session.getAttribute(SESSION_CART);
        for(AddShoppingCartForm form :listCart){
            if( form.getType().equals(cartType)&&
                form.getItemId().equals(itemId)&&
                form.getSizeId().equals(sizeId)){
                ret = true;
            }
        }
        return ret;
    }


    @RequestMapping(value = "changeFromSessionCartType" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult changeFromSessionCartType(String formId,Integer goalType){
        if(formId==null){
            return errorResult("参数为空");
        }
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        List<AddShoppingCartForm> listCart  = (List<AddShoppingCartForm>)session.getAttribute(SESSION_CART);
        if(listCart!=null&&listCart.size()>0) {
            Iterator<AddShoppingCartForm> it = listCart.iterator();
            while(it.hasNext()){
                AddShoppingCartForm now = it.next();
                if(now.getId().equals(formId)){
                    if(goalType==ShoppingCartTypeEnum.RESERVATION.getValue() &&
                        isExistInSessionCart(ShoppingCartTypeEnum.RESERVATION.getValue(),now.getItemId(),now.getSizeId())
                    ){
                        return  errorResult("该商品精品店已存在");
                    }
                    if(goalType==ShoppingCartTypeEnum.WISH_LIST.getValue() &&
                            isExistInSessionCart(ShoppingCartTypeEnum.WISH_LIST.getValue(),now.getItemId(),now.getSizeId())
                            ){
                        return  errorResult("该商品心愿单已存在");
                    }
                    if(goalType==ShoppingCartTypeEnum.SHOPPING_CART.getValue() &&
                            isExistInSessionCart(ShoppingCartTypeEnum.SHOPPING_CART.getValue(),now.getItemId(),now.getSizeId())
                            ){
                        return  errorResult("该商品购物车已存在");
                    }
                    GoodsDomain goodsDomain = goodsService.get(goodsItemService.get(now.getItemId()).getGoodsId());
                    if(goalType==ShoppingCartTypeEnum.RESERVATION.getValue()&&goodsDomain.getIsPre()==0){
                        return  errorResult("该商品无法预约");
                    }
                    now.setType(goalType);
                }
            }
        }
        session.setAttribute(SESSION_CART,listCart);
        return  successResult("修改成功");
    }

    @RequestMapping(value = "removeFromSessionWish" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult removeFromSessionWish(@ModelAttribute AddShoppingCartForm addShoppingCartForm){

        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        List<AddShoppingCartForm> listCart  = (List<AddShoppingCartForm>)session.getAttribute(SESSION_CART);
        System.out.println("oldlistCart:"+JsonUtils.toJSONString(listCart));
        if(listCart!=null&&listCart.size()>0) {
            Iterator<AddShoppingCartForm> it = listCart.iterator();
            while(it.hasNext()){
                AddShoppingCartForm now = it.next();
                /*获取SKU*/
                Long itemId = addShoppingCartForm.getItemId();
                Long sizeId = addShoppingCartForm.getSizeId();
                if(now.getItemId().equals(itemId) && now.getSizeId().equals(sizeId) && now.getType()==2){
                    it.remove();
                    System.out.println("dellistCart:"+JsonUtils.toJSONString(now));
                }
            }
        }
        System.out.println("nowlistCart:"+JsonUtils.toJSONString(listCart));
        session.setAttribute(SESSION_CART,listCart);
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
        SkuDomain skuDomain =  shoppingCartService.getSkubySizeAndItem(itemId,sizeId);
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
        UserContext userContext = UserContext.current();
        if(userContext.isGuest()){
            HttpSession session = HttpContext.current().getRequest().getSession();
            List<AddShoppingCartForm> listCart  = (List<AddShoppingCartForm>)session.getAttribute(SESSION_CART);
            System.out.println("oldlistCart:"+JsonUtils.toJSONString(listCart));
            if(listCart!=null&&listCart.size()>0) {
                Iterator<AddShoppingCartForm> it = listCart.iterator();
                while(it.hasNext()){
                    AddShoppingCartForm now = it.next();
                    if(now.getId().equals(shoppingCartItemId+"")&& now.getType()==1){
                        now.setNum(num);
                        System.out.println("updateCartNum:"+JsonUtils.toJSONString(now));
                    }
                }
            }
            session.setAttribute(SESSION_CART,listCart);
            return  successResult("修改成功");
        }else{
            Long accountId = userContext.getAccountDomain().getId();
            CustomerDomain customerDomain = customerService.getAccount(accountId);
            shoppingCartService.updateShoppingCartItem(customerDomain,shoppingCartItemId,num);
            return  successResult("修改成功");
        }
    }

    @RequestMapping(value = "list" ,method = RequestMethod.GET)
    public ModelAndView list(){
        UserContext userContext = UserContext.current();
        List<ShoppingCartItemDomain> cartList = new ArrayList<>();
        if(userContext.isGuest()){
            //构建虚拟购物车商品
            HttpServletRequest request = HttpContext.current().getRequest();
            HttpSession session = request.getSession();
            List<AddShoppingCartForm> listCart  = (List<AddShoppingCartForm>)session.getAttribute(SESSION_CART);
            if(listCart!=null&&listCart.size()>0) {
                for(AddShoppingCartForm form:listCart){
                    if(form.getType()==ShoppingCartTypeEnum.SHOPPING_CART.getValue()){
                        SkuDomain skuDomain = shoppingCartService.getSkubySizeAndItem(form.getItemId(),form.getSizeId());
                        GoodsDomain goodsDomain = goodsService.get(skuDomain.getGoodsId());
                        GoodsItemDomain goodsItemDomain = goodsItemService.get(skuDomain.getItemId());
                        ShoppingCartItemDomain shoppingCartItemDomain = new ShoppingCartItemDomain();
                        shoppingCartItemDomain.setGoodsCode(goodsItemDomain.getGoodsNo());
                        shoppingCartItemDomain.setGoodsName(goodsDomain.getName());
                        shoppingCartItemDomain.setGoodsEnName(goodsDomain.getEnName());
                        shoppingCartItemDomain.setGoodsPrice(goodsItemDomain.getPrice());
                        shoppingCartItemDomain.setGoodsDisPrice(goodsDomain.getDisPrice());
                        shoppingCartItemDomain.setSkuId(skuDomain.getId());
                        shoppingCartItemDomain.setItemId(skuDomain.getItemId());
                        shoppingCartItemDomain.setShoppingCartType(form.getType());
                        shoppingCartItemDomain.setNum(form.getNum());
                        shoppingCartItemDomain.setSkuSpecifications(skuDomain.getSpecifications());
                        shoppingCartItemDomain.setFormId(form.getId());
                        cartList.add(shoppingCartItemDomain);
                    }
                }
                shoppingCartService.withGoodsItem(cartList);
                shoppingCartService.withSku(cartList);
                shoppingCartService.withSizeDomain(cartList);
            }
        }else {
            Long accountId = userContext.getAccountDomain().getId();
            CustomerDomain customerDomain = customerService.getAccount(accountId);
            cartList= shoppingCartService.listShoppingCartItemByCustomerId(customerDomain.getId(),1);
            shoppingCartService.withGoodsItem(cartList);
            shoppingCartService.withSku(cartList);
            shoppingCartService.withSizeDomain(cartList);
        }
        updateStock(cartList);
        ModelAndView mv = new ModelAndView("shoppingcart/list");
        mv.addObject("cartList",cartList);
        return mv;
    }

    private void updateStock(List<ShoppingCartItemDomain> cartList) {
        for(ShoppingCartItemDomain cartItemDomain:cartList){
                String productNo = cartItemDomain.getGoodsCode().split("\\s+")[0];//库存商品编号
                String color = cartItemDomain.getGoodsCode().split("\\s+")[1];//颜色标识
            cartItemDomain.setStock(goodsService.getTempStock(productNo,color,cartItemDomain.getSizeDomain().getName()));
        }
    }

    @RequestMapping(value = "wishlist" ,method = RequestMethod.GET)
    public ModelAndView wishlist(Long categoryId) throws UnsupportedEncodingException {
        if(UserContext.current() == null){
            String loginRef = "/passport/toLogin?ref=" + URLEncoder.encode(HttpContext.current().getRequest().getServletPath(), "UTF-8");
            return new ModelAndView("redirect:"+loginRef);
        }

        ModelAndView mv = new ModelAndView("wishlist/list");
        //获取全部愿望清单列表
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        List<ShoppingCartItemDomain> wishList = shoppingCartService.listShoppingCartItemByCustomerId(customerDomain.getId(),2);
        shoppingCartService.withGoodsItem(wishList);
        shoppingCartService.withSizeDomain(wishList);

        GoodsQuery query = new GoodsQuery();
        query.setIsPublished(ValidEnum.YES.getValue());
        List<Long> goodsIds = new ArrayList<>();
        for(ShoppingCartItemDomain line:wishList){
            GoodsDomain goodsDomain = goodsService.get(line.getGoodsItemDomain().getGoodsId());
            line.setGoodsDomain(goodsDomain);
            goodsIds.add(goodsDomain.getId());
        }
        updateStock(wishList);

        query.setIds(goodsIds);
        List<GoodsDomain> goodsList =  goodsService.getList(query);

        List<Long> categoryIdListAll = new ArrayList<>();
        for (GoodsDomain goodsDomain1 :goodsList){
            List<Long> categoryIdList = JSON.parseArray(goodsDomain1.getCategoryIds(),Long.class);
            goodsDomain1.setCategoryIdList(categoryIdList);
            categoryIdListAll.addAll(categoryIdList);
        }
        categoryIdListAll = categoryIdListAll.stream().distinct().collect(Collectors.toList());
        List<GoodsCategoryDomain> categoryDomainList = new ArrayList<>();

        GoodsCategoryQuery categoryQuery = new GoodsCategoryQuery();
        categoryQuery.setIds(categoryIdListAll);
        categoryDomainList = goodsCategoryService.getList(categoryQuery);


        //根据一级分类进行筛选
        GoodsCategoryDomain categoryDomain = goodsCategoryService.get(categoryId);
        if(categoryDomain!=null){

            List <GoodsCategoryDomain> list =null;
            List<Long> childCategoryIds = new ArrayList<>();
            if(categoryDomain.getLevel()==1){
                GoodsCategoryQuery goodsCategoryQuery = new GoodsCategoryQuery();
                goodsCategoryQuery.setParentId(categoryDomain.getId());
                goodsCategoryQuery.setLevel(2);
                goodsCategoryQuery.setIsValid(ValidEnum.YES.getValue());
                list  = goodsCategoryService.getList(goodsCategoryQuery);
                for (GoodsCategoryDomain line: list){
                    childCategoryIds.add(line.getId());
                }
            }
            mv.addObject("categoryDomain",goodsCategoryService.get(categoryId));
            List<ShoppingCartItemDomain> newWishList  = new ArrayList<>();
            for(ShoppingCartItemDomain line:wishList){
                if( CollectionUtils.containsAny(JsonUtils.toLongArray(line.getGoodsDomain().getCategoryIds()),childCategoryIds)){
                    newWishList.add(line);
                }
            }
            wishList = newWishList;
        }

        //获取所有一级分类id集合
        List<Long> firstCategoryIdsList = categoryDomainList.stream().filter(x->x.getIsValid()==ValidEnum.YES.getValue()).map(x->x.getParentId()).collect(Collectors.toList());
        GoodsCategoryQuery categoryQuery1 = new GoodsCategoryQuery();
        categoryQuery1.setIds(firstCategoryIdsList);
        List<GoodsCategoryDomain>   firstCategoryList = goodsCategoryService.getList(categoryQuery1);
        mv.addObject("categoryList",firstCategoryList);
        mv.addObject("wishList",wishList);

        //您也许也喜欢
        List<Long> categoryIds = new ArrayList<>(categoryIdListAll);
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setIsPublished(ValidEnum.YES.getValue());
        List<GoodsDomain> goodsListAll =  goodsService.getList(goodsQuery);
        for (GoodsDomain goodsDomain1 :goodsListAll){
            List<Long> categoryIdList = JSON.parseArray(goodsDomain1.getCategoryIds(),Long.class);
            goodsDomain1.setCategoryIdList(categoryIdList);
        }
        List<GoodsDomain> likeGoodsList = goodsListAll.stream().filter(x-> CollectionUtils.containsAny(x.getCategoryIdList(),categoryIds)).collect(Collectors.toList());

        Collections.shuffle(likeGoodsList);
        List<GoodsDomain> goodsDomainList = likeGoodsList.subList(0,4);
        goodsService.withGoodsItemList(goodsDomainList);
        mv.addObject("likeGoodsList",likeGoodsList);
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
         GoodsItemDomain goodsItemDomain = goodsItemService.get(shoppingCartItemDomain.getItemId());
         GoodsDomain goodsDomain = goodsService.get(goodsItemDomain.getGoodsId());
         if(goodsDomain.getIsPre()== ValidEnum.NO.getValue()){
             return errorResult("该商品不能预约");
         }
        ShoppingCartItemDomain queryDomain = shoppingCartService.isExistInCart(customerDomain, skuService.get(shoppingCartItemDomain.getSkuId()), ShoppingCartTypeEnum.RESERVATION.getValue());
        if(queryDomain!=null){
            return  errorResult("该商品精品店已存在");
        }
         shoppingCartItemDomain.setShoppingCartType(ShoppingCartTypeEnum.RESERVATION.getValue());
         shoppingCartService.update(shoppingCartItemDomain);
        return  successResult("操作成功");
    }

    @RequestMapping(value = "cartToWish" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult cartToWish(Long shoppingCartItemId){
        if(shoppingCartItemId==null){
            return errorResult("参数为空");
        }
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        ShoppingCartItemDomain shoppingCartItemDomain = shoppingCartService.get(shoppingCartItemId);
        SkuDomain skuDomain = shoppingCartService.getSkubySizeAndItem(shoppingCartItemDomain.getItemId(),JSONObject.fromObject(shoppingCartItemDomain.getSkuSpecifications()).getLong("size"));
        ShoppingCartItemDomain queryShoppingCart = shoppingCartService.isExistInWish(customerDomain, skuDomain);
        if(queryShoppingCart!=null){
            return  errorResult("心愿单中已经存在");
        }
        shoppingCartItemDomain.setShoppingCartType(2);
        shoppingCartService.update(shoppingCartItemDomain);
        return  successResult("操作成功");
    }

    @RequestMapping(value = "boutiqueToWish" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult boutiqueToWish(Long shoppingCartItemId){
        if(shoppingCartItemId==null){
            return errorResult("参数为空");
        }
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        ShoppingCartItemDomain shoppingCartItemDomain = shoppingCartService.get(shoppingCartItemId);
        SkuDomain skuDomain = shoppingCartService.getSkubySizeAndItem(shoppingCartItemDomain.getItemId(),JSONObject.fromObject(shoppingCartItemDomain.getSkuSpecifications()).getLong("size"));
        ShoppingCartItemDomain queryShoppingCart = shoppingCartService.isExistInWish(customerDomain, skuDomain);
        if(queryShoppingCart!=null){
            return  errorResult("心愿单中已经存在");
        }
        shoppingCartItemDomain.setShoppingCartType(2);
        shoppingCartService.update(shoppingCartItemDomain);
        updateBoutiqueListSession(shoppingCartItemId);
        return  successResult("操作成功");
    }

    @RequestMapping(value = "boutiqueToCart" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult boutiqueToCart(Long shoppingCartItemId){
        if(shoppingCartItemId==null){
            return errorResult("参数为空");
        }
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        ShoppingCartItemDomain shoppingCartItemDomain = shoppingCartService.get(shoppingCartItemId);
        ShoppingCartItemDomain queryDomain = shoppingCartService.isExistInCart(customerDomain, skuService.get(shoppingCartItemDomain.getSkuId()), ShoppingCartTypeEnum.SHOPPING_CART.getValue());
        if(queryDomain!=null){
            return  errorResult("该商品购物车已存在");
        }
        shoppingCartItemDomain.setShoppingCartType(1);
        shoppingCartService.update(shoppingCartItemDomain);
        updateBoutiqueListSession(shoppingCartItemId);
        return  successResult("操作成功");
    }

    @RequestMapping(value = "freshCartNum" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult freshCartNum(Integer num){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("cartNumber",num);
        System.out.println("cartNumber"+num);
        return  successResult("操作成功");
    }
    @RequestMapping(value = "getCartNum" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getCartNum(Integer type){
        if(type==null){
            return errorResult("参数为空");
        }
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        ShoppingCartItemQuery query = new ShoppingCartItemQuery();
        query.setCustomerId(customerDomain.getId());
        query.setShoppingCartType(type);
        Integer num = shoppingCartService.count(query);
        session.setAttribute("cartNumber",num);
        System.out.println("cartNumber"+num);
        return  successResult("查询成功",num);
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

    public void updateBoutiqueListSession(Long id){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        List<ShoppingCartItemDomain> cartList = (List<ShoppingCartItemDomain>)session.getAttribute("submitCartList");
        for(int i=0;cartList!=null&&cartList.size()>0&&i<cartList.size();i++){
            if(cartList.get(i).getId()==id){
                cartList.remove(i);
                break;
            }
        }
        session.setAttribute("submitCartList",cartList);
    }

}
