package com.dookay.shiatzy.web.admin.controller;


import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.service.IAdminService;
import com.dookay.coral.security.token.TokenManager;
import com.dookay.coral.security.user.*;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import com.dookay.shiatzy.web.admin.model.LoginModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 登录
 * @author : kezhan
 * @since : 2017年1月23日
 * @version : v0.0.1
 */
@RestController
@RequestMapping(value = "/api/passport")
@Api(tags="passport",value = "/api/passport", description = "登录相关接口", position = 5)
public class PassportController extends BaseApiController {

	private Logger logger = Logger.getLogger(PassportController.class);

	@Autowired
	private IAdminService adminService;
    @Autowired
    private UserSecurityRealm userSecurityRealm;

	@ApiOperation(value = "登录", notes = "登录", httpMethod = "POST")
	@RequestMapping(value = "/login")
	public LoginSuccessResponse login(@ModelAttribute LoginModel loginModel) throws Exception {
		AdminDomain adminDomain = adminService.login(loginModel.getUserName(),loginModel.getPassword(),"");
        AdminModel adminModel = new AdminModel(adminDomain.getId(), adminDomain.getRealName(), adminDomain.getUserName(), adminDomain.getPassword(),
                adminDomain.getSalt(), adminDomain.getLocked(), adminDomain.getLastLoginIp(), adminDomain.getLoginCount());

        Set<String>  roles = userSecurityRealm.getAdminRoles(adminModel);
        List<ModuleModel> moduleModels = userSecurityRealm.getModuleModels(adminModel);
        Set<String> adminPermissions = new HashSet<>();

        for (ModuleModel moduleModel : moduleModels){
            List<String> clickPermissions = moduleModel.getPermissionModels().stream().map(PermissionModel::getPermission).collect(Collectors.toList());
            adminPermissions.addAll(clickPermissions);//加入click权限
            for(PermissionModel menuPermission :moduleModel.getPermissionModels()){
                adminPermissions.add(menuPermission.getPermission());//加入menu权限
                for (PermissionModel buttonPermission :menuPermission.getPermissionModels()){
                    adminPermissions.add(buttonPermission.getPermission());//加入button权限
                }
            }
        }

        AdminContext adminContext = new AdminContext();
        adminContext.setAdminModel(adminModel);
        adminContext.setModuleModels(moduleModels);
        adminContext.setRoles(roles);
        adminContext.setPermissions(adminPermissions);

        AdminContext.signIn(adminContext);

        String token = TokenManager.getMe().generateToken(adminContext);
        LoginSuccessResponse loginSuccessResponse = new LoginSuccessResponse();
		loginSuccessResponse.setAdminModel(adminModel);
        loginSuccessResponse.setPermissions(adminPermissions);
        loginSuccessResponse.setRoles(roles);
        loginSuccessResponse.setModuleModels(moduleModels);
        loginSuccessResponse.setToken(token);
		return loginSuccessResponse;
	}
}
