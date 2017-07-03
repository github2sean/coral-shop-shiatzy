package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.coral.shop.promotion.query.CouponQuery;
import com.dookay.coral.shop.promotion.service.ICouponService;
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
@RequestMapping(value = "/api/coupon")
@Api(tags="coupon",value = "/api/coupon", description = "优惠券相关接口")
public class CouponController extends BaseApiController {



    @Autowired
    private ICouponService couponService;




    @ApiOperation(value = "获取优惠券列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<CouponDomain>> list(@ModelAttribute CouponQuery couponQuery) {
        PageList<CouponDomain> couponDomainPageList = couponService.getPageList(couponQuery);
        return ResponseEntity.ok().body(couponDomainPageList);
    }

    @ApiOperation(value = "获取优惠券", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<CouponDomain> get(@RequestParam("id") Long id) {
        CouponDomain couponDomain = couponService.get(id);
        return ResponseEntity.ok().body(couponDomain);
    }

    @ApiOperation(value = "创建优惠券",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(CouponDomain domain) {
        domain.setCreateTime(new Date());
        if(domain.getRuleType()==0||domain.getRuleType()==1){
            domain.setLeftTimes(999999999);
            domain.setLimitTimes(999999999);
        }
        couponService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改优惠券", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(CouponDomain domain) {
        if(domain.getRuleType()==0||domain.getRuleType()==1){
            domain.setLeftTimes(999999999);
            domain.setLimitTimes(999999999);
        }
        couponService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除优惠券", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        couponService.delete(id);
        return successResponse("删除成功");
    }

}
