package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.extension.GoodsCategoryExtension;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
@RestController
@RequestMapping(value = "/api/order")
@Api(tags="order",value = "/api/order", description = "订单相关接口")
public class OrderController extends BaseApiController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderItemService orderItemService;

    @ApiOperation(value = "获取订单列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<OrderDomain>> list(@ModelAttribute OrderQuery orderQuery) {
        PageList<OrderDomain> orderDomainPageList = orderService.getPageList(orderQuery);
        return ResponseEntity.ok().body(orderDomainPageList);
    }

    @ApiOperation(value = "获取订单商品", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<OrderItemDomain>> get(@Param("id") Long id) {
        OrderItemQuery query = new OrderItemQuery();
        query.setOrderId(id);
        PageList<OrderItemDomain>  orderItemDomainPageList = orderItemService.getPageList(query);
        return ResponseEntity.ok().body(orderItemDomainPageList);
    }

    @ApiOperation(value = "创建订单",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(OrderDomain domain) {
        domain.setOrderTime(new Date());
        orderService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改订单", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(OrderDomain domain) {
        orderService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除订单", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@Param("id") Long id) {
        orderService.delete(id);
        return successResponse("删除成功");
    }
}
