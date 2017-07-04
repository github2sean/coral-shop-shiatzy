package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;

import com.dookay.coral.shop.store.domain.StoreCountryDomain;
import com.dookay.coral.shop.store.query.StoreCountryQuery;
import com.dookay.coral.shop.store.service.IStoreCountryService;
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
@RequestMapping(value = "/api/store/country")
@Api(tags="store",value = "/api/store/country", description = "门店国家相关接口")
public class StoreCountryController extends BaseApiController {

    @Autowired
    private IStoreCountryService storeCountryService;
    

    @ApiOperation(value = "获取门店国家列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<StoreCountryDomain>> list(@ModelAttribute StoreCountryQuery storeCountryQuery) {
        PageList<StoreCountryDomain> storeCountryDomainPageList = storeCountryService.getPageList(storeCountryQuery);
        return ResponseEntity.ok().body(storeCountryDomainPageList);
    }

    @ApiOperation(value = "获取门店国家", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<StoreCountryDomain> get(@RequestParam("id") Long id) {
        StoreCountryDomain storeCountryDomain = storeCountryService.get(id);
        return ResponseEntity.ok().body(storeCountryDomain);
    }

    @ApiOperation(value = "创建门店国家",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(StoreCountryDomain domain) {
        domain.setCreateTime(new Date());
        storeCountryService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改门店国家", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(StoreCountryDomain domain) {
        domain.setUpdateTime(new Date());
        storeCountryService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除门店国家", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        storeCountryService.delete(id);
        return successResponse("删除成功");
    }

    

}
