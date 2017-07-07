package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.order.domain.ReturnRequestDomain;
import com.dookay.coral.shop.order.extension.ReturnRequestExtension;
import com.dookay.coral.shop.order.query.ReturnRequestQuery;
import com.dookay.coral.shop.order.service.*;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import com.dookay.shiatzy.web.admin.form.ReturnForm;
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
@RequestMapping(value = "/api/returnRequest")
@Api(tags="returnRequest",value = "/api/returnRequest", description = "退货单相关接口")
public class ReturnRequestController extends BaseApiController {

    @Autowired
    private IReturnRequestService returnRequestService;

    @Autowired
    private ReturnRequestExtension returnRequestExtension;

    private final static Integer ACCEPT_BACK = 3;
    private final static Integer REFUSE_BACK = 4;

    @ApiOperation(value = "获取退货单列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<ReturnRequestDomain>> list(@ModelAttribute ReturnRequestQuery returnRequestQuery) {
        PageList<ReturnRequestDomain> returnRequestDomainPageList = returnRequestService.getPageList(returnRequestQuery);
        returnRequestExtension.withReturnRequestItem(returnRequestDomainPageList);
        returnRequestExtension.withCustomer(returnRequestDomainPageList);
        return ResponseEntity.ok().body(returnRequestDomainPageList);
    }

    @ApiOperation(value = "获取退货单商品", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<ReturnRequestDomain> get(@RequestParam("id") Long id) {
        ReturnRequestDomain returnRequestDomain = returnRequestService.get(id);
        returnRequestExtension.withReturnRequestItem(returnRequestDomain);
        returnRequestExtension.withCustomer(returnRequestDomain);
        return ResponseEntity.ok().body(returnRequestDomain);
    }

    @ApiOperation(value = "创建退货单",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(ReturnRequestDomain domain) {
        domain.setOrderTime(new Date());
        returnRequestService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改退货单", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(ReturnRequestDomain domain) {
        returnRequestService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除退货单", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        returnRequestService.delete(id);
        return successResponse("删除成功");
    }

    @ApiOperation(value = "是否同意退货", httpMethod = "POST")
    @RequestMapping(value = "/isAgree", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity isAgree(@RequestParam("id") Long id,Integer isAgree,@ModelAttribute ReturnForm returnForm) {
        returnRequestService.isAgree(id,isAgree,returnForm.getReturnItemList());
        return successResponse("操作成功");
    }


}
