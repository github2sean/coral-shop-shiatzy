package com.dookay.shiatzy.web.mobile.controller;


        import com.alibaba.fastjson.JSON;
        import com.dookay.coral.common.enums.ValidEnum;
        import com.dookay.coral.common.json.JsonUtils;
        import com.dookay.coral.common.persistence.Query;
        import com.dookay.coral.common.persistence.pager.PageList;
        import com.dookay.coral.common.web.BaseController;
        import com.dookay.coral.common.web.HttpContext;
        import com.dookay.coral.common.web.JsonResult;
        import com.dookay.coral.shop.goods.domain.*;
        import com.dookay.coral.shop.goods.query.*;
        import com.dookay.coral.shop.goods.service.*;
        import com.dookay.shiatzy.web.mobile.form.QueryGoodsForm;
        import com.dookay.shiatzy.web.mobile.util.HistoryUtil;
        import org.apache.commons.collections.CollectionUtils;
        import org.apache.commons.lang.StringUtils;
        import org.apache.shiro.session.Session;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.servlet.ModelAndView;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpSession;
        import java.util.*;
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
    private IGoodsColorSeriesService goodsColorSeriesService;
    @Autowired
    private IGoodsItemService goodsItemService;
    @Autowired
    private IGoodsItemFormatService goodsItemFormatService;
    @Autowired
    private IGoodsAttributeValueService goodsAttributeValueService;
    @Autowired
    private IGoodsSkinService goodsSkinService;

    private static Integer COLOR_FILTER = 1;
    private static Integer SIZE_FILTER = 0;
    private static Integer ATTR_FILTER = 2;
    private static Integer COLOR_AND_SIZE_FILTER = 3;
    private static String COLOR_IDS = "colorIds";
    private static String SIZE_IDS = "sizeIds";
    private static String ATTR_IDS = "attrIds";
    private static Integer HIGN_TO_LOW = 1;
    private static Integer LOW_TO_HIGN= 0;

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelAndView search(@ModelAttribute QueryGoodsForm queryGoodsForm,Integer priceWay){
        //查询页面进入模糊查询，主页面进入根据商品所有信息匹配查询
        GoodsQuery query = new GoodsQuery();
        query.setName(queryGoodsForm.getGoodsName());
        query.setDesc(false);
        query.setPageSize(20);
        List<GoodsDomain> goodsList =  goodsService.getList(query);
        ModelAndView modelAndView = new ModelAndView("goods/namelist");
        List<GoodsCategoryDomain> categoryList = goodsService.getAll2Category(goodsList);
        if(query.getCategoryId()==null && query.getPrototypeId()==null) {
            goodsService.withGoodsItemList(goodsList);
            modelAndView.addObject("keyword",query.getName());
            modelAndView.addObject("categoryList",categoryList);
            //材质列表
            modelAndView.addObject("attributeList",getAttributes(goodsList));
            //颜色列表
            modelAndView.addObject("colorList",getColors(goodsList));
            //尺寸列表
            modelAndView.addObject("sizeList",getSizes(goodsList));

            //过滤开始
            doFilter(query,modelAndView,goodsList,priceWay);

            goodsService.withSizeDomain(goodsList);
        }
        return modelAndView;
    }


    public List<GoodsDomain> filterParam(List<GoodsDomain> goodsList,List<Long> queryColorIds, List<Long> querySizeIds,List<Long> querySkinIds){

        if(queryColorIds.size()>0){
            goodsList  = goodsList.stream().filter(x->CollectionUtils.containsAny(JsonUtils.toLongArray(x.getColorIds()),queryColorIds)).collect(Collectors.toList());
        }
        if(querySizeIds.size()>0){
            goodsList  = goodsList.stream().filter(x->CollectionUtils.containsAny(JsonUtils.toLongArray(x.getSizeIds()),querySizeIds)).collect(Collectors.toList());
        }

        if(querySkinIds.size()>0){
            goodsList  = goodsList.stream().filter(x->CollectionUtils.containsAny(JsonUtils.toLongArray(x.getSkinIds()),querySkinIds)).collect(Collectors.toList());
        }

        return goodsList;
    }

    public List<PrototypeAttributeOptionDomain> getAttributes(List<GoodsDomain> goodsList){
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
        return prototypeAttributeOptionDomainList;
    }

    public List<PrototypeSpecificationOptionDomain> getSizes(List<GoodsDomain> goodsList){
        List<Long> sizeIds = new ArrayList<>();
        goodsList.forEach(x->sizeIds.addAll(JsonUtils.toLongArray(x.getSizeIds())));
        List<Long> newSizeIds = sizeIds.stream().distinct().collect(Collectors.toList());
        PrototypeSpecificationOptionQuery prototypeSpecificationOptionQuery = new PrototypeSpecificationOptionQuery();
        prototypeSpecificationOptionQuery.setIds(newSizeIds);
        Comparator<PrototypeSpecificationOptionDomain> comparator = Comparator.comparing(PrototypeSpecificationOptionDomain::getName);
        List<PrototypeSpecificationOptionDomain> sizeList
                = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery).stream().sorted(comparator).collect(Collectors.toList());
        return sizeList;
    }


    public List<GoodsColorSeriesDomain> getColorSeriesList(List<GoodsDomain> goodsList){
        List<Long> colorIds = new ArrayList<>();
        goodsList.forEach(x->colorIds.addAll(JsonUtils.toLongArray(x.getColorIds())));
        List<Long> newColorIds = colorIds.stream().distinct().collect(Collectors.toList());
        GoodsColorQuery goodsColorQuery = new GoodsColorQuery();
        goodsColorQuery.setIds(newColorIds);
        List<GoodsColorDomain> goodsColorDomainList = goodsColorService.getList(goodsColorQuery);
        List<Long> colorSeriesIds = goodsColorDomainList.stream().map(GoodsColorDomain::getSeriesId).distinct().collect(Collectors.toList());
        GoodsColorSeriesQuery colorSeriesQuery = new GoodsColorSeriesQuery();
        colorSeriesQuery.setIds(colorSeriesIds);
        List<GoodsColorSeriesDomain> goodsColorSeriesDomainList = goodsColorSeriesService.getList(colorSeriesQuery);
        return goodsColorSeriesDomainList;
    }

    public   List<GoodsColorDomain> getColors(List<GoodsDomain> goodsList){
        List<Long> colorIds = new ArrayList<>();
        goodsList.forEach(x->colorIds.addAll(JsonUtils.toLongArray(x.getColorIds())));
        List<Long> newColorIds = colorIds.stream().distinct().collect(Collectors.toList());
        GoodsColorQuery goodsColorQuery = new GoodsColorQuery();
        goodsColorQuery.setIds(newColorIds);
        List<GoodsColorDomain> goodsColorDomainList = goodsColorService.getList(goodsColorQuery);
        return goodsColorDomainList;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(GoodsQuery query,Integer priceWay){
        query.setOrderBy("rank");
        query.setPageSize(20);
        ModelAndView modelAndView = new ModelAndView("goods/list");
        Long categoryId = query.getCategoryId();//商品分类
        modelAndView.addObject("categoryId",categoryId);

        //商品分类
        GoodsCategoryDomain goodsCategoryDomain = goodsCategoryService.getCategory(categoryId);
        modelAndView.addObject("goodsCategoryDomain",goodsCategoryDomain);
        List <GoodsCategoryDomain> list =null;
        if(goodsCategoryDomain.getLevel()==1){
            List<Long> childCategoryIds = new ArrayList<>();
            GoodsCategoryQuery goodsCategoryQuery = new GoodsCategoryQuery();
            goodsCategoryQuery.setParentId(goodsCategoryDomain.getId());
            goodsCategoryQuery.setLevel(2);
            goodsCategoryQuery.setIsValid(ValidEnum.YES.getValue());
            list  = goodsCategoryService.getList(goodsCategoryQuery);
            for (GoodsCategoryDomain line: list){
                childCategoryIds.add(line.getId());
            }
            query.setCategoryIds(childCategoryIds);
        }else{
            query.setCategoryId(categoryId);
        }

        //当前分类下的商品列表
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setIsPublished(ValidEnum.YES.getValue());
        List<GoodsDomain> goodsListAll =  goodsService.getList(goodsQuery);
        for (GoodsDomain goodsDomain :goodsListAll){
            List<Long> categoryIdList = JSON.parseArray(goodsDomain.getCategoryIds(),Long.class);
            goodsDomain.setCategoryIdList(categoryIdList);
        }
        if(query.getCategoryIds()==null){
            goodsListAll  =goodsListAll.stream().filter(x->x.getCategoryIdList().contains(query.getCategoryId())).collect(Collectors.toList());
        }else{
            goodsListAll  =goodsListAll.stream().filter(x->
                    CollectionUtils.containsAny(x.getCategoryIdList(),query.getCategoryIds())).collect(Collectors.toList());
        }

        //过滤开始
        doFilter(query,modelAndView,goodsListAll,priceWay);

        goodsService.withGoodsItemList(goodsListAll);
        goodsService.withSizeDomain(goodsListAll);
        modelAndView.addObject("query",query);

        //同级分类列表
        List<GoodsCategoryDomain> goodsCategoryDomainList =list!=null&&list.size()>0?list:goodsCategoryService.listCategoryByParentId(goodsCategoryDomain.getParentId(),false);
        modelAndView.addObject("categoryList",goodsCategoryDomainList);

        //材质列表
        List<Long> skinIds = new ArrayList<>();
        for(GoodsDomain goodsDomain:goodsListAll){
            if(StringUtils.isNotBlank(goodsDomain.getSkinIds()) ){
                skinIds.addAll(JsonUtils.toLongArray(goodsDomain.getSkinIds()));
            }
        }
        List<Long> newSkinIds = skinIds.stream().distinct().collect(Collectors.toList());
        GoodsSkinQuery goodsSkinQuery = new GoodsSkinQuery();
        goodsSkinQuery.setIds(newSkinIds);
        List<GoodsSkinDomain> goodsSkinDomainList = goodsSkinService.getList(goodsSkinQuery);
        modelAndView.addObject("skinList",goodsSkinDomainList);

        //颜色列表
        modelAndView.addObject("colorList",getColors(goodsListAll));

        //色系列表
        modelAndView.addObject("colorSeriesList",getColorSeriesList(goodsListAll));

        //尺寸列表
        modelAndView.addObject("sizeList",getSizes(goodsListAll));

        return modelAndView;
    }

    public void doFilter(GoodsQuery query,ModelAndView modelAndView,List<GoodsDomain> goodsList,Integer priceWay){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        //清空session中筛选条件
        session.setAttribute(COLOR_IDS,null);
        session.setAttribute(ATTR_IDS,null);
        session.setAttribute(SIZE_IDS,null);
        session.setAttribute("priceWay",priceWay);
        //颜色尺寸材质过滤
        List<Long> queryColorSeriesIds = query.getColorSeriesIds();
        GoodsColorQuery goodsColorQuery = new GoodsColorQuery();
        goodsColorQuery.setSeriesIds(queryColorSeriesIds);
        List<GoodsColorDomain> goodsColorDomainList = goodsColorService.getList(goodsColorQuery);

        List<Long> queryColorIds= goodsColorDomainList.stream().distinct().map(GoodsColorDomain::getId).collect(Collectors.toList());
        List<Long> querySizeIds = query.getSizeIds();
        List<Long> queryAttributeIds=query.getAttributeIds();
        //当前的筛选条件存入session中
        if(queryColorIds!=null&&queryColorIds.size()>0){
            session.setAttribute(COLOR_IDS,queryColorIds);
        }
        if(queryAttributeIds!=null&&queryAttributeIds.size()>0){
            session.setAttribute(ATTR_IDS,queryAttributeIds);
        }
        if(querySizeIds!=null&&querySizeIds.size()>0){
            session.setAttribute(SIZE_IDS,querySizeIds);
        }

        if(queryColorIds == null)
            queryColorIds = new ArrayList<>();
        if(querySizeIds == null)
            querySizeIds = new ArrayList<>();
        if(queryAttributeIds == null)
            queryAttributeIds = new ArrayList<>();
        //筛选
        goodsList = filterParam(goodsList,queryColorIds,querySizeIds,queryAttributeIds);
        goodsService.withGoodsItemList(goodsList);
        //排序
        if (priceWay != null) {
            if (Objects.equals(priceWay, HIGN_TO_LOW)) {
                sortByPrice(goodsList, HIGN_TO_LOW);
            }
            if (Objects.equals(priceWay, LOW_TO_HIGN)) {
                sortByPrice(goodsList, LOW_TO_HIGN);
            }
        }
        //分页
        int totalRecord = goodsList.size();
        Integer skip = query.getPageIndex() * query.getPageSize() - query.getPageSize();
        List<GoodsDomain> goodsList2 =goodsList.stream().skip(skip).limit(query.getPageSize()).collect(Collectors.toList());
        PageList<GoodsDomain> goodsDomainPageList = new PageList<>(goodsList2,query.getPageIndex(),query.getPageSize(),totalRecord);
        modelAndView.addObject("goodsDomainPageList",goodsDomainPageList);
    }

    @RequestMapping(value = "details/{itemId}" ,method = RequestMethod.GET)
    public ModelAndView details(@PathVariable Long itemId){
        ModelAndView mv = new ModelAndView("goods/details");
        //未发布，跳转到404页面

        //商品数据校验，没有sku，跳转到404页面

        //准备商品数据
        GoodsItemDomain goodsItemDomain =  goodsItemService.get(itemId);
        Long goodsId = goodsItemDomain.getGoodsId();
        goodsItemService.withColor(goodsItemDomain);
        GoodsDomain goodsDomain = goodsService.get(goodsId);//得到商品
        goodsService.withGoodsItemList(goodsDomain);

        GoodsItemFormatQuery goodsItemFormatQuery = new GoodsItemFormatQuery();
        goodsItemFormatQuery.setItemId(goodsItemDomain.getId());
        List<GoodsItemFormatDomain> goodsItemFormatDomainList = goodsItemFormatService.getList(goodsItemFormatQuery);
        mv.addObject("goodsItemDomain",goodsItemDomain);
        mv.addObject("goodsDomain",goodsDomain);
        mv.addObject("goodsItemFormatList",goodsItemFormatDomainList);
        //您也许也喜欢
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setIsPublished(ValidEnum.YES.getValue());
        List<GoodsDomain> goodsListAll =  goodsService.getList(goodsQuery);
        Collections.shuffle(goodsListAll);
        List<GoodsDomain> goodsDomainList = goodsListAll.subList(0,4);
        goodsService.withGoodsItemList(goodsDomainList);
        mv.addObject("likeGoodsList",goodsDomainList);
        //尺寸
        List<Long> sizeIds =JsonUtils.toLongArray(goodsDomain.getSizeIds());
        PrototypeSpecificationOptionQuery prototypeSpecificationOptionQuery = new PrototypeSpecificationOptionQuery();
        prototypeSpecificationOptionQuery.setIds(sizeIds);
        Comparator<PrototypeSpecificationOptionDomain> comparator = Comparator.comparing(PrototypeSpecificationOptionDomain::getName);

        List<PrototypeSpecificationOptionDomain> sizeList = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery).stream().sorted(comparator).collect(Collectors.toList());
        for(PrototypeSpecificationOptionDomain sizeDomain:sizeList){
            String productNo = goodsItemDomain.getGoodsNo().split("\\s+")[0];//库存商品编号
            String color = goodsItemDomain.getGoodsNo().split("\\s+")[1];//颜色标识
            sizeDomain.setStock(goodsService.getTempStock(productNo,color,sizeDomain.getName()));
        }

        mv.addObject("sizeList",sizeList);

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
      return  goodsList.stream().filter(x->CollectionUtils.containsAny(JsonUtils.toLongArray(x.getSkinIds()),data)).collect(Collectors.toList());
    }

    //价格筛选
    public  List<GoodsDomain> price(List<GoodsDomain> goodsList,Integer type){
        List<GoodsDomain> list  = new ArrayList<GoodsDomain>();
        GoodsQuery query =new GoodsQuery();
        if(type == null||type == 0){
            query.setOrderBy("price");
            query.setDesc(Boolean.TRUE);
        }else{
            query.setOrderBy("price");
            query.setDesc(Boolean.FALSE);
        }
        List<GoodsDomain> newList=goodsService.getList(query);
        for (GoodsDomain goodsDomain : goodsList){
            for (GoodsDomain goods:newList) {
                    if (goods.getId()== goodsDomain.getId()) {
                        list.add(goodsDomain);
                        System.out.println("nowGoodsDomain:" + JsonUtils.toJSONString(goodsDomain));
                    }
            }
        }
        return list;
    }

    //价格排序 是跟页面一致用第一个商品的价格排序
    public List<GoodsDomain> sortByPrice(List<GoodsDomain> goodsDomainList,Integer sortType){
        List<GoodsDomain> list  = goodsDomainList;

        Comparator comparator = new Comparator<GoodsDomain>() {
            @Override
            public int compare(GoodsDomain good, GoodsDomain good2) {

                GoodsItemDomain itemDomain =   good.getGoodsItemList().get(0);
                GoodsItemDomain itemDomain2 =  good2.getGoodsItemList().get(0);
                Double price1 = itemDomain.getDiscountPrice()!=null?itemDomain.getDiscountPrice():itemDomain.getPrice();
                Double price2 = itemDomain2.getDiscountPrice()!=null?itemDomain2.getDiscountPrice():itemDomain2.getPrice();
                if(price1>price2){
                    return sortType==HIGN_TO_LOW?1:-1;
                }
                if(price1==price2){
                    return 0;
                }
                return sortType==HIGN_TO_LOW?-1:1;
            }
        };
        list.sort(comparator);
        return list;
    }
}
