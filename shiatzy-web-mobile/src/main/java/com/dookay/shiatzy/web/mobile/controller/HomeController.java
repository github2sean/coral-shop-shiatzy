package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.adapter.sendmsg.sendmail.SimpleAliDMSendMail;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.CookieUtil;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.service.IAccountService;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
import com.dookay.coral.shop.content.domain.PushContentDomain;
import com.dookay.coral.shop.content.query.ContentCategoryQuery;
import com.dookay.coral.shop.content.query.PushContentQuery;
import com.dookay.coral.shop.content.service.IContentCategoryService;
import com.dookay.coral.shop.content.service.IPushContentService;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;
import com.dookay.coral.shop.index.domain.IndexBlockDomain;
import com.dookay.coral.shop.index.domain.IndexBlockGroupDomain;
import com.dookay.coral.shop.index.query.IndexBlockGroupQuery;
import com.dookay.coral.shop.index.query.IndexBlockQuery;
import com.dookay.coral.shop.index.service.IIndexBlockGroupService;
import com.dookay.coral.shop.index.service.IIndexBlockService;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.coral.shop.promotion.query.CouponQuery;
import com.dookay.coral.shop.promotion.service.ICouponService;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.query.ShippingCountryQuery;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/4/25
 */
@Controller
@RequestMapping("home/")
public class HomeController extends MobileBaseController {

    @Autowired
    private IGoodsCategoryService goodsCategoryService;
    @Autowired
    private ICouponService couponService;
    @Autowired
    private IShippingCountryService shippingCountryService;
    @Autowired
    private IContentCategoryService contentCategoryService;
    @Autowired
    private IPushContentService pushContentService;
    @Autowired
    private IIndexBlockGroupService indexBlockGroupService;
    @Autowired
    private IIndexBlockService indexBlockService;
    @Autowired
    private CookieLocaleResolver resolver;
    @Autowired
    private SimpleAliDMSendMail simpleAliDMSendMail;

    private static final String PUSH_HISTORY = "push_history";

    private final static String SHIPPING_COUNTRY_ID="shippingCountryId";
    private final static String LANGUAGE_HISTORY = "Language";
    private final static int MAX_COOKIE_AGE = 24*60*60*7;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() throws MessagingException {

        ModelAndView mv = new ModelAndView("home/index");
        CouponQuery query = new CouponQuery();
        query.setIndexShow(1);
        CouponDomain couponDomain = couponService.getFirst(query);
        mv.addObject("coupon",couponDomain);
        //查询页尾内容
        ContentCategoryQuery querys=new ContentCategoryQuery();
        querys.setLevel(1);
        List<ContentCategoryDomain> domainList=contentCategoryService.getList(querys);
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("domainList",domainList);


        //查询推送内容
        //先查询cookie中是否以查看过内容查看过无需再显示
        String pushId = CookieUtil.getCookieValue(request,PUSH_HISTORY);
        System.out.println("pushId:"+JsonUtils.toJSONString(pushId));
        if(StringUtils.isBlank(pushId)){
            PushContentQuery pushContentQuery = new PushContentQuery();
            pushContentQuery.setIsValid(1);
            pushContentQuery.setDesc(false);
            pushContentQuery.setOrderBy("time");
            List<PushContentDomain> pushContentList =  pushContentService.getList(pushContentQuery);
            PushContentDomain result = null;
            Date nowTime  = new Date();
            for(PushContentDomain line: pushContentList){
                Date time = line.getTime();
                if(time.after(nowTime)){
                    result = line;
                    break;
                }
            }
            if(result!=null){
                System.out.println("pushContent:"+JsonUtils.toJSONString(result));
                CookieUtil.setCookieValue(HttpContext.current().getResponse(),PUSH_HISTORY,result.getId()+"",MAX_COOKIE_AGE);
                mv.addObject("pushContent",result);
            }
        }


        //查询首页布局样式
        IndexBlockGroupQuery groupQuery = new IndexBlockGroupQuery();
        groupQuery.setIsValid(1);
        groupQuery.setDesc(false);
        groupQuery.setOrderBy("rank");
        List<IndexBlockGroupDomain> groupList = indexBlockGroupService.getList(groupQuery);
        IndexBlockQuery blockQuery = new IndexBlockQuery();
        System.out.println("groupList:"+groupList+"\n size:"+groupList.size());
        for(IndexBlockGroupDomain line:groupList){
            blockQuery.setGroupId(line.getId());
            blockQuery.setDesc(false);
            blockQuery.setOrderBy("rank");
            blockQuery.setIsValid(1);
            List<IndexBlockDomain> indexBlockDomainList  = indexBlockService.getList(blockQuery);
            for(IndexBlockDomain row:indexBlockDomainList){
              String image = row.getImage();
                if(StringUtils.isNotBlank(image) && !image.contains("[{") && !image.contains("}]")){
                    image = "[{\"alt\":\"首页图\",\"file\":\""+image+"\"}]";
                }
                row.setImage(image);
            }
            System.out.println("indexBlockDomainList:"+indexBlockDomainList+"\n size:"+indexBlockDomainList.size());
            line.setIndexBlockDomainList(indexBlockDomainList);
        }
        mv.addObject("groupList",groupList);
        return mv;
    }


    @RequestMapping(value = "chooseShippingCountry", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult chooseShippingCountry(HttpServletResponse response, Long shippingCountryId){
        if(shippingCountryId==null){
            return errorResult("选择为空");
        }
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(SHIPPING_COUNTRY_ID,shippingCountryId);
        CookieUtil.setCookieValueByKey(response,"shippingCountry",shippingCountryId+"",60*60*24*365);
        return successResult("选择成功");
    }

    @RequestMapping(value = "listShippingCountry", method = RequestMethod.GET)
    public ModelAndView listShippingCountry(){
        ModelAndView mv = new ModelAndView("home/listShippingCountry");
        //查询出配送国家
        ShippingCountryQuery query = new ShippingCountryQuery();
        query.setDesc(false);
        query.setOrderBy("rank");
        List<ShippingCountryDomain> shippingCountryDomainList = shippingCountryService.getList(query);
        mv.addObject("countryList",shippingCountryDomainList);
        return mv;
    }

    @RequestMapping(value = "selectLanguage", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult selectLanguage(String nowLanguage){
        //判断当前语言环境
        String cookieName = resolver.getCookieName();
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        if("zh_CN".equals(nowLanguage)){
            CookieUtil.setCookieValueByKey(HttpContext.current().getResponse(),cookieName,"zh_CN",MAX_COOKIE_AGE);
        }else if("en_US".equals(nowLanguage)){
            CookieUtil.setCookieValueByKey(HttpContext.current().getResponse(),cookieName,"en_US",MAX_COOKIE_AGE);
        }else{
            return errorResult("参数有错");
        }
        return successResult("选择成功");
    }

    @RequestMapping(value = "queryCurrentRate", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult queryCurrentRate(){
        //判断当前语言环境
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();

        String countryId = CookieUtil.getCookieValueByKey(request,"shippingCountry");
        Long id = StringUtils.isBlank(countryId)?1L:Long.parseLong(countryId);
        if(id==null){
            return errorResult("未选择国家");
        }
        //查询出配送国家
        ShippingCountryDomain country = shippingCountryService.get(id);
        if(country==null){
            return  errorResult("无此国家");
        }
        return successResult("查询成功",country);
    }


}
