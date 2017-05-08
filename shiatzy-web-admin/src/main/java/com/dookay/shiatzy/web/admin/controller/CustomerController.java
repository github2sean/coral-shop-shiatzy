package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.extension.CustomerExtension;
import com.dookay.coral.shop.customer.query.CustomerQuery;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.OrderQuery;
import com.dookay.coral.shop.order.service.IOrderItemService;
import com.dookay.coral.shop.order.service.IOrderService;
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
 * @since 2017/5/2
 */
@RestController
@RequestMapping(value = "/api/customer")
@Api(tags="customer",value = "/api/customer", description = "会员相关接口")
public class CustomerController extends BaseApiController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private CustomerExtension customerExtension;


    @ApiOperation(value = "获取会员列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<CustomerDomain>> list(@ModelAttribute CustomerQuery customerQuery) {
        PageList<CustomerDomain> customerDomainPageList = customerService.getPageList(customerQuery);
        customerExtension.withAccountAndAddress(customerDomainPageList);
        return ResponseEntity.ok().body(customerDomainPageList);
    }


    @ApiOperation(value = "创建会员",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(CustomerDomain domain) {
        domain.setCreateTime(new Date());
        customerService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改会员", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(CustomerDomain domain) {
        customerService.update(domain);
        customerExtension.withAccountAndAddress(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除会员", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        customerService.delete(id);
        return successResponse("删除成功");
    }
    @ApiOperation(value = "禁用会员", httpMethod = "POST")
    @RequestMapping(value = "/forbid", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity forbid(@RequestParam("id") Long id) {
        customerService.forbid(id);
        return successResponse("禁用成功");
    }
    @ApiOperation(value = "启用会员", httpMethod = "POST")
    @RequestMapping(value = "/enable", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity enable(@RequestParam("id") Long id) {
        customerService.enable(id);
        return successResponse("启用成功");
    }

    @ApiOperation(value = "获取会员", httpMethod = "GET", response = CustomerDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<CustomerDomain> get(@RequestParam ("id") Long id) {
        CustomerDomain domain = customerService.get(id);
        customerExtension.withAccountAndAddress(domain);
        return ResponseEntity.ok().body(domain);
    }

}
