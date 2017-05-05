package com.dookay.shiatzy.web.admin.model;


import com.dookay.coral.common.mapper.BeanMapper;
import com.dookay.coral.host.security.domain.RoleDomain;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色form模型
 * @author : kezhan
 * @since : 2016年12月7日
 * @version : v0.0.1
 */
public class RoleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*角色id*/
	private Long id;
	/*角色名称*/
	@NotEmpty(message = "角色名不允许空")
	private String name;

	/*是否可用 0:true 1:false*/
	@NotNull(message = "请选择状态")
	private Integer available;

	/*角色描述*/
	@Length(min = 5, max = 20, message = "请输入5到20个字符描述")
	private String description;
	
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

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 将模型转换为对应domain
	 * @param model
	 * @return
	 */
	public static RoleDomain toRoleDomain(RoleModel model) {
		return BeanMapper.map(model, RoleDomain.class);
	}
	
	/**
	 * 将domain转换为对应model
	 * @param domain
	 * @return
	 */
	public static RoleModel toAdminModel(RoleDomain domain) {
		return BeanMapper.map(domain, RoleModel.class);
	}
}
