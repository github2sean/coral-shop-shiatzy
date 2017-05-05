package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationOptionDomain;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationOptionDomain;
import com.dookay.coral.shop.goods.query.PrototypeSpecificationOptionQuery;
import com.dookay.coral.shop.goods.query.PrototypeSpecificationQuery;
import com.dookay.coral.shop.goods.service.IGoodsPrototypeService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationService;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/3
 */
@RestController
@RequestMapping(value = "/api/goods/prototype/specification/option")
@Api(tags="goodsPrototypeSpecificationOption",value = "/api/goods/prototype/specification/option", description = "商品原型规格选项相关接口")
public class PrototypeSpecificationOptionController extends BaseApiController {

    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;

    @ApiOperation(value = "列出商品原型规格选项", httpMethod = "GET", response = PrototypeSpecificationOptionDomain.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<PrototypeSpecificationOptionDomain>> list(@ModelAttribute PrototypeSpecificationOptionQuery query) {
        PageList<PrototypeSpecificationOptionDomain> pageList = prototypeSpecificationOptionService.getPageList(query);

        return ResponseEntity.ok().body(pageList);
    }
    
    @ApiOperation(value = "获取商品原型规格选项", httpMethod = "GET", response = PrototypeSpecificationOptionDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PrototypeSpecificationOptionDomain> get(@Param("id") Long id) {
        PrototypeSpecificationOptionDomain domain = prototypeSpecificationOptionService.get(id);

        return ResponseEntity.ok().body(domain);
    }

    @ApiOperation(value = "创建商品原型规格选项",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(PrototypeSpecificationOptionDomain domain) {
        prototypeSpecificationOptionService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改商品原型规格选项", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(PrototypeSpecificationOptionDomain domain) {
        prototypeSpecificationOptionService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除商品原型规格选项", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@Param("id") Long id) {
        prototypeSpecificationOptionService.delete(id);
        return successResponse("删除成功");
    }
}
