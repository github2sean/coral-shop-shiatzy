package com.dookay.shiatzy.web.admin.security;

import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.domain.ModuleDomain;
import com.dookay.coral.host.security.domain.PermissionDomain;
import com.dookay.coral.host.security.enums.PermissionAvailableType;
import com.dookay.coral.host.security.enums.PermissionType;
import com.dookay.coral.host.security.query.AdminQuery;
import com.dookay.coral.host.security.query.PermissionQuery;
import com.dookay.coral.host.security.service.IAdminService;
import com.dookay.coral.host.security.service.IModuleService;
import com.dookay.coral.host.security.service.IPermissionService;
import com.dookay.coral.security.user.AdminModel;
import com.dookay.coral.security.user.ModuleModel;
import com.dookay.coral.security.user.PermissionModel;
import com.dookay.coral.security.user.UserSecurityRealm;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 用户安全领域具体实现
 * @author : kezhan
 * @since : 2017年1月17日
 * @version : v0.0.1
 */
public class AdminSecurityReaml extends UserSecurityRealm {
	
	@Autowired
	private IAdminService adminService;
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IPermissionService permissionService;
	
	@Override
	public AdminModel login(String userName, String password, String ip) throws Exception {
		AdminDomain adminDomain = adminService.login(userName, password, ip);
		AdminModel adminModel = null;
		if(null != adminDomain) {
			adminModel = new AdminModel(adminDomain.getId(), adminDomain.getRealName(), adminDomain.getUserName(), adminDomain.getPassword(),
					adminDomain.getSalt(), adminDomain.getLocked(), adminDomain.getLastLoginIp(), adminDomain.getLoginCount());
		}
		return adminModel;
	}


	@Override
	public AdminModel getAdmin(String userName){
		AdminDomain adminDomain = adminService.getAdmin(userName);
		AdminModel adminModel = null;
		if(null != adminDomain) {
			adminModel = new AdminModel(adminDomain.getId(), adminDomain.getRealName(), adminDomain.getUserName(), adminDomain.getPassword(),
					adminDomain.getSalt(), adminDomain.getLocked(), adminDomain.getLastLoginIp(), adminDomain.getLoginCount());
		}
		return adminModel;
	}

	@Override
	public AdminModel getAdminModel(AdminModel adminModel) {
		AdminModel model = null;
		AdminDomain adminDomain = null;
		if(adminModel != null) {
			AdminQuery adminQuery = new AdminQuery();
			adminQuery.setUserName(adminModel.getUserName());
			adminDomain = adminService.getOne(adminQuery);
		}
		
		if(null != adminDomain) {
			model = new AdminModel(adminDomain.getId(), adminDomain.getRealName(), adminDomain.getUserName(), adminDomain.getPassword(),
					adminDomain.getSalt(), adminDomain.getLocked(), adminDomain.getLastLoginIp(), adminDomain.getLoginCount());
		}
		return model;
	}

	@Override
	public Set<String> getAdminPermissions(AdminModel adminModel) {
		Set<String> permissions = Sets.newHashSet();
		Set<PermissionDomain> permissionDomains = adminService.getAdminPermissions(verifyAdmin(adminModel));
		for (PermissionDomain permissionDomain : permissionDomains) {
			permissions.add(permissionDomain.getPermission());
		}
		return permissions;
	}

	@Override
	public Set<String> getAdminRoles(AdminModel adminModel) {
		return adminService.getAdminRoles(verifyAdmin(adminModel));
	}

	@Override
	public List<ModuleModel> getModuleModels(AdminModel adminModel) {
		List<ModuleDomain> moduleDomains = moduleService.getModulesByAdmin(verifyAdmin(adminModel));
		//处理对应用户 对应模块下的对应权限对象集合
		Set<PermissionDomain> permissionDomains = adminService.getAdminPermissions(verifyAdmin(adminModel));
		List<ModuleModel> moduleModels = null;
		if(moduleDomains != null && moduleDomains.size() > 0 && 
				permissionDomains != null && permissionDomains.size() > 0) {
			moduleModels = modulePermission(permissionDomains, moduleDomains);
		}
		
		return moduleModels;
	}

