package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.index.domain.IndexBlockGroupDomain;
import com.dookay.coral.shop.index.query.IndexBlockGroupQuery;
import com.dookay.coral.shop.index.service.IIndexBlockGroupService;
import com.dookay.coral.shop.index.service.IIndexBlockService;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.coral.shop.promotion.query.CouponQuery;
import com.dookay.coral.shop.promotion.service.ICouponService;
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
@RequestMapping(value = "/api/indexBlockGroup")
@Api(tags="indexBlockGroup",value = "/api/indexBlockGroup", description = "首页模块组相关接口")
public class IndexBlockGroupController extends BaseApiController {


    @Autowired
    private IIndexBlockGroupService indexBlockGroupService;

    @ApiOperation(value = "获取首页模块组列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<IndexBlockGroupDomain>> list(@ModelAttribute IndexBlockGroupQuery indexBlockGroupQuery) {
        PageList<IndexBlockGroupDomain> indexBlockGroupDomainPageList = indexBlockGroupService.getPageList(indexBlockGroupQuery);
        return ResponseEntity.ok().body(indexBlockGroupDomainPageList);
    }

    @ApiOperation(value = "获取首页模块组", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<IndexBlockGroupDomain> get(@RequestParam("id") Long id) {
        IndexBlockGroupDomain indexBlockGroupDomain = indexBlockGroupService.get(id);
        //带上群组
        indexBlockGroupService.withIndexBlock(indexBlockGroupDomain);
        return ResponseEntity.ok().body(indexBlockGroupDomain);
    }

    @ApiOperation(value = "创建首页模块组",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(IndexBlockGroupDomain domain) {
        domain.setCreateTime(new Date());
        indexBlockGroupService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改首页模块组", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(IndexBlockGroupDomain domain) {
        domain.setUpdateTime(new Date());
        indexBlockGroupService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除首页模块组", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        indexBlockGroupService.delete(id);
        return successResponse("删除成功");
    }

}
