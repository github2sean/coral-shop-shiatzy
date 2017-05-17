package com.dookay.shiatzy.web.mobile.controller;


        import com.alibaba.fastjson.JSONArray;
        import com.dookay.coral.common.json.JsonUtils;
        import com.dookay.coral.common.persistence.Query;
        import com.dookay.coral.common.persistence.criteria.QueryCriteria;
        import com.dookay.coral.common.persistence.pager.PageList;
        import com.dookay.coral.common.utils.CookieUtils;
        import com.dookay.coral.common.web.BaseController;
        import com.dookay.coral.common.web.HttpContext;
        import com.dookay.coral.shop.goods.domain.*;
        import com.dookay.coral.shop.goods.query.*;
        import com.dookay.coral.shop.goods.service.*;
        import com.dookay.shiatzy.web.mobile.form.QueryGoodsForm;
        import com.dookay.shiatzy.web.mobile.util.HistoryUtil;
        import net.sf.json.util.JSONUtils;
        import org.apache.commons.lang.StringUtils;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.servlet.ModelAndView;

        import javax.servlet.http.Cookie;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Objects;
        import java.util.stream.Collectors;

/**
 * Created by admin on 2017/4/25.
 */
@Controller
@RequestMapping("/goods/")
public class GoodsController extends BaseController{

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISkuService skuService;
    @Autowired
    private IGoodsPrototypeService goodsPrototypeService;
    @Autowired
    private IPrototypeAttributeService prototypeAttributeService;
    @Autowired
    private IPrototypeAttributeOptionService prototypeAttributeOptionService;
    @Autowired
    private IPrototypeSpecificationService prototypeSpecificationService;
    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
    @Autowired
    private IGoodsCategoryService goodsCategoryService;
    @Autowired
    private IGoodsColorService goodsColorService;
    @Autowired
    private IGoodsItemService goodsItemService;
    @Autowired
    private IGoodsAttributeValueService goodsAttributeValueService;

