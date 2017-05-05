package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.host.security.enums.PermissionType;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsPrototypeDomain;
import com.dookay.coral.shop.goods.query.GoodsPrototypeQuery;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsPrototypeService;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import com.dookay.shiatzy.web.admin.response.goods.ListGoodsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/3
 */
@RestController
@RequestMapping(value = "/api/goods/prototype")
@Api(tags="prototype",value = "/api/goods/prototype", description = "商品原型相关接口")
public class GoodsPrototypeController extends BaseApiController {
    @Autowired
    private IGoodsPrototypeService goodsPrototypeService;

    @ApiOperation(value = "列出商品原型", httpMethod = "GET", response = GoodsPrototypeDomain.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<List<GoodsPrototypeDomain>> list(@ModelAttribute GoodsPrototypeQuery goodsPrototypeQuery) {
        List<GoodsPrototypeDomain> goodsDomainPageList = goodsPrototypeService.getList(goodsPrototypeQuery);

        return ResponseEntity.ok().body(goodsDomainPageList);
    }

    @ApiOperation(value = "获取商品原型", httpMethod = "GET", response = GoodsPrototypeDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<GoodsPrototypeDomain> get(@Param("id") Long id) {
        GoodsPrototypeDomain goodsPrototypeDomain = goodsPrototypeService.get(id);

        return ResponseEntity.ok().body(goodsPrototypeDomain);
    }

    @ApiOperation(value = "创建商品原型",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(GoodsPrototypeDomain domain) {
        domain.setCreateTime(new Date());
        goodsPrototypeService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改商品原型", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(GoodsPrototypeDomain domain) {
        goodsPrototypeService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除商品原型", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@Param("id") Long id) {
        goodsPrototypeService.delete(id);
        return successResponse("删除成功");
    }
}
