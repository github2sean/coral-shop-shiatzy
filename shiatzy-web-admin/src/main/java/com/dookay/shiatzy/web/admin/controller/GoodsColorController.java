package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsColorDomain;
import com.dookay.coral.shop.goods.query.GoodsColorQuery;
import com.dookay.coral.shop.goods.service.IGoodsColorService;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import com.dookay.shiatzy.web.admin.response.goods.ListGoodsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/5
 */
@RestController
@RequestMapping(value = "/api/goods/color")
@Api(tags="goods",value = "/api/goods/color", description = "商品相关接口")
public class GoodsColorController extends BaseApiController {

    @Autowired
    private IGoodsColorService goodsColorService;

    @ApiOperation(value = "获取商品颜色列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<GoodsColorDomain>> list(@ModelAttribute GoodsColorQuery query) {
        PageList<GoodsColorDomain> goodsDomainPageList = goodsColorService.getPageList(query);
        return ResponseEntity.ok().body(goodsDomainPageList);
    }

    @ApiOperation(value = "获取商品颜色", httpMethod = "GET", response = GoodsColorDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<GoodsColorDomain> get(@RequestParam("id") Long id) {
        GoodsColorDomain domain = goodsColorService.get(id);

        return ResponseEntity.ok().body(domain);
    }

    @ApiOperation(value = "创建商品颜色",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(GoodsColorDomain domain) {
        domain.setCreateTime(new Date());
        goodsColorService.create(domain);
        return successResponse("创建成功");
    }
    
    @ApiOperation(value = "修改商品颜色", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(GoodsColorDomain domain) {
        goodsColorService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除商品颜色", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        goodsColorService.delete(id);
        return successResponse("删除成功");
    }
    
}
