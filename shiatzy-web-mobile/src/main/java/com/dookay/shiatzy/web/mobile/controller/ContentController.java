package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
import com.dookay.coral.shop.content.domain.ContentItemDomain;
import com.dookay.coral.shop.content.query.ContentCategoryQuery;
import com.dookay.coral.shop.content.query.ContentItemQuery;
import com.dookay.coral.shop.content.service.IContentCategoryService;
import com.dookay.coral.shop.content.service.IContentItemService;
import com.dookay.shiatzy.web.mobile.form.QueryGoodsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/4/25.
 */
@Controller
@RequestMapping("/content/")
public class ContentController extends BaseController {
    @Autowired
    private IContentCategoryService contentCategoryService;
    @Autowired
    private IContentItemService contentItemService;
    @RequestMapping(value = "faq", method = RequestMethod.GET)
    public ModelAndView faq(Long id){
        ContentCategoryQuery query =new ContentCategoryQuery();
        query.setParentId(id);
        List<ContentCategoryDomain> domainList=contentCategoryService.getList(query);
        ContentItemQuery itemQuery =new ContentItemQuery();
        for (ContentCategoryDomain content:domainList) {
            itemQuery.setCategoryId(content.getId());
            List<ContentItemDomain> contentItemDomains=contentItemService.getList(itemQuery).stream()
                    .sorted(Comparator.comparing(ContentItemDomain::getId)).collect(Collectors.toList());
            content.setContentItemDomainList(contentItemDomains);
        }
        ModelAndView modelAndView = new ModelAndView("content/faq");
        modelAndView.addObject("domainList",domainList);
        return modelAndView;
    }

    @RequestMapping(value = "specialService", method = RequestMethod.GET)
    public ModelAndView specialService(){
        ModelAndView modelAndView = new ModelAndView("content/specialService");
        return modelAndView;
    }

    @RequestMapping(value = "returnOrchange", method = RequestMethod.GET)
    public ModelAndView returnOrchange(Long id){
        ContentCategoryQuery query =new ContentCategoryQuery();
        query.setParentId(id);
        List<ContentCategoryDomain> domainList=contentCategoryService.getList(query).stream()
                .sorted(Comparator.comparing(ContentCategoryDomain::getId)).collect(Collectors.toList());
        ContentItemQuery itemQuery =new ContentItemQuery();
        for (ContentCategoryDomain content:domainList) {
            itemQuery.setCategoryId(content.getId());
            List<ContentItemDomain> contentItemDomains=contentItemService.getList(itemQuery).stream()
                    .sorted(Comparator.comparing(ContentItemDomain::getId)).collect(Collectors.toList());
            content.setContentItemDomainList(contentItemDomains);
        }
        ModelAndView modelAndView = new ModelAndView("content/returnOrchange");
        modelAndView.addObject("domainList",domainList);
        return modelAndView;
    }
    @RequestMapping(value = "deliveryTime", method = RequestMethod.GET)
    public ModelAndView deliveryTime(){
        ModelAndView modelAndView = new ModelAndView("content/deliveryTime");
        return modelAndView;
    }

    @RequestMapping(value = "whatBoutique", method = RequestMethod.GET)
    public ModelAndView whatBoutique(Long id){
        ContentCategoryQuery query =new ContentCategoryQuery();
        query.setParentId(id);
        List<ContentCategoryDomain> domainList=contentCategoryService.getList(query).stream()
                .sorted(Comparator.comparing(ContentCategoryDomain::getId).reversed()).collect(Collectors.toList());
        ContentItemQuery itemQuery =new ContentItemQuery();
        for (ContentCategoryDomain content:domainList) {
            itemQuery.setCategoryId(content.getId());
            List<ContentItemDomain> contentItemDomains=contentItemService.getList(itemQuery).stream()
                    .sorted(Comparator.comparing(ContentItemDomain::getId)).collect(Collectors.toList());
            content.setContentItemDomainList(contentItemDomains);
        }

        ModelAndView modelAndView = new ModelAndView("content/whatBoutique");
        modelAndView.addObject("domainList",domainList);
        return modelAndView;
    }

    @RequestMapping(value = "privacyNotice", method = RequestMethod.GET)
    public ModelAndView privacyNotice(){
        ModelAndView modelAndView = new ModelAndView("content/privacyNotice");
        return modelAndView;
    }
    @RequestMapping(value = "customerService", method = RequestMethod.GET)
    public ModelAndView customerService(){
        ModelAndView modelAndView = new ModelAndView("content/customerService");
        return modelAndView;
    }
    @RequestMapping(value = "sizeNotice", method = RequestMethod.GET)
    public ModelAndView sizeNotice(){
        ModelAndView modelAndView = new ModelAndView("content/sizeNotice");
        return modelAndView;
    }

    @RequestMapping(value = "whatWish", method = RequestMethod.GET)
    public ModelAndView whatWish(Long id){
        ContentCategoryQuery query =new ContentCategoryQuery();
        query.setParentId(id);
        List<ContentCategoryDomain> domainList=contentCategoryService.getList(query).stream()
                .sorted(Comparator.comparing(ContentCategoryDomain::getId).reversed()).collect(Collectors.toList());
        ContentItemQuery itemQuery =new ContentItemQuery();
        for (ContentCategoryDomain content:domainList) {
            itemQuery.setCategoryId(content.getId());
            List<ContentItemDomain> contentItemDomains=contentItemService.getList(itemQuery).stream()
                    .sorted(Comparator.comparing(ContentItemDomain::getId)).collect(Collectors.toList());
            content.setContentItemDomainList(contentItemDomains);
        }
        ModelAndView modelAndView = new ModelAndView("content/whatWish");
        modelAndView.addObject("domainList",domainList);
        return modelAndView;
    }
    @RequestMapping(value = "whatCoupon", method = RequestMethod.GET)
    public ModelAndView whatCoupon(){
        ModelAndView modelAndView = new ModelAndView("content/whatCoupon");
        return modelAndView;
    }
    
}
