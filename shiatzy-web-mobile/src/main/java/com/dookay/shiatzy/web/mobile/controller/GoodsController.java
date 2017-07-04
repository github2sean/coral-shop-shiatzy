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
    private IGoodsItemService goodsItemService;
    @Autowired
    private IGoodsAttributeValueService goodsAttributeValueService;

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
        query.setCategoryId(queryGoodsForm.getCategoryId());
        query.setPrototypeId(queryGoodsForm.getPrototypeId());
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
            doFilter(query,modelAndView,goodsList,priceWay,goodsList.size());

            goodsService.withSizeDomain(goodsList);
        }
        return modelAndView;
    }


    public List<GoodsDomain> filterParam(List<GoodsDomain> goodsList,List<Long> queryColorIds, List<Long> querySizeIds,List<Long> queryAttributeIds){

        // 过滤goodsList
        Boolean colorBoolean = queryColorIds!=null && queryColorIds.size()>0;//颜色
        Boolean sizeBoolean = querySizeIds!=null && querySizeIds.size()>0;//尺寸
        Boolean attributeBoolean = queryAttributeIds!=null && queryAttributeIds.size()>0; //材质
        if(colorBoolean && !sizeBoolean && !attributeBoolean){ //颜色不为空，其他都为空
            goodsList = filterGoods(goodsList,COLOR_FILTER,queryColorIds);
            goodsList =goodsList.stream().distinct().collect(Collectors.toList());
        }else if(colorBoolean && sizeBoolean && !attributeBoolean){//材质为空，颜色不为空，尺寸不为空
            goodsList = filterGoods(goodsList,COLOR_FILTER,queryColorIds);
            goodsList =goodsList.stream().distinct().collect(Collectors.toList());
            goodsList = filterGoods(goodsList,SIZE_FILTER,querySizeIds);
        }else if(colorBoolean && sizeBoolean && attributeBoolean){//都不为空
            goodsList = filterGoods(goodsList,COLOR_FILTER,queryColorIds);
            goodsList =goodsList.stream().distinct().collect(Collectors.toList());
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
            goodsList =goodsList.stream().distinct().collect(Collectors.toList());
            goodsList = attribute(goodsList, ATTR_FILTER, queryAttributeIds);
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
        List<PrototypeSpecificationOptionDomain> sizeList = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery);
        return sizeList;
    }

    public List<GoodsColorDomain> getColors(List<GoodsDomain> goodsList){
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

        query.setOrderBy("price");
        query.setDesc(false);
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
            query.setCategoryId(null);
        }else{
            query.setCategoryId(categoryId);
        }
        //商品列表
        //支持多分类查询
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setIsPublished(ValidEnum.YES.getValue());
        List<GoodsDomain> goodsListAll =  goodsService.getList(goodsQuery);
        for (GoodsDomain goodsDomain :goodsListAll){
            List<Long> categoryIdList = JSON.parseArray(goodsDomain.getCategoryIds(),Long.class);
            System.out.println("categoryIdList:"+JsonUtils.toJSONString(categoryIdList));
            goodsDomain.setCategoryIdList(categoryIdList);
        }
        if(query.getCategoryId()!=null){
            goodsListAll  =goodsListAll.stream().filter(x->x.getCategoryIdList().contains(query.getCategoryId())).collect(Collectors.toList());
        }else{
            System.out.println("getCategoryIdList:"+JsonUtils.toJSONString(query.getCategoryIds()));
            goodsListAll  =goodsListAll.stream().filter(x->
                    CollectionUtils.containsAny(x.getCategoryIdList(),query.getCategoryIds())).collect(Collectors.toList());
        }

        //List<GoodsDomain> goodsList =  goodsService.getPageList(query).getList();//goodsService.getList(query);
        Integer skip = query.getPageIndex() * query.getPageSize() - query.getPageSize();
        List<GoodsDomain> goodsList =goodsListAll.stream().skip(skip).limit(query.getPageSize()).collect(Collectors.toList());
        goodsService.withGoodsItemList(goodsList);
        System.out.println("goodsList:"+JsonUtils.toJSONString(goodsList));
        modelAndView.addObject("query",query);

        //同级分类列表
        List<GoodsCategoryDomain> goodsCategoryDomainList =list!=null&&list.size()>0?list:goodsCategoryService.listCategoryByParentId(goodsCategoryDomain.getParentId());
        modelAndView.addObject("categoryList",goodsCategoryDomainList);

        //材质列表
        modelAndView.addObject("attributeList",getAttributes(goodsList));

        //颜色列表
        modelAndView.addObject("colorList",getColors(goodsList));

        //尺寸列表
        modelAndView.addObject("sizeList",getSizes(goodsList));
        goodsService.withSizeDomain(goodsList);

        //过滤开始
        doFilter(query,modelAndView,goodsList,priceWay,goodsListAll.size());

        return modelAndView;
    }

    public void doFilter(GoodsQuery query,ModelAndView modelAndView,List<GoodsDomain> goodsList,Integer priceWay,int total){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        //清空session中筛选条件
        session.setAttribute(COLOR_IDS,null);
        session.setAttribute(ATTR_IDS,null);
        session.setAttribute(SIZE_IDS,null);
        session.setAttribute("priceWay",priceWay);
        //颜色尺寸材质过滤
        List<Long> queryColorIds= query.getColorIds();
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
        //参数过滤
        goodsList = filterParam(goodsList,queryColorIds,querySizeIds,queryAttributeIds);

        System.out.println("goodsList:"+JsonUtils.toJSONString(goodsList));
        if(priceWay == null ){
            PageList<GoodsDomain> goodsDomainPageList = new PageList<>(goodsList,query.getPageIndex(),query.getPageSize(),total);
            modelAndView.addObject("goodsDomainPageList",goodsDomainPageList);
        }else if(priceWay ==0 || priceWay ==1)
        {
            PageList<GoodsDomain> goodsDomainPageList = new PageList<>(priceWay==HIGN_TO_LOW?sortByPrice(goodsList,HIGN_TO_LOW):sortByPrice(goodsList,LOW_TO_HIGN),query.getPageIndex(),query.getPageSize(),total);
            modelAndView.addObject("goodsDomainPageList",goodsDomainPageList);
        }
    }

    @RequestMapping(value = "listMore", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult listMore(String goodsName,Long categoryId,Integer priceWay,Integer offset,Integer nowPage){
        GoodsQuery query = new GoodsQuery();
        GoodsCategoryDomain goodsCategoryDomain = goodsCategoryService.getCategory(categoryId);
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
            query.setCategoryId(null);
        }else{
            query.setCategoryId(categoryId);
        }

        //query.setOffset(offset);
       // query.setLimit(2);
        query.setName(goodsName);
        query.setPageIndex(nowPage);
        query.setPageSize(20);
        //商品列表
        //支持多分类查询
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setIsPublished(ValidEnum.YES.getValue());
        List<GoodsDomain> goodsListAll =  goodsService.getList(goodsQuery);
        for (GoodsDomain goodsDomain :goodsListAll){
            goodsDomain.setCategoryIdList(JSON.parseArray(goodsDomain.getCategoryIds(),Long.class));
        }
        if(query.getCategoryId()!=null){
            goodsListAll  =goodsListAll.stream().filter(x->x.getCategoryIdList().contains(query.getCategoryId())).collect(Collectors.toList());
        }else{
            goodsListAll  =goodsListAll.stream().filter(x->x.getCategoryIdList().contains(query.getCategoryIds())).collect(Collectors.toList());
        }

        //List<GoodsDomain> goodsList =  goodsService.getPageList(query).getList();//goodsService.getList(query);
        Integer skip = query.getPageIndex() * query.getPageSize() - query.getPageSize();
        List<GoodsDomain> goodsList =goodsListAll.stream().skip(skip).limit(query.getPageSize()).collect(Collectors.toList());

        goodsService.withGoodsItemList(goodsList);

        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();

        //颜色尺寸材质过滤
        List<Long> queryColorIds= (List<Long>)session.getAttribute(COLOR_IDS);
        List<Long> querySizeIds = (List<Long>)session.getAttribute(SIZE_IDS);
        List<Long> queryAttributeIds= (List<Long>)session.getAttribute(ATTR_IDS);
        //参数过滤
        goodsList = filterParam(goodsList,queryColorIds,querySizeIds,queryAttributeIds);

        //把商品与自己的颜色和尺寸关联
        for(GoodsDomain goodsDomain:goodsList){
            List<Long> sizeIds = JsonUtils.toLongArray(goodsDomain.getSizeIds());
            List<Long> colorIds = JsonUtils.toLongArray(goodsDomain.getColorIds());
            //关联颜色
            List<Long> newColorIds = colorIds.stream().distinct().collect(Collectors.toList());
            GoodsColorQuery goodsColorQuery = new GoodsColorQuery();
            goodsColorQuery.setIds(newColorIds);
            List<GoodsColorDomain> goodsColorDomainList = goodsColorService.getList(goodsColorQuery);
            goodsDomain.setGoodsColorDomainList(goodsColorDomainList);
            //关联默认第一个尺寸
            List<Long> newSizeIds = sizeIds.stream().distinct().collect(Collectors.toList());
            PrototypeSpecificationOptionQuery prototypeSpecificationOptionQuery = new PrototypeSpecificationOptionQuery();
            prototypeSpecificationOptionQuery.setIds(newSizeIds);
            List<PrototypeSpecificationOptionDomain> sizeList = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery);
            goodsDomain.setSizeDomainList(sizeList);
            System.out.println("sizeIds:"+sizeIds+" sizeList"+sizeList);
            goodsDomain.setFirstSizeDomain(sizeList.get(0));
        }

        PageList<GoodsDomain> goodsDomainPageList = null;
        if(priceWay == null ){
             goodsDomainPageList = new PageList<>(goodsList,query.getPageIndex(),query.getPageSize(),goodsList.size());

        }else if(priceWay ==0 || priceWay ==1){
            goodsDomainPageList = new PageList<>(priceWay==HIGN_TO_LOW?sortByPrice(goodsList,HIGN_TO_LOW):sortByPrice(goodsList,LOW_TO_HIGN),query.getPageIndex(),query.getPageSize(),goodsList.size());
        }

        for(GoodsDomain line : goodsDomainPageList.getList()){

            System.out.println("line:"+JsonUtils.toJSONString(line));
        }
        return successResult("操作成功",goodsDomainPageList);
    }

    @RequestMapping(value = "onSale" ,method = RequestMethod.GET)
    public ModelAndView onSale(){
        ModelAndView mv = new ModelAndView("goods/salelist");

        Integer onSale =1;
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setIsSale(onSale);
        //商品是否打折
        List<GoodsDomain> goodsDomainList = goodsService.getList(goodsQuery);
        //颜色商品是否打折
        goodsService.withGoodsItemList(goodsDomainList,onSale);
        //商品分类
        mv.addObject("categoryName","SALE");
        if(!(goodsDomainList!=null&&goodsDomainList.size()>0)){
            return mv;
        }else if(!(goodsDomainList.get(0).getGoodsItemList()!=null&&goodsDomainList.get(0).getGoodsItemList().size()>0)){
            return mv;
        }
        //商品分类同级列表
        GoodsCategoryQuery goodsCategoryQuery = new GoodsCategoryQuery();
        goodsCategoryQuery.setLevel(1);
        List<GoodsCategoryDomain> goodsCategoryDomainList = goodsCategoryService.getList(goodsCategoryQuery);
        mv.addObject("categoryList",goodsCategoryDomainList);

        //材质列表
        //获得原型Ids
        List<Long> prototypeIds = new ArrayList<Long>();
        goodsDomainList.forEach(x->prototypeIds.addAll(JsonUtils.toLongArray("["+x.getPrototypeId()+"]")));
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
        mv.addObject("attributeList",prototypeAttributeOptionDomainList);
        System.out.println("attributeList:"+JsonUtils.toJSONString(prototypeAttributeOptionDomainList));

        //颜色列表
        List<Long> colorIds = new ArrayList<>();
        goodsDomainList.forEach(x->colorIds.addAll(JsonUtils.toLongArray(x.getColorIds())));
        List<Long> newColorIds = colorIds.stream().distinct().collect(Collectors.toList());
        GoodsColorQuery goodsColorQuery = new GoodsColorQuery();
        goodsColorQuery.setIds(newColorIds);
        List<GoodsColorDomain> goodsColorDomainList = goodsColorService.getList(goodsColorQuery);
        mv.addObject("colorList",goodsColorDomainList);
        //尺寸列表
        List<Long> sizeIds = new ArrayList<>();
        goodsDomainList.forEach(x->sizeIds.addAll(JsonUtils.toLongArray(x.getSizeIds())));
        List<Long> newSizeIds = sizeIds.stream().distinct().collect(Collectors.toList());
        PrototypeSpecificationOptionQuery prototypeSpecificationOptionQuery = new PrototypeSpecificationOptionQuery();
        prototypeSpecificationOptionQuery.setIds(newSizeIds);
        List<PrototypeSpecificationOptionDomain> sizeList = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery);
        mv.addObject("sizeList",sizeList);



        PageList<GoodsDomain> goodsDomainPageList = new PageList<>(goodsDomainList,goodsQuery.getPageIndex(),goodsQuery.getPageSize(),goodsDomainList.size());
        mv.addObject("goodsDomainPageList",goodsDomainPageList);
        return mv;
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


        //获取sku列表
        List<SkuDomain> skuDomainList = skuService.getSkuByGoodsId(goodsId);

        //尺寸
        List<Long> sizeIds =JsonUtils.toLongArray(goodsDomain.getSizeIds());
        PrototypeSpecificationOptionQuery prototypeSpecificationOptionQuery = new PrototypeSpecificationOptionQuery();
        prototypeSpecificationOptionQuery.setIds(sizeIds);
        List<PrototypeSpecificationOptionDomain> sizeList = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery);
        System.out.println("sizeIds"+sizeIds);
        for(PrototypeSpecificationOptionDomain sizeDomain:sizeList){
            String productNo = goodsItemDomain.getGoodsNo().split("\\s+")[0];//库存商品编号
            String color = goodsItemDomain.getGoodsNo().split("\\s+")[1];//颜色标识

            sizeDomain.setStock(goodsService.getTempStock(productNo,color,sizeDomain.getName()));
        }

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
            GoodsAttributeValueDomain goodsAttributeValueDomain =goodsAttributeValueService.goodsAttributeValueDomain(goodsDomain.getId());//获取材质的id
            for (Long attribute:data) {
                if (type == ATTR_FILTER) {
                    if (goodsAttributeValueDomain.getPrototypeAttributeOptionId()== Integer.parseInt(attribute.toString())) {
                        list.add(goodsDomain);
                        System.out.println("nowGoodsDomain:" + JsonUtils.toJSONString(goodsDomain));
                    }
                }
            }

        }
        return list;
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
                Double price1 = good.getGoodsItemList().get(0).getPrice();
                Double price2 = good2.getGoodsItemList().get(0).getPrice();
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
