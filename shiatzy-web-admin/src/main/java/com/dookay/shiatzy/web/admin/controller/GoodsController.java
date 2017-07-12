package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.adapter.payment.alipay.util.httpClient.HttpRequest;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.extension.GoodsCategoryExtension;
import com.dookay.coral.shop.goods.extension.GoodsExtension;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import com.dookay.shiatzy.web.admin.response.goods.ListGoodsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
@RestController
@RequestMapping(value = "/api/goods")
@Api(tags="goods",value = "/api/goods", description = "商品相关接口")
public class GoodsController extends BaseApiController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private GoodsCategoryExtension goodsCategoryExtension;


    @ApiOperation(value = "获取商品列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<GoodsDomain>> list(@ModelAttribute GoodsQuery goodsQuery) {
        PageList<GoodsDomain> goodsDomainPageList = goodsService.getPageList(goodsQuery);
        goodsCategoryExtension.withGoodsCategory(goodsDomainPageList);

        return ResponseEntity.ok().body(goodsDomainPageList);
    }

    @ApiOperation(value = "获取商品", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<GoodsDomain> get(@RequestParam("id") Long id,
                                           @RequestParam(value = "withSpecificationList",required = false) Long withSpecificationList) {
        GoodsDomain domain = goodsService.get(id);
        goodsCategoryExtension.withGoodsCategory(domain);
        if(withSpecificationList!= null){
            goodsService.withSpecificationList(domain);
        }
        return ResponseEntity.ok().body(domain);
    }

    @ApiOperation(value = "创建商品",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(GoodsDomain domain) {
        domain.setCreateTime(new Date());
        goodsService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改商品", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(GoodsDomain domain) {
        goodsService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除商品", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        goodsService.delete(id);
        return successResponse("删除成功");
    }

    @ApiOperation(value = "导入商品", httpMethod = "POST")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity importExcel(@RequestParam("fileName") String fileName) {
        HttpServletRequest request = HttpContext.current().getRequest();
        String msg =  goodsService.importGoods(request.getServletContext().getRealPath("/")+fileName);
        return successResponse(msg);
    }

}
