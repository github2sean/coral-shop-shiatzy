package com.dookay.coral.host.user.query;

import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.host.user.enums.AccountTypeEnum;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;

import java.util.List;

/**
 * 统一账户表的Query
 *
 * @author : stone
 * @version : v0.0.1
 * @since : 2017年03月02日
 */
public class AccountQuery extends Query {

    private String userName;
    private Integer accountType;
    private AccountTypeEnum accountStatus;
    private String keyword;
    private List<Long> ids;

    @Override
    public QueryCriteria toCriteria() {
        QueryCriteria queryCriteria = new QueryCriteria(AccountDomain.class);
        Example.Criteria criteria = queryCriteria.createCriteria();

        if (valid(ids)) {
            criteria.andIn("id", ids);
        }

        if (valid(userName)) {
            criteria.andEqualTo("userName", userName);
        }

        if (valid(accountType)) {
            criteria.andEqualTo("accountType", accountType);
        }

        if (valid(accountStatus)) {
            criteria.andEqualTo("accountStatus", accountStatus.getValue());
        }

        if (valid(keyword)) {
            String likeStr = "%" + keyword + "%";
            queryCriteria.or().andLike("userName", likeStr);
            queryCriteria.or().andLike("email", likeStr);
            queryCriteria.or().andLike("cellphone", likeStr);
        }

        return queryCriteria;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public AccountTypeEnum getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountTypeEnum accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
