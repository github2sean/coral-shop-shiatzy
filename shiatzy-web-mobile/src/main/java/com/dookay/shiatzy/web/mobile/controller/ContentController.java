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
import java.util.List;

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
            List<ContentItemDomain> contentItemDomains=contentItemService.getList(itemQuery);
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
}
