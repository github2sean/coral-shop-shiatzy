package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.query.GoodsItemQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
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
 * @since 2017/5/5
 */
@RestController
@RequestMapping(value = "/api/goods/item")
@Api(tags="goods",value = "/api/goods/item", description = "商品相关接口")
public class GoodsItemController extends BaseApiController {

    @Autowired
    private IGoodsItemService goodsItemService;
    @Autowired
    private IGoodsService goodsService;

    @ApiOperation(value = "获取商品项目列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<GoodsItemDomain>> list(@ModelAttribute GoodsItemQuery query) {
        PageList<GoodsItemDomain> goodsDomainPageList = goodsItemService.getPageList(query);
        goodsItemService.withGoods(goodsDomainPageList);
        return ResponseEntity.ok().body(goodsDomainPageList);
    }

    @ApiOperation(value = "获取商品项目", httpMethod = "GET", response = GoodsItemDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<GoodsItemDomain> get(@RequestParam("id") Long id) {
        GoodsItemDomain domain = goodsItemService.get(id);
        return ResponseEntity.ok().body(domain);
    }

    @ApiOperation(value = "创建商品项目",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(GoodsItemDomain domain) {
        domain.setCreateTime(new Date());
        goodsItemService.withColor(domain);
        goodsItemService.create(domain);
        //更新颜色
        updateColors(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改商品项目", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(GoodsItemDomain domain) {
        goodsItemService.withColor(domain);
        goodsItemService.update(domain);
        //更新颜色
        updateColors(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除商品项目", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        goodsItemService.delete(id);
        GoodsItemDomain domain = goodsItemService.get(id);
        //更新颜色
        updateColors(domain);
        return successResponse("删除成功");
    }

    private void updateColors(GoodsItemDomain domain) {
        GoodsDomain goodsDomain = new GoodsDomain();
        goodsDomain.setId(domain.getGoodsId());
        goodsService.updateColors(goodsDomain);
    }

}
