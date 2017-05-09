package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.extension.GoodsExtension;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.ISkuService;
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
@RequestMapping(value = "/api/goods/sku")
@Api(tags="sku",value = "/api/goods/sku", description = "商品sku相关接口")
public class SkuController extends BaseApiController {
    
    @Autowired
    private ISkuService skuService;
    @Autowired
    private GoodsExtension goodsExtension;
    @Autowired
    private IGoodsService goodsService;
    
    @ApiOperation(value = "获取商品sku列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<SkuDomain>> list(@ModelAttribute GoodsQuery goodsQuery) {
        PageList<SkuDomain> pageList = skuService.getPageList(goodsQuery);
        goodsExtension.withGoods(pageList);
        return ResponseEntity.ok().body(pageList);
    }
    
    @ApiOperation(value = "获取商品sku", httpMethod = "GET", response = SkuDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<SkuDomain> get(@RequestParam("id") Long id) {
        SkuDomain domain = skuService.get(id);
        goodsExtension.withGoods(domain);
        return ResponseEntity.ok().body(domain);
    }
    
    @ApiOperation(value = "创建商品sku",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(SkuDomain domain) {
        domain.setCreateTime(new Date());
        skuService.create(domain);
        updateSizes(domain);
        return successResponse("创建成功");
    }
    
    @ApiOperation(value = "修改商品sku", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(SkuDomain domain) {
        skuService.update(domain);
        updateSizes(domain);
        return successResponse("编辑成功");
    }
    
    @ApiOperation(value = "删除商品sku", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        skuService.delete(id);
        SkuDomain domain = skuService.get(id);
        updateSizes(domain);
        return successResponse("删除成功");
    }

    private void updateSizes(SkuDomain domain){
        GoodsDomain goodsDomain = new GoodsDomain();
        goodsDomain.setId(domain.getGoodsId());
        goodsService.updateSizes(goodsDomain);
    }
}
