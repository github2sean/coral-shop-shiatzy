package com.dookay.coral.host.security.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.common.utils.DateUtils;
import com.dookay.coral.host.security.domain.AdminDomain;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 管理员信息表的Query
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public class AdminQuery extends Query {

	/*用户名*/
	private String userName;

	/*密码*/
	private String password;

	/*是否可用 0:true 1:false*/
	private Integer locked;

	/*创建时间*/
	private Date createDate;

	/*手机号*/
	private String phone;

	/*使用者*/
	private String realName;

	/*邮箱*/
	private String email;

	private List<Long> idList;

	private String keyword;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(AdminDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		// todo 写查询逻辑
		if (valid(userName))
			criteria.andEqualTo("userName", userName);
		if (valid(password))
			criteria.andEqualTo("password", password);
		if (valid(locked))
			criteria.andEqualTo("locked", locked);
		if (valid(email))
			criteria.andEqualTo("email", email);
		if (valid(createDate)) {
			Date[] dayTime = DateUtils.getDayStartEnd(createDate);
			criteria.andGreaterThanOrEqualTo("createTime", dayTime[0]);
			criteria.andLessThanOrEqualTo("createTime", dayTime[1]);
		}
		if (valid(phone))
			criteria.andEqualTo("phone", phone);
		if (valid(realName))
			criteria.andEqualTo("realName", realName);
		if(valid(idList)){
			criteria.andIn("id",idList);
		}
		if(valid(keyword)){
			String likeStr = "%" + keyword + "%";
			queryCriteria.or().andLike("userName",likeStr);
			queryCriteria.or().andLike("phone",likeStr);
			queryCriteria.or().andLike("realName",likeStr);
		}
		return queryCriteria;
	}

	public AdminQuery() {
		super();
	}

	public AdminQuery(String userName) {
		super();
		this.userName = userName;
	}

	public AdminQuery(
			String userName, String password, Integer locked, Date createDate, String phone,
			String realName, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.locked = locked;
		this.createDate = createDate;
		this.phone = phone;
		this.realName = realName;
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