    private static Integer COLOR_FILTER = 1;
    private static Integer SIZE_FILTER = 0;
    private static Integer ATTR_FILTER = 2;
    private static Integer COLOR_AND_SIZE_FILTER = 3;

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public ModelAndView search(@ModelAttribute QueryGoodsForm queryGoodsForm){
        //查询页面进入模糊查询，主页面进入根据商品所有信息匹配查询
        GoodsQuery query = new GoodsQuery();
        query.setName(queryGoodsForm.getGoodsName());
        query.setCategoryId(queryGoodsForm.getCategoryId());
        query.setPrototypeId(queryGoodsForm.getPrototypeId());
        PageList<GoodsDomain> goodsList =  goodsService.getGoodsList(query);
        List<GoodsDomain> goodsNameList =  goodsService.getList(query);
        ModelAndView modelAndView;
        if(query.getCategoryId()==null && query.getPrototypeId()==null) {
            modelAndView = new ModelAndView("goods/namelist");
            goodsService.withGoodsItemList(goodsNameList);
            System.out.println(" goodsList:"+JsonUtils.toJSONString(goodsNameList));
            PageList<GoodsDomain> goodsDomainPageList = new PageList<>(goodsNameList,query.getPageIndex(),query.getPageSize(),goodsNameList.size());
            System.out.println(" list:"+JsonUtils.toJSONString(goodsDomainPageList));
            modelAndView.addObject("goodsDomainPageList",goodsDomainPageList);
        } else {
            PageList<SkuDomain> goodsSku = skuService.getPageList(query);
            System.out.print("goodsSku:" + JsonUtils.toJSONString(goodsSku));
            modelAndView = new ModelAndView("goods/list");
            modelAndView.addObject("goodsList", goodsList);
            modelAndView.addObject("goodsSku", goodsSku);
        }
        return modelAndView;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(GoodsQuery query){
        ModelAndView modelAndView = new ModelAndView("goods/list");
        Long categoryId = query.getCategoryId();//商品分类
        modelAndView.addObject("categoryId",categoryId);
        //商品列表
        query.setCategoryId(categoryId);
        query.setPriceWay(query.getPriceWay());
        List<GoodsDomain> goodsList =  goodsService.getList(query);
        goodsService.withGoodsItemList(goodsList);
        //商品分类
        GoodsCategoryDomain goodsCategoryDomain = goodsCategoryService.getCategory(categoryId);
        modelAndView.addObject("categoryName",goodsCategoryDomain.getName());
        //分类列表
        List<GoodsCategoryDomain> goodsCategoryDomainList = goodsCategoryService.listCategoryByParentId(goodsCategoryDomain.getParentId());
        modelAndView.addObject("categoryList",goodsCategoryDomainList);

        //材质列表
        //获得原型Ids
        List<Long> prototypeIds = new ArrayList<Long>();
        goodsList.forEach(x->prototypeIds.addAll(JsonUtils.toLongArray("["+x.getPrototypeId()+"]")));
        List<Long> newPrototypeIds = prototypeIds.stream().distinct().collect(Collectors.toList());
        //获得原型id属性
        PrototypeAttributeQuery prototypeAttributeQuery = new PrototypeAttributeQuery();
        prototypeAttributeQuery.setPrototypeIds(newPrototypeIds);
        List<PrototypeAttributeDomain> prototypeAttributeDomainList = prototypeAttributeService.getList(prototypeAttributeQuery);
        List<PrototypeAttributeDomain> newAttributeDomainList = prototypeAttributeDomainList.stream().distinct().collect(Collectors.toList());
        //获得原型属性选项
        List<Long> prototypeAttributeIds = new ArrayList<>();
        for (PrototypeAttributeDomain attributeDomain:newAttributeDomainList) {
            prototypeAttributeIds.add(attributeDomain.getPrototypeId());
        }
        List<Long> newPrototypeAttributeIds = prototypeAttributeIds.stream().distinct().collect(Collectors.toList());
        PrototypeAttributeOptionQuery prototypeAttributeOptionQuery = new PrototypeAttributeOptionQuery();
        prototypeAttributeOptionQuery.setPrototypeAttributeIds(newPrototypeAttributeIds);
        List<PrototypeAttributeOptionDomain> prototypeAttributeOptionDomainList = prototypeAttributeOptionService.getList(prototypeAttributeOptionQuery);
        modelAndView.addObject("attributeList",prototypeAttributeOptionDomainList);
        System.out.println("attributeList:"+JsonUtils.toJSONString(prototypeAttributeOptionDomainList));

        //颜色列表
        List<Long> colorIds = new ArrayList<>();
        goodsList.forEach(x->colorIds.addAll(JsonUtils.toLongArray(x.getColorIds())));
        List<Long> newColorIds = colorIds.stream().distinct().collect(Collectors.toList());
        GoodsColorQuery goodsColorQuery = new GoodsColorQuery();
        goodsColorQuery.setIds(newColorIds);
        List<GoodsColorDomain> goodsColorDomainList = goodsColorService.getList(goodsColorQuery);
        modelAndView.addObject("colorList",goodsColorDomainList);
        //尺寸列表
        List<Long> sizeIds = new ArrayList<>();
        goodsList.forEach(x->sizeIds.addAll(JsonUtils.toLongArray(x.getSizeIds())));
        List<Long> newSizeIds = sizeIds.stream().distinct().collect(Collectors.toList());
        PrototypeSpecificationOptionQuery prototypeSpecificationOptionQuery = new PrototypeSpecificationOptionQuery();
        prototypeSpecificationOptionQuery.setIds(newSizeIds);
        List<PrototypeSpecificationOptionDomain> sizeList = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery);
        modelAndView.addObject("sizeList",sizeList);

        //颜色尺寸材质过滤
        List<Long> queryColorIds= query.getColorIds();
        List<Long> querySizeIds = query.getSizeIds();
        List<Long> queryAttributeIds=query.getAttributeIds();
        System.out.println("colorIds:"+queryColorIds);
        System.out.println("sizeIds:"+JsonUtils.toJSONString(querySizeIds));
        System.out.println("attributeIds:"+JsonUtils.toJSONString(queryAttributeIds));

        // 过滤goodsList
        Boolean colorBoolean = queryColorIds!=null && queryColorIds.size()>0;//颜色
        Boolean sizeBoolean = querySizeIds!=null && querySizeIds.size()>0;//尺寸
        Boolean attributeBoolean = queryAttributeIds!=null && queryAttributeIds.size()>0; //材质
        if(colorBoolean && !sizeBoolean && !attributeBoolean){ //颜色不为空，其他都为空
            goodsList = filterGoods(goodsList,COLOR_FILTER,queryColorIds);
        }else if(colorBoolean && sizeBoolean && !attributeBoolean){//材质为空，颜色不为空，尺寸不为空
            goodsList = filterGoods(goodsList,COLOR_FILTER,queryColorIds);
            goodsList = filterGoods(goodsList,SIZE_FILTER,querySizeIds);
        }else if(colorBoolean && sizeBoolean && attributeBoolean){//都不为空
            goodsList = filterGoods(goodsList,COLOR_FILTER,queryColorIds);
            goodsList = filterGoods(goodsList,SIZE_FILTER,querySizeIds);
            goodsList = attribute(goodsList,ATTR_FILTER,queryAttributeIds);
        }else if(!colorBoolean && sizeBoolean && !attributeBoolean){ //尺寸不为空，其他都为空
            goodsList = filterGoods(goodsList,SIZE_FILTER,querySizeIds);
        }else if(!colorBoolean && sizeBoolean && attributeBoolean){//颜色为空，尺寸不为空，材质不为空
            goodsList = filterGoods(goodsList,SIZE_FILTER,querySizeIds);
            goodsList = attribute(goodsList,ATTR_FILTER,queryAttributeIds);
        }else if(!colorBoolean && !sizeBoolean && attributeBoolean){//材质不为空，其他都为空
            goodsList = attribute(goodsList,ATTR_FILTER,queryAttributeIds);
        }else if(colorBoolean && !sizeBoolean && attributeBoolean){//尺寸为空 ，颜色不为空，材质不为空
            goodsList = filterGoods(goodsList,COLOR_FILTER,queryColorIds);
            goodsList = attribute(goodsList, ATTR_FILTER, queryAttributeIds);

        }

        PageList<GoodsDomain> goodsDomainPageList = new PageList<>(goodsList,query.getPageIndex(),query.getPageSize(),goodsList.size());
        modelAndView.addObject("goodsDomainPageList",goodsDomainPageList);
        return modelAndView;
    }
    @RequestMapping(value = "details/{itemId}" ,method = RequestMethod.GET)
    public ModelAndView details(@PathVariable Long itemId){
        //未发布，跳转到404页面

        //商品数据校验，没有sku，跳转到404页面

        //准备商品数据

        GoodsItemDomain goodsItemDomain =  goodsItemService.get(itemId);
        Long goodsId = goodsItemDomain.getGoodsId();
        goodsItemService.withColor(goodsItemDomain);
        GoodsDomain goodsDomain = goodsService.get(goodsId);//得到商品
        goodsService.withGoodsItemList(goodsDomain);

        //调用工具类，把浏览记录存入Cookie
        HistoryUtil.setHistory(goodsId);
        //把获取的记录存到List集合
        List<GoodsDomain> historyList = HistoryUtil.getHistory();
        goodsService.withGoodsItemList(historyList);

        List<Long> sizeIds =JsonUtils.toLongArray(goodsDomain.getSizeIds());
        PrototypeSpecificationOptionQuery prototypeSpecificationOptionQuery = new PrototypeSpecificationOptionQuery();
        prototypeSpecificationOptionQuery.setIds(sizeIds);
        List<PrototypeSpecificationOptionDomain> sizeList = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery);

