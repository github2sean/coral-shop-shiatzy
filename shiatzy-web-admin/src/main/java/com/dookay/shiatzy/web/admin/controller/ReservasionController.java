package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.extension.ReservationExtension;
import com.dookay.coral.shop.order.extension.ReturnRequestExtension;
import com.dookay.coral.shop.order.query.ReservationQuery;
import com.dookay.coral.shop.order.query.ReturnRequestQuery;
import com.dookay.coral.shop.order.service.IReservationItemService;
import com.dookay.coral.shop.order.service.IReservationService;
import com.dookay.coral.shop.order.service.IReturnRequestService;
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
@RequestMapping(value = "/api/reservation")
@Api(tags="reservation",value = "/api/reservation", description = "预订单相关接口")
public class ReservasionController extends BaseApiController {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private ReservationExtension reservationExtension;

    private final static Integer ACCEPT_BACK = 3;
    private final static Integer REFUSE_BACK = 4;

    @ApiOperation(value = "获取预订单列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<ReservationDomain>> list(@ModelAttribute ReservationQuery reservationQuery) {
        PageList<ReservationDomain> reservationDomainPageList = reservationService.getPageList(reservationQuery);
        reservationExtension.withReservationItem(reservationDomainPageList);
        reservationExtension.withCustomer(reservationDomainPageList);
        return ResponseEntity.ok().body(reservationDomainPageList);
    }

    @ApiOperation(value = "获取预订单商品", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<ReservationDomain> get(@RequestParam("id") Long id) {
        ReservationDomain reservationDomain = reservationService.get(id);
        reservationExtension.withReservationItem(reservationDomain);
        reservationExtension.withCustomer(reservationDomain);
        return ResponseEntity.ok().body(reservationDomain);
    }

    @ApiOperation(value = "创建预订单",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(ReservationDomain domain) {
        domain.setCreateTime(new Date());
        reservationService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改预订单", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(ReservationDomain domain) {
        reservationService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除预订单", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        reservationService.delete(id);
        return successResponse("删除成功");
    }

    

}
