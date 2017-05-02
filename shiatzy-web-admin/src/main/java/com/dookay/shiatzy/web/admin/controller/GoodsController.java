package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import com.dookay.shiatzy.web.admin.response.goods.ListGoodsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
@RestController
@RequestMapping(value = "/api/goods")
@Api(tags="goods",value = "/api/goods", description = "商品相关接口")
public class GoodsController extends BaseApiController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    @ApiOperation(value = "获取商品列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/listGoods", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<GoodsDomain>> listActivity(@ModelAttribute GoodsQuery goodsQuery) {
        PageList<GoodsDomain> goodsDomainPageList = goodsService.getPageList(goodsQuery);

        return ResponseEntity.ok().body(goodsDomainPageList);
    }
}
