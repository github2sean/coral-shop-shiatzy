package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.store.domain.StoreCityDomain;
import com.dookay.coral.shop.store.extension.StoreCityExtension;
import com.dookay.coral.shop.store.extension.StoreExtension;
import com.dookay.coral.shop.store.query.StoreCityQuery;
import com.dookay.coral.shop.store.query.StoreQuery;
import com.dookay.coral.shop.store.service.IStoreCityService;
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
@RequestMapping(value = "/api/store/city")
@Api(tags="city",value = "/api/store/city", description = "门店城市相关接口")
public class StoreCityController extends BaseApiController {

    @Autowired
    private IStoreCityService storeCityService;
    @Autowired
    private StoreCityExtension storeCityExtension;


    @ApiOperation(value = "获取门店城市列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<StoreCityDomain>> list(@ModelAttribute StoreCityQuery storeCityQuery) {
        PageList<StoreCityDomain> storeCityDomainPageList = storeCityService.getPageList(storeCityQuery);
        storeCityExtension.withCountry(storeCityDomainPageList);
        return ResponseEntity.ok().body(storeCityDomainPageList);
    }

    @ApiOperation(value = "获取门店城市", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<StoreCityDomain> get(@RequestParam("id") Long id) {
        StoreCityDomain StoreCityDomain = storeCityService.get(id);
        storeCityExtension.withCountry(StoreCityDomain);
        return ResponseEntity.ok().body(StoreCityDomain);
    }

    @ApiOperation(value = "创建门店城市",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(StoreCityDomain domain) {
        domain.setCreateTime(new Date());
        storeCityService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改门店城市", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(StoreCityDomain domain) {
        domain.setUpdateTime(new Date());
        storeCityService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除门店城市", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        storeCityService.delete(id);
        return successResponse("删除成功");
    }

    

}
