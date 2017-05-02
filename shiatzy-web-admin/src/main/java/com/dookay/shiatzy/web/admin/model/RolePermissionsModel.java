package com.dookay.shiatzy.web.admin.model;

import java.io.Serializable;
import java.util.List;

/**
 * 角色权限模型
 * 
 * @author : kezhan
 * @since : 2016年12月8日
 * @version : v0.0.1
 */
public class RolePermissionsModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* 权限id */
	private Long id;
	/* 权限父id */
	private Long parentId;
	/* 权限文本描述 */
	private String text;
	/* 模块id */
	private Long moduleId;
	/* 权限状态是否选中 */
	private Boolean state;

	/*一级菜单id集合*/
	private List<Long> clickId;
	/*二级菜单id集合*/
	private List<Long> menuId;
	/*三级菜单id集合*/
	private List<Long> attributeId;
	/*类型*/
	private String type;

	public RolePermissionsModel() {
		super();
	}

	public RolePermissionsModel(
			Long id, Long parentId, String text, Long moduleId, Boolean state, List<Long> clickId,
			List<Long> menuId, List<Long> attributeId, String type) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.text = text;
		this.moduleId = moduleId;
		this.state = state;
		this.clickId = clickId;
		this.menuId = menuId;
		this.attributeId = attributeId;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public List<Long> getClickId() {
		return clickId;
	}

	public void setClickId(List<Long> clickId) {
		this.clickId = clickId;
	}

	public List<Long> getMenuId() {
		return menuId;
	}

	public void setMenuId(List<Long> menuId) {
		this.menuId = menuId;
	}

	public List<Long> getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(List<Long> attributeId) {
		this.attributeId = attributeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
