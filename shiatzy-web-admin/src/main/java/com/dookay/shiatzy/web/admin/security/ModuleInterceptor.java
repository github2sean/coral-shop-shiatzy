package com.dookay.shiatzy.web.admin.security;


import com.dookay.coral.host.security.domain.PermissionDomain;
import com.dookay.coral.host.security.enums.PermissionAvailableType;
import com.dookay.coral.host.security.query.PermissionQuery;
import com.dookay.coral.host.security.service.IPermissionService;
import com.dookay.coral.security.user.AdminContext;
import com.dookay.coral.security.user.ModuleModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 模块拦截处理
 * @author : kezhan
 * @since : 2017年3月17日
 * @version : v0.0.1
 */
public class ModuleInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = Logger.getLogger(ModuleInterceptor.class);
	
	@Autowired
	private AdminContext adminContext;
	
	@Autowired
	private IPermissionService permissionService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.getSession().removeAttribute("module");
		
		Long moduleId = null;//模块id
		Long module = null;// 父级菜单id
		Long moduleNav = null;// 子菜单id
		String label = null;//模块标签
		
		AdminContext context = adminContext.getCurrent();//通过 上下文获取当前模块资源
		List<ModuleModel> moduleModels = null;
		if(context != null)
			moduleModels = context.getModuleModels();
		
		String currentUrl = request.getServletPath();//获取当前URL
		logger.error(currentUrl);
		PermissionQuery permissionQuery = new PermissionQuery();
		permissionQuery.setUrl(currentUrl);
		permissionQuery.setAvailable(PermissionAvailableType.ON.getValue());
		PermissionDomain permissionDomain = permissionService.getOne(permissionQuery);
		
		if(permissionDomain != null) {
			moduleId = permissionDomain.getModuleId();
			module = permissionDomain.getParentId();
			moduleNav = permissionDomain.getId();
		}
		//判断是否存在父级id
		PermissionDomain domain = null;
		if(permissionDomain != null && !permissionDomain.getParentId().equals(0L)) {
			domain = permissionService.get(permissionDomain.getParentId());
		}
		if(domain != null) {
			if(!domain.getParentId().equals(0L)) {
				moduleId = domain.getModuleId();
				module = domain.getParentId();
				moduleNav = domain.getId();
			}	
		}

		if (moduleModels != null){
			for (ModuleModel moduleModel : moduleModels) {
				if(!StringUtils.isEmpty(label))
					break;

				if(permissionDomain != null){
					if(moduleModel.getId().equals(moduleId))
						label = moduleModel.getLabel();
				}
			}
		}
		
		request.getSession().setAttribute("label", label);
		request.getSession().setAttribute("module", module);
		request.getSession().setAttribute("moduleNav", moduleNav);
		return true;
	}

}