        ModelAndView mv = new ModelAndView("goods/details");
        mv.addObject("goodsItemDomain",goodsItemDomain);
        mv.addObject("goodsDomain",goodsDomain);
        mv.addObject("sizeList",sizeList);
        mv.addObject("historyList",historyList);
        return mv;
    }



    public  List<GoodsDomain> filterGoods(List<GoodsDomain> goodsList,Integer type,List<Long> data){
        List<GoodsDomain> returnList  = new ArrayList<GoodsDomain>();
        List<Long> allSizeIds=null;
        for (GoodsDomain goodsDomain : goodsList){
            if( type==SIZE_FILTER )
            {
                allSizeIds =JsonUtils.toLongArray(goodsDomain.getSizeIds());
            }else if(type==COLOR_FILTER)
            {
                allSizeIds =JsonUtils.toLongArray(goodsDomain.getColorIds());
            }
            for (Long filterSizeIds:data){
                for(Long nowSizeIds:allSizeIds){
                    if(Objects.equals(nowSizeIds, filterSizeIds)){
                        {
                            returnList.add(goodsDomain);
                            System.out.println("nowGoodsDomain:" + JsonUtils.toJSONString(goodsDomain));
                        }
                    }
                }
            }
        }
        return returnList;
    }
    //材质筛选
    public  List<GoodsDomain> attribute(List<GoodsDomain> goodsList,Integer type,List<Long> data){
        List<GoodsDomain> list  = new ArrayList<GoodsDomain>();
        for (GoodsDomain goodsDomain : goodsList){
            GoodsAttributeValueDomain goodsAttributeValueDomain =goodsAttributeValueService.get(goodsDomain.getId());//获取材质的id
            if(type == ATTR_FILTER )
            {
                for (Long attribute:data){
                    for(Long newAttributeIds:goodsDomain.getAttributeIds()){
                        if(Objects.equals(newAttributeIds, attribute)){
                            {
                                if(goodsAttributeValueDomain.getPrototypeAttributeOptionId() == goodsDomain.getId())
                                    list.add(goodsDomain);
                                System.out.println("nowGoodsDomain:" + JsonUtils.toJSONString(goodsDomain));
                            }
                        }
                    }
                }
            }

        }
        return list;
    }
}