	/**
	 * 处理用户对于的模块下的权限集合
	 * @param permissionDomains
	 * @param moduleDomains
	 * @return
	 * @author : kezhan	
	 * @since : 2017年2月7日
	 */
	public List<ModuleModel> modulePermission(Set<PermissionDomain> permissionDomains, List<ModuleDomain> moduleDomains) {
		List<PermissionModel> permissionModels = new ArrayList<PermissionModel>();
		if(permissionDomains != null) {
			List<Long> idList = new ArrayList<Long>();
			//处理对于模块下的权限集合
			for (PermissionDomain permissionDomain : permissionDomains) {
				idList.add(permissionDomain.getId());
			}
			//按id排序查询
			List<PermissionDomain> domains = permissionService.getList(new PermissionQuery(idList, PermissionAvailableType.ON.getValue(),
					false, "id"));
			
			//排序处理菜单列表
			click(domains, permissionModels);
			menu(domains, permissionModels);
			button(domains, permissionModels);
			attribute(domains, permissionModels);
		}		
		//模块处理
		List<ModuleModel> moduleModels = new ArrayList<ModuleModel>();

		comparePermissionById(permissionModels);//按id排序权限对象
		module(permissionModels, moduleModels, moduleDomains);

		return moduleModels;
	}
	
	/**
	 * 按id排序权限对象
	 * 
	 * int compare(PermissionModel model1, PermissionModel model2) 返回一个基本类型的整型，  
	 * 返回负数表示：model1 小于 model2，  
	 * 返回正数表示：model1 大于 model2。  
	 * @param permissionModels
	 * @author : kezhan	
	 * @since : 2017年2月7日
	 */
	public void comparePermissionById(List<PermissionModel> permissionModels) {
		Collections.sort(permissionModels, new Comparator<PermissionModel>(){
			public int compare(PermissionModel model1, PermissionModel model2) {
				if(model1.getId() > model2.getId()){  
					return 1;
				}  
				return -1;  
			}  
		});   
	}
	
	/**
	 * click 类型处理
	 * @param domains
	 * @param permissionModels
	 * @author : kezhan	
	 * @since : 2016年12月14日
	 */
	public void click(List<PermissionDomain> domains, List<PermissionModel> permissionModels) {
		if (domains != null) {
			for (PermissionDomain domain : domains) {
				//click
				if(domain.getType().equals(PermissionType.CLICK.getType())) {
					PermissionModel clickModel = new PermissionModel(domain.getId(), domain.getName(), domain.getType(), domain.getUrl(),
							domain.getParentId(), domain.getParentIds(), domain.getPermission(), domain.getModuleId(), null);
					
					List<PermissionDomain> menuList = permissionService.getList(new PermissionQuery(domain.getModuleId(), domain.getId(),
							PermissionType.MENU.getType(), PermissionAvailableType.ON.getValue(), false, "id"));
					
					//处理click 下模块
					if(menuList != null && menuList.size() > 0) {
						List<PermissionModel> permissionMenus = new ArrayList<PermissionModel>();
						for (PermissionDomain menu : menuList) {
							PermissionModel menuModel = new PermissionModel(menu.getId(), menu.getName(), menu.getType(), menu.getUrl(),
									menu.getParentId(), menu.getParentIds(), menu.getPermission(), menu.getModuleId(), null);
							permissionMenus.add(menuModel);
						}
						clickModel.setPermissionModels(permissionMenus);//click下对应的menu
					}
					permissionModels.add(clickModel);
				}
			}
		}		
	}
	
