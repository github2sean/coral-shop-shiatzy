package com.dookay.coral.security.user;

import java.io.Serializable;
import java.util.List;

/**
 * 权限对象
 * 
 * @author : kezhan
 * @since : 2016年12月14日
 * @version : v0.0.1
 */
public class PermissionModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* id */
	private Long id;

	/* 权限名称 */
	private String name;

	/* 权限类型 */
	private String type;

	/* 资源路径 */
	private String url;

	/* 资源父ID */
	private Long parentId;

	/* 资源父编号列表 */
	private String parentIds;

	/* 权限字符串 */
	private String permission;

	/* 模块ID */
	private Long moduleId;

	/* 子模块权限列表 */
	private List<PermissionModel> permissionModels;

	public PermissionModel() {
		super();
	}

	public PermissionModel(Long id, String name, String type, String url, Long parentId, String parentIds, String permission,
			Long moduleId, List<PermissionModel> permissionModels) {
		super();
		this.id= id;
		this.name = name;
		this.type = type;
		this.url = url;
		this.parentId = parentId;
		this.parentIds = parentIds;
		this.permission = permission;
		this.moduleId = moduleId;
		this.permissionModels = permissionModels;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public List<PermissionModel> getPermissionModels() {
		return permissionModels;
	}

	public void setPermissionModels(List<PermissionModel> permissionModels) {
		this.permissionModels = permissionModels;
	}

}
