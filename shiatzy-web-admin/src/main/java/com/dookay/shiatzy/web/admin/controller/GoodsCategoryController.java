package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.extension.GoodsCategoryExtension;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import com.dookay.shiatzy.web.admin.response.goods.ListGoodsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/5
 */
@RestController
@RequestMapping(value = "/api/goods/category")
@Api(tags="goods",value = "/api/goods/category", description = "商品相关接口")
public class GoodsCategoryController extends BaseApiController {

    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    @ApiOperation(value = "获取商品分类列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<List<GoodsCategoryDomain>> list(@ModelAttribute GoodsCategoryQuery query) {
        List<GoodsCategoryDomain> goodsDomainPageList = goodsCategoryService.listCategory(query);
        return ResponseEntity.ok().body(goodsDomainPageList);
    }

    @ApiOperation(value = "获取商品分类", httpMethod = "GET", response = GoodsCategoryDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<GoodsCategoryDomain> get(@Param("id") Long id) {
        GoodsCategoryDomain domain = goodsCategoryService.get(id);

        return ResponseEntity.ok().body(domain);
    }

    @ApiOperation(value = "创建商品分类",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(GoodsCategoryDomain domain) {
        domain.setCreateTime(new Date());
        goodsCategoryService.create(domain);
        return successResponse("创建成功");
    }
    
    @ApiOperation(value = "修改商品分类", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(GoodsCategoryDomain domain) {
        goodsCategoryService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除商品分类", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@Param("id") Long id) {
        goodsCategoryService.delete(id);
        return successResponse("删除成功");
    }
    
}