	/**
	 * menu 类型处理
	 * @param domains
	 * @param permissionModels
	 * @author : kezhan	
	 * @since : 2016年12月14日
	 */
	public void menu(List<PermissionDomain> domains, List<PermissionModel> permissionModels) {
		if (domains != null) {
			for (PermissionDomain domain : domains) {
				if(domain.getType().equals(PermissionType.MENU.getType())) {
					PermissionModel menu = new PermissionModel(domain.getId(), domain.getName(), domain.getType(), domain.getUrl(),
							domain.getParentId(), domain.getParentIds(), domain.getPermission(), domain.getModuleId(), null);
					//判断是否存在上级
					if(domain.getParentId() != 0) {
						PermissionDomain permissionDomain = permissionService.get(domain.getParentId());
						PermissionModel permissionClick = new PermissionModel(permissionDomain.getId(), permissionDomain.getName(), permissionDomain.getType(),
								permissionDomain.getUrl(), permissionDomain.getParentId(), permissionDomain.getParentIds(), permissionDomain.getPermission(), 
								permissionDomain.getModuleId(), null);
						//判断是否存在上级菜单，存在测加入，不存在则新建
						boolean falt = false;
 						for (PermissionModel permissionModel : permissionModels) {
						 	if(permissionClick.getId().equals(permissionModel.getId())){
						 		falt = true;
						 		List<PermissionModel> models = permissionModel.getPermissionModels();
						 		models.add(menu);
						 	}
						}
 						//新建
						if(!falt) {	
							List<PermissionModel> models = new ArrayList<PermissionModel>();
							models.add(menu);
							permissionClick.setPermissionModels(models);
							permissionModels.add(permissionClick);	
						}	
					} else {
						permissionModels.add(menu);
					}
				}
			}
		}		
	}
	
	/**
	 * button 类型处理
	 * @param domains
	 * @param permissionModels
	 * @author : kezhan	
	 * @since : 2016年12月14日
	 */
	public void button(List<PermissionDomain> domains, List<PermissionModel> permissionModels) {
		if (domains != null) {
			for (PermissionDomain domain : domains) {
				if(domain.getType().equals(PermissionType.BUTTON.getType())) {
					PermissionDomain permission =  permissionService.getOne(new PermissionQuery(domain.getParentId(),
							PermissionAvailableType.ON.getValue(), false, null));
					
					PermissionModel menu = new PermissionModel(permission.getId(), permission.getName(), permission.getType(), permission.getUrl(),
							permission.getParentId(), permission.getParentIds(), permission.getPermission(), permission.getModuleId(), null);
					boolean isOk = false;
					//循环处理是否存在当前菜单
					for (PermissionModel permissionModel : permissionModels) {
						if(permissionModel.getName().equals(menu.getName())) {
							isOk = true;
						}
					}
					
					//判断是否存在上级
					if(menu.getParentId() != 0) { 
						PermissionDomain permissionDomain = permissionService.get(menu.getParentId());
						PermissionModel permissionClick = new PermissionModel(permissionDomain.getId(), permissionDomain.getName(), permissionDomain.getType(),
								permissionDomain.getUrl(), permissionDomain.getParentId(), permissionDomain.getParentIds(), permissionDomain.getPermission(), 
								permissionDomain.getModuleId(), null);
						
						//判断是否存在上级菜单，存在测加入，不存在则新建
						boolean flat = false;
 						for (PermissionModel permissionModel : permissionModels) {
 							List<PermissionModel> models = null;
						 	if(permissionClick.getId().equals(permissionModel.getId())){
						 		flat = true;
						 		models = permissionModel.getPermissionModels();
						 		boolean flat2 = false;
						 		for (PermissionModel model2 : models) {
									if(model2.getId().equals(menu.getId())) {
										flat2 = true;
									}
								}
						 		if(!flat2) {
						 			models.add(menu);
						 			//排序
								 	if(models != null) {
								 		comparePermissionById(models);
								 	}
						 		}
						 	}
						}
 						//新建
						if(!flat) {	
							List<PermissionModel> models = new ArrayList<PermissionModel>();
							models.add(menu);
							permissionClick.setPermissionModels(models);
							permissionModels.add(permissionClick);
						}	
					} else { 
						if(!isOk) {
							permissionModels.add(menu);
						}
					}
					
					
				}
			}
		}		
	}
	
