package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.query.AccountQuery;
import com.dookay.coral.host.user.service.IAccountService;
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
@RequestMapping(value = "/api/customer/account")
@Api(tags="account",value = "/api/customer/account", description = "用户相关接口")
public class AccountController extends BaseApiController {

    @Autowired
    private IAccountService accountService;


    @ApiOperation(value = "获取用户列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<AccountDomain>> list(@ModelAttribute AccountQuery accountQuery) {
        PageList<AccountDomain> accountDomainPageList = accountService.getPageList(accountQuery);
        return ResponseEntity.ok().body(accountDomainPageList);
    }


    @ApiOperation(value = "创建用户",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(AccountDomain domain) {
        domain.setCreateTime(new Date());
        accountService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改用户", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(AccountDomain domain) {
        accountService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除用户", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@Param("id") Long id) {
        accountService.delete(id);
        return successResponse("删除成功");
    }
}
