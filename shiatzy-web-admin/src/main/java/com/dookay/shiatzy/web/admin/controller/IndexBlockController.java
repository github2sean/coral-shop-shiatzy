package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.index.domain.IndexBlockDomain;
import com.dookay.coral.shop.index.query.IndexBlockGroupQuery;
import com.dookay.coral.shop.index.query.IndexBlockQuery;
import com.dookay.coral.shop.index.service.IIndexBlockGroupService;
import com.dookay.coral.shop.index.service.IIndexBlockService;
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
@RequestMapping(value = "/api/indexBlockGroup/indexBlock")
@Api(tags="indexBlockGroup",value = "/api/indexBlockGroup/indexBlock", description = "首页模块相关接口")
public class IndexBlockController extends BaseApiController {


    @Autowired
    private IIndexBlockService indexBlockService;

    @ApiOperation(value = "获取首页模块列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<IndexBlockDomain>> list(@ModelAttribute IndexBlockQuery indexBlockQuery) {
        PageList<IndexBlockDomain> indexBlockDomainPageList = indexBlockService.getPageList(indexBlockQuery);
        return ResponseEntity.ok().body(indexBlockDomainPageList);
    }

    @ApiOperation(value = "获取首页模块", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<IndexBlockDomain> get(@RequestParam("id") Long id) {
        IndexBlockDomain IndexBlockDomain = indexBlockService.get(id);
        return ResponseEntity.ok().body(IndexBlockDomain);
    }

    @ApiOperation(value = "创建首页模块",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(IndexBlockDomain domain) {
        domain.setCreateTime(new Date());
        indexBlockService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改首页模块", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(IndexBlockDomain domain) {
        domain.setUpdateTime(new Date());
        indexBlockService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除首页模块", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        indexBlockService.delete(id);
        return successResponse("删除成功");
    }

}
