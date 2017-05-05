package com.dookay.shiatzy.web.admin.security;


import com.dookay.coral.host.security.domain.ModuleDomain;
import com.dookay.coral.host.security.domain.PermissionAssignDomain;
import com.dookay.coral.host.security.domain.PermissionDomain;
import com.dookay.coral.host.security.enums.PermissionType;
import com.dookay.coral.host.security.query.ModuleQuery;
import com.dookay.coral.host.security.query.PermissionAssignQuery;
import com.dookay.coral.host.security.query.PermissionQuery;
import com.dookay.coral.host.security.service.IModuleService;
import com.dookay.coral.host.security.service.IPermissionAssignService;
import com.dookay.coral.host.security.service.IPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 权限资源初始化
 * 
 * @author : kezhan
 * @since : 2017年3月2日
 * @version : v0.0.1
 */
public class InitPermissionListener implements ApplicationListener<ContextRefreshedEvent> {

	private Logger logger = Logger.getLogger(InitPermissionListener.class);

	private IPermissionService permissionService;

	private IModuleService moduleService;

	private IPermissionAssignService permissionAssignService;

	private boolean initializePermissionEnable = false;

	public IPermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(IPermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public IModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(IModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public IPermissionAssignService getPermissionAssignService() {
		return permissionAssignService;
	}

	public void setPermissionAssignService(IPermissionAssignService permissionAssignService) {
		this.permissionAssignService = permissionAssignService;
	}

	public boolean isInitializePermissionEnable() {
		return initializePermissionEnable;
	}

	public void setInitializePermissionEnable(boolean initializePermissionEnable) {
		this.initializePermissionEnable = initializePermissionEnable;
	}

	/*保存父级 菜单 */
	private List<PermissionDomain> clickPermission = new ArrayList<PermissionDomain>();
	/*初始化所有模块*/
	private List<ModuleDomain> moduleDomains = null;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(!initializePermissionEnable) {
			logger.error("InitPermissionListener return...");
			return;
		}
		logger.error("InitPermissionListener start...");
		
		ApplicationContext applicationContext = event.getApplicationContext();
		RequestMappingHandlerMapping rmhp = applicationContext.getBean(RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
		
		moduleDomains = moduleService.getList(new ModuleQuery());//获取所有模块
		
		if(moduleDomains == null) {
			logger.error("InitPermissionListener moduleDomains is null...");
			return;
		}
		
		clickPermission(map);
		menuPermission(map);
		buttonOrattributePermission(map);
		
		logger.debug("clickPermission ..." + clickPermission.size());
		
		//插入超级管理员权限关联
		if (clickPermission.size() > 0) {
			for (int i = 0; i < clickPermission.size(); i++) {
				logger.debug(" --- " + (i+1) + ":" + clickPermission.get(i).getParentId());
				PermissionAssignDomain assignDomain = null;
				if(clickPermission.get(i).getParentId() == 0) {
					PermissionAssignQuery permissionAssignQuery = new PermissionAssignQuery();
					permissionAssignQuery.setRoleId(1L);//超级管理员角色id
					permissionAssignQuery.setPermissionId(clickPermission.get(i).getId());
					assignDomain = permissionAssignService.getOne(permissionAssignQuery);
					if(assignDomain == null) {
						assignDomain = new PermissionAssignDomain();
						assignDomain.setRoleId(1L);
						assignDomain.setPermissionId(clickPermission.get(i).getId());
						assignDomain.setCreateTime(new Date());
						permissionAssignService.create(assignDomain);
					}
				}
			}
		}

	}
	
	/**
	 * click关联级别处理
	 * @param map
	 * @author : kezhan	
	 * @since : 2017年3月2日
	 */
	private void clickPermission(Map<RequestMappingInfo, HandlerMethod> map) {
		Date date = new Date();
		
		for (Iterator<RequestMappingInfo> iterator = map.keySet().iterator(); iterator.hasNext();) {
			PermissionDomain permissionDomain = new PermissionDomain();
			
			RequestMappingInfo info = iterator.next();
			HandlerMethod handlerMethod = map.get(info);
			Method method = handlerMethod.getMethod();
			//获取类级别
			Class<?> class1 = method.getDeclaringClass();
			RequestMapping requestMappingClass = class1.getAnnotation(RequestMapping.class);//获取类级别路由信息
			RequiresPermissions requiresPermissionsClass = class1.getAnnotation(RequiresPermissions.class);//获取类级别shiro权限字符串
			ShiroPermissions shiroPermissionsClass = class1.getAnnotation(ShiroPermissions.class);//获取自定义shiro注解
			
			permissionDomain.setParentId(0L);//类级别父id 默认0
			
			if(requestMappingClass != null) {
				permissionDomain.setUrl(requestMappingClass.value()[0]);
			}
			if(requiresPermissionsClass != null) {
				permissionDomain.setPermission(requiresPermissionsClass.value()[0]);
			}
			if(shiroPermissionsClass != null) {
				logger.debug("--- " + shiroPermissionsClass.name());
				permissionDomain.setAvailable(shiroPermissionsClass.available().getValue());
				permissionDomain.setName(shiroPermissionsClass.name());
				permissionDomain.setType(shiroPermissionsClass.type().getType());
				
				//label
				String label = shiroPermissionsClass.moduleLabel();
				if(!StringUtils.isEmpty(label)) {
					for (ModuleDomain module : moduleDomains) {
						//模块标签相同则使用对应模块
						if(module.getLabel().equals(label)) {
							permissionDomain.setModuleId(module.getId());
						}
					}
				}
			}
			
			boolean isOk = false;
			for (PermissionDomain permission : clickPermission) {
				//String url = permission.getUrl() == null ? "" : permission.getUrl();
				String name = permission.getName() == null ? "" : permission.getName();
				if(name.equals(permissionDomain.getName())) {
					isOk = true;
				}
			}
			permissionDomain.setCreateTime(date);
			if(!isOk && !StringUtils.isEmpty(permissionDomain.getUrl()) && !StringUtils.isEmpty(permissionDomain.getName())
					&& !StringUtils.isEmpty(permissionDomain.getType()) && permissionDomain.getParentId() != null 
					&& !StringUtils.isEmpty(permissionDomain.getPermission()) && permissionDomain.getModuleId() != null
					&& permissionDomain.getAvailable() != null) {
				String url = permissionDomain.getUrl();
				permissionDomain.setUrl("#"); //click url替换后插入
				createPermission(permissionDomain); //插入数据时click 菜单默认# 它的url在权限中没有使用 ， 插入后再次替换给予后面方法使用
				permissionDomain.setUrl(url);
				clickPermission.add(permissionDomain);
			}	
		}
	}
	
	/**
	 * menu 级别处理
	 * @param map
	 * @author : kezhan	
	 * @since : 2016年12月22日
	 */
	private void menuPermission(Map<RequestMappingInfo, HandlerMethod> map) {
		Date date = new Date();
		for (Iterator<RequestMappingInfo> iterator = map.keySet().iterator(); iterator.hasNext();) {
			
			PermissionDomain permissionDomain = new PermissionDomain();
			
			RequestMappingInfo info = iterator.next();
			
			HandlerMethod handlerMethod = map.get(info);
			Method method = handlerMethod.getMethod();
			
			//获取类级别
			Class<?> class1 = method.getDeclaringClass();
			RequestMapping requestMappingClass = class1.getAnnotation(RequestMapping.class);//获取类级别路由信息
			
			//获取方法权限
			RequestMapping requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
			RequiresPermissions requiresPermissions = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
			ShiroPermissions shiroPermissions = handlerMethod.getMethodAnnotation(ShiroPermissions.class);
			
			permissionDomain.setParentId(0L);//父id 默认0
			if(requiresPermissions != null) {
				permissionDomain.setPermission(requiresPermissions.value()[0].trim());
			}
			
			if(requestMapping != null) {
				String[] urls =  requestMapping.value();
				if(urls.length >0)
					permissionDomain.setUrl(urls[0]);
			}
			if(requestMappingClass != null) {
				permissionDomain.setUrl(requestMappingClass.value()[0] + permissionDomain.getUrl());
			}
			
			
			if(shiroPermissions != null && shiroPermissions.type().getType().equals(PermissionType.MENU.getType())) {
				permissionDomain.setAvailable(shiroPermissions.available().getValue());
				permissionDomain.setName(shiroPermissions.name());
				permissionDomain.setType(shiroPermissions.type().getType());
				//label
				String label = shiroPermissions.moduleLabel();
				
				if("index".equals(label)) {//如果是首页
					permissionDomain.setPermission("home:*");
				}
				if(!StringUtils.isEmpty(label)) {
					for (ModuleDomain module : moduleDomains) {
						if(module.getLabel().equals(label)) {
							permissionDomain.setModuleId(module.getId());
						}
					}
				}
				
				for (PermissionDomain domain : clickPermission) {
					if(requestMappingClass != null) {
						logger.error("--- " + domain.getName() + " ---" + domain.getPermission());
						if(domain.getPermission().equals(shiroPermissions.parentPermissions())) { //属于父级
							permissionDomain.setParentId(domain.getId());//修改父id
						}
					}
				}
				
				permissionDomain.setCreateTime(date);
				createPermission(permissionDomain);
				clickPermission.add(permissionDomain);
			}
		}
	}
	
	/**
	 * 按钮 数据级 处理
	 * @param map
	 * @author : kezhan	
	 * @since : 2016年12月22日
	 */
	private void buttonOrattributePermission(Map<RequestMappingInfo, HandlerMethod> map) {
		Date date = new Date();
		for (Iterator<RequestMappingInfo> iterator = map.keySet().iterator(); iterator.hasNext();) {
			
			PermissionDomain permissionDomain = new PermissionDomain();
			
			RequestMappingInfo info = iterator.next();
			
			HandlerMethod handlerMethod = map.get(info);
			Method method = handlerMethod.getMethod();
			
			//获取类级别
			Class<?> class1 = method.getDeclaringClass();
			RequestMapping requestMappingClass = class1.getAnnotation(RequestMapping.class);//获取类级别路由信息
			
			//获取方法权限
			RequestMapping requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
			RequiresPermissions requiresPermissions = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
			ShiroPermissions shiroPermissions = handlerMethod.getMethodAnnotation(ShiroPermissions.class);
			
			permissionDomain.setParentId(0L);//父id 默认0
			if(requiresPermissions != null) {
				permissionDomain.setPermission(requiresPermissions.value()[0]);
			}
			
			if(requestMapping != null) {
				if(requestMapping.value().length>0)
					permissionDomain.setUrl(requestMapping.value()[0]);
			}
			if(requestMappingClass != null) {
				permissionDomain.setUrl(requestMappingClass.value()[0] + permissionDomain.getUrl());
			}
			
			if(shiroPermissions != null && 
					(shiroPermissions.type().getType().equals(PermissionType.BUTTON.getType()) ||
					shiroPermissions.type().getType().equals(PermissionType.ATTRIBUTE.getType()))) {
				permissionDomain.setAvailable(shiroPermissions.available().getValue());
				permissionDomain.setName(shiroPermissions.name());
				permissionDomain.setType(shiroPermissions.type().getType());
				//label
				String label = shiroPermissions.moduleLabel();
				if(!StringUtils.isEmpty(label)) {
					for (ModuleDomain module : moduleDomains) {
						if(module.getLabel().equals(label)) {
							permissionDomain.setModuleId(module.getId());
						}
					}
				}
				
				for (PermissionDomain domain : clickPermission) {
					if(!domain.getUrl().equals("/")) {
						if(domain.getPermission().equals(shiroPermissions.parentPermissions())) { //属于父级
							permissionDomain.setParentId(domain.getId());//修改父id
						}
					}
					
				}
				permissionDomain.setCreateTime(date);
				createPermission(permissionDomain);
				clickPermission.add(permissionDomain);
			}
		}
	}
	
	
	/**
	 * 创建Permission
	 * @param permissionDomain
	 * @author : kezhan	
	 * @since : 2016年12月21日
	 */
	public void createPermission(PermissionDomain permissionDomain) {
		PermissionQuery query = new PermissionQuery();
		query.setUrl(permissionDomain.getUrl());
		query.setPermission(permissionDomain.getPermission());
		PermissionDomain domain =  permissionService.getOne(query);
		if(null == domain) {
			permissionService.create(permissionDomain);
		} else {
			permissionDomain.setId(domain.getId());
		}
	}

}
