package com.dookay.coral.security.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 模块模型
 * 
 * @author : kezhan
 * @since : 2016年12月1日
 * @version : v0.0.1
 */
public class ModuleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* 模块id */
	private Long id;

	/* 模块名称 */
	private String name;

	/* 菜单模块描述 */
	private String description;

	/* 标签 */
	private String label;

	/* 创建时间 */
	private Date createTime;

	/* 修改时间 */
	private Date updateTime;

	/* 对应模块下默认的url */
	private String defaultUrl;
	
	/*权限列表*/
	private List<PermissionModel> permissionModels;

	public ModuleModel() {
		super();
	}

	public ModuleModel(Long id, String name, String description, String label, Date createTime, Date updateTime,
			String defaultUrl) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.label = label;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.defaultUrl = defaultUrl;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public List<PermissionModel> getPermissionModels() {
		return permissionModels;
	}

	public void setPermissionModels(List<PermissionModel> permissionModels) {
		this.permissionModels = permissionModels;
	}

}
