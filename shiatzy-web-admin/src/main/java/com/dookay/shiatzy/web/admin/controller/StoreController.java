package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.store.domain.StoreDomain;
import com.dookay.coral.shop.store.extension.StoreExtension;
import com.dookay.coral.shop.store.query.StoreQuery;
import com.dookay.coral.shop.store.service.IStoreService;
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
@RequestMapping(value = "/api/store")
@Api(tags="store",value = "/api/store", description = "门店相关接口")
public class StoreController extends BaseApiController {

    @Autowired
    private IStoreService storeService;

    @Autowired
    private StoreExtension storeExtension;


    @ApiOperation(value = "获取门店列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<StoreDomain>> list(@ModelAttribute StoreQuery storeQuery) {
        PageList<StoreDomain> storeDomainPageList = storeService.getPageList(storeQuery);
        storeExtension.withCountryAndCity(storeDomainPageList);
        return ResponseEntity.ok().body(storeDomainPageList);
    }

    @ApiOperation(value = "获取门店", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<StoreDomain> get(@RequestParam("id") Long id) {
        StoreDomain StoreDomain = storeService.get(id);
        storeExtension.withCountryAndCity(StoreDomain);
        return ResponseEntity.ok().body(StoreDomain);
    }

    @ApiOperation(value = "创建门店",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(StoreDomain domain) {
        domain.setCreateTime(new Date());
        storeService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改门店", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(StoreDomain domain) {
        domain.setUpdateTime(new Date());
        storeService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除门店", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        storeService.delete(id);
        return successResponse("删除成功");
    }

    

}