	/**
	 * attribute 类型处理
	 * @param domains
	 * @param permissionModels
	 * @author : kezhan	
	 * @since : 2016年12月14日
	 */
	public void attribute(List<PermissionDomain> domains, List<PermissionModel> permissionModels) {
		if (domains != null) {
			for (PermissionDomain domain : domains) {
				if(domain.getType().equals(PermissionType.ATTRIBUTE.getType())) {
					PermissionDomain permission =  permissionService.getOne(new PermissionQuery(domain.getParentId(),
							PermissionAvailableType.ON.getValue(), false, null));
					
					PermissionModel menu = new PermissionModel(permission.getId(), permission.getName(), permission.getType(), permission.getUrl(),
							permission.getParentId(), permission.getParentIds(), permission.getPermission(), permission.getModuleId(), null);
					boolean isOk = false;
					//循环处理是否存在当前菜单
					for (PermissionModel permissionModel : permissionModels) {
						if(permissionModel.getName().equals(menu.getName())) {
							isOk = true;
						}
					}
					
					//判断是否存在上级
					if(menu.getParentId() != 0) { 
						PermissionDomain permissionDomain = permissionService.get(menu.getParentId());
						PermissionModel permissionClick = new PermissionModel(permissionDomain.getId(), permissionDomain.getName(), permissionDomain.getType(),
								permissionDomain.getUrl(), permissionDomain.getParentId(), permissionDomain.getParentIds(), permissionDomain.getPermission(), 
								permissionDomain.getModuleId(), null);
						//判断是否存在上级菜单，存在测加入，不存在则新建
						boolean flat = false;
 						for (PermissionModel permissionModel : permissionModels) {
 							List<PermissionModel> models = null;
						 	if(permissionClick.getId().equals(permissionModel.getId())){
						 		flat = true;
						 		models = permissionModel.getPermissionModels();
						 		boolean flat2 = false;
						 		for (PermissionModel model2 : models) {
									if(model2.getId().equals(menu.getId())) {
										flat2 = true;
									}
								}
						 		if(!flat2) {
						 			models.add(menu);
						 			//排序
								 	if(models != null) {
								 		comparePermissionById(models);
								 	}
						 		}
						 	}
						 	
						}
 						//新建
						if(!flat) {	
							List<PermissionModel> models = new ArrayList<PermissionModel>();
							models.add(menu);
							permissionClick.setPermissionModels(models);
							permissionModels.add(permissionClick);
						}	
						
					} else {
						if(!isOk) {
							permissionModels.add(menu);
						}
					}
					
					
					
				}
			}
		}		
	}
	
	/**
	 * module 处理
	 * @param permissionModels
	 * @param moduleModels
	 * @param moduleDomains
	 * @author : kezhan	
	 * @since : 2016年12月14日
	 */
	public void module(List<PermissionModel> permissionModels, List<ModuleModel> moduleModels, List<ModuleDomain> moduleDomains) {
		for (ModuleDomain moduleDomain : moduleDomains) {
			ModuleModel moduleModel = new ModuleModel(moduleDomain.getId(), moduleDomain.getName(), moduleDomain.getDescription(),
					moduleDomain.getLabel(), moduleDomain.getCreateTime(), moduleDomain.getUpdateTime(), null);
			moduleModels.add(moduleModel);
			
			for (PermissionModel permission : permissionModels) {
				if(permission == null) {
					continue;
				}
				//处理模块下默认url
				if(permission.getModuleId().equals(moduleDomain.getId())) {
					if(permission.getType().equals(PermissionType.MENU.getType())) {
						for (ModuleModel module : moduleModels) {
							if(module.getId().equals(permission.getModuleId()) && module.getDefaultUrl() == null) {
								moduleModel.setDefaultUrl(permission.getUrl());
							}
						}
						
					}
					
					if(permission.getType().equals(PermissionType.CLICK.getType())) {
						for (ModuleModel module : moduleModels) {
							//判断模块下是否存在默认url
							if(module.getId().equals(permission.getModuleId()) && module.getDefaultUrl() == null) {
								List<PermissionModel> models = permission.getPermissionModels();
								if(models != null) {
									PermissionModel permissionModel = models.get(0); //取第一个;
									if(permissionModel != null) {
										moduleModel.setDefaultUrl(permissionModel.getUrl());
									}
								}
								
								
							}							
						}
					}
					
					List<PermissionModel> models = moduleModel.getPermissionModels();
					if(models == null) {
						models = new ArrayList<PermissionModel>();
						moduleModel.setPermissionModels(models);
					}
					models.add(permission);
					
				}
			}	
		}
	}
	
	/**
	 * 验证AdminModel
	 * @param adminModel
	 * @return
	 * @author : kezhan	
	 * @since : 2017年2月7日
	 */
	public AdminDomain verifyAdmin(AdminModel adminModel) {
		AdminDomain adminDomain = new AdminDomain();
		if(adminModel != null) {
			adminDomain.setId(adminModel.getId());
		}
		return adminDomain;
	}


}
