package com.dookay.shiatzy.web.admin.controller;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
import com.dookay.coral.shop.content.enums.ContentCategoryLevel;
import com.dookay.coral.shop.content.query.ContentCategoryQuery;
import com.dookay.coral.shop.content.service.IContentCategoryService;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import com.dookay.shiatzy.web.admin.response.goods.ListGoodsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
@RestController
@RequestMapping(value = "/api/content/category")
@Api(tags="content",value = "/api/content/category", description = "内容相关接口")
public class ContentCategoryController extends BaseApiController {

    @Autowired
    private IContentCategoryService contentCategoryService;


    @ApiOperation(value = "获取内容分类列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<List<ContentCategoryDomain>> list(@ModelAttribute ContentCategoryQuery contentCategoryQuery) {
        List<ContentCategoryDomain> contentItemDomainList = contentCategoryService.listCategory(contentCategoryQuery);
        return ResponseEntity.ok().body(contentItemDomainList);
    }


    @ApiOperation(value = "添加内容分类",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(ContentCategoryDomain domain) {
        domain.setCreateTime(new Date());
        contentCategoryService.create(domain);
        return successResponse("创建成功");
    }
    @ApiOperation(value = "获取内容分类", httpMethod = "GET", response = ContentCategoryDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<ContentCategoryDomain> get(@RequestParam("id") Long id) {
        ContentCategoryDomain domain = contentCategoryService.get(id);
        return ResponseEntity.ok().body(domain);
    }
    @ApiOperation(value = "修改内容分类", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(ContentCategoryDomain domain) {
        contentCategoryService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除内容分类", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        ContentCategoryDomain contentCategoryDomain= contentCategoryService.get(id);
        ContentCategoryQuery query =new ContentCategoryQuery();
        query.setParentId(contentCategoryDomain.getId());
        List<ContentCategoryDomain> contentCategoryDomainList =contentCategoryService.getList(query);
        //是否是一级分类
        if(contentCategoryDomain.getLevel() != ContentCategoryLevel.LEVEL2.getValue()){
            if(contentCategoryDomainList.size()>0){
                return successResponse("请先删除所属子分类");
            }else if(contentCategoryDomainList.size()==0){
                contentCategoryService.delete(id);
            }
        }else if(contentCategoryDomain.getLevel() == ContentCategoryLevel.LEVEL2.getValue())
        {
            contentCategoryService.delete(id);
        }
        return successResponse("删除成功");
    }
}
