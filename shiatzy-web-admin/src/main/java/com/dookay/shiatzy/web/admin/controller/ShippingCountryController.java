package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.query.ShippingCountryQuery;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
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
@RequestMapping(value = "/api/shippingCountry")
@Api(tags="shippingCountry",value = "/api/shippingCountry", description = "收货国家/地区相关接口")
public class ShippingCountryController extends BaseApiController {

    @Autowired
    private IShippingCountryService shippingCountryService;

    
    @ApiOperation(value = "获取收货国家/地区列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<ShippingCountryDomain>> list(@ModelAttribute ShippingCountryQuery shippingCountryQuery) {
        PageList<ShippingCountryDomain> storeDomainPageList = shippingCountryService.getPageList(shippingCountryQuery);
        return ResponseEntity.ok().body(storeDomainPageList);
    }

    @ApiOperation(value = "获取收货国家/地区", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<ShippingCountryDomain> get(@RequestParam("id") Long id) {
        ShippingCountryDomain ShippingCountryDomain = shippingCountryService.get(id);
        return ResponseEntity.ok().body(ShippingCountryDomain);
    }

    @ApiOperation(value = "创建收货国家/地区",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(ShippingCountryDomain domain) {
        domain.setCreateTime(new Date());
        shippingCountryService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改收货国家/地区", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(ShippingCountryDomain domain) {
        domain.setUpdateTime(new Date());
        shippingCountryService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除收货国家/地区", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        shippingCountryService.delete(id);
        return successResponse("删除成功");
    }

    

}
