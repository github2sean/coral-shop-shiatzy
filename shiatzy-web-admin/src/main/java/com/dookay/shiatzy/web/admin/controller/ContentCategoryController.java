package com.dookay.shiatzy.web.admin.controller;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
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
    public ResponseEntity<PageList<ContentCategoryDomain>> list(@ModelAttribute ContentCategoryQuery contentCategoryQuery) {
        PageList<ContentCategoryDomain> contentItemDomainPageList = contentCategoryService.getPageList(contentCategoryQuery);
        return ResponseEntity.ok().body(contentItemDomainPageList);
    }


    @ApiOperation(value = "创建分类",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(ContentCategoryDomain domain) {
        domain.setCreateTime(new Date());
        contentCategoryService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改分类", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(ContentCategoryDomain domain) {
        contentCategoryService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除分类", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        contentCategoryService.delete(id);
        return successResponse("删除成功");
    }
}
