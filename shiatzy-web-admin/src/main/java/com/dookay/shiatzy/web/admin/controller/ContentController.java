package com.dookay.shiatzy.web.admin.controller;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.content.domain.ContentItemDomain;
import com.dookay.coral.shop.content.query.ContentItemQuery;
import com.dookay.coral.shop.content.service.IContentItemService;
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
@RequestMapping(value = "/api/content")
@Api(tags="content",value = "/api/content", description = "内容相关接口")
public class ContentController extends BaseApiController {

    @Autowired
    private IContentItemService contentItemService;


    @ApiOperation(value = "获取内容列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<ContentItemDomain>> list(@ModelAttribute ContentItemQuery contentItemQuery) {
        PageList<ContentItemDomain> contentItemDomainPageList = contentItemService.getPageList(contentItemQuery);
        return ResponseEntity.ok().body(contentItemDomainPageList);
    }

    @ApiOperation(value = "创建内容",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(ContentItemDomain domain) {
        domain.setCreateTime(new Date());
        contentItemService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改内容", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(ContentItemDomain domain) {
        contentItemService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除内容", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        contentItemService.delete(id);
        return successResponse("删除成功");
    }
}
