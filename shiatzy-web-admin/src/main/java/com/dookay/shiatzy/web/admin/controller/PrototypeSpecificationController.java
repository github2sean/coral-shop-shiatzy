package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.GoodsPrototypeDomain;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationDomain;
import com.dookay.coral.shop.goods.query.GoodsPrototypeQuery;
import com.dookay.coral.shop.goods.query.PrototypeSpecificationQuery;
import com.dookay.coral.shop.goods.service.IGoodsPrototypeService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationService;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/3
 */
@RestController
@RequestMapping(value = "/api/goods/prototype/specification")
@Api(tags="goodsPrototypeSpecification",value = "/api/goods/prototype/specification", description = "商品原型规格相关接口")
public class PrototypeSpecificationController extends BaseApiController {
    @Autowired
    private IGoodsPrototypeService goodsPrototypeService;
    @Autowired
    private IPrototypeSpecificationService prototypeSpecificationService;

    @ApiOperation(value = "列出商品原型规格", httpMethod = "GET", response = PrototypeSpecificationDomain.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<PrototypeSpecificationDomain>> list(@ModelAttribute PrototypeSpecificationQuery query) {
        PageList<PrototypeSpecificationDomain> prototypeSpecificationDomainPageList = prototypeSpecificationService.getPageList(query);

        return ResponseEntity.ok().body(prototypeSpecificationDomainPageList);
    }

    @ApiOperation(value = "获取商品原型规格", httpMethod = "GET", response = PrototypeSpecificationDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PrototypeSpecificationDomain> get(@Param("id") Long id) {
        PrototypeSpecificationDomain prototypeSpecificationDomain = prototypeSpecificationService.get(id);

        return ResponseEntity.ok().body(prototypeSpecificationDomain);
    }

    @ApiOperation(value = "创建商品原型规格",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(PrototypeSpecificationDomain domain) {
        prototypeSpecificationService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改商品原型规格", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(PrototypeSpecificationDomain domain) {
        prototypeSpecificationService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除商品原型规格", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@Param("id") Long id) {
        prototypeSpecificationService.delete(id);
        return successResponse("删除成功");
    }
}
