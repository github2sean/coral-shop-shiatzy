package com.dookay.coral.host.user.query;

import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.host.user.enums.AccountTypeEnum;
import lombok.Data;
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
@Data
public class AccountQuery extends Query {

    private String userName;
    private String email;
    private Integer accountType;
    private AccountTypeEnum accountStatus;
    private String keyword;
    private List<Long> ids;
    private Integer isValid;

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

        if(valid(email)){
            criteria.andEqualTo("email",email);
        }
        if (valid(accountType)) {
            criteria.andEqualTo("accountType", accountType);
        }

        if (valid(accountStatus)) {
            criteria.andEqualTo("accountStatus", accountStatus.getValue());
        }

        if(valid(isValid)){
            criteria.andEqualTo("isValid",isValid);
        }

        if (valid(keyword)) {
            String likeStr = "%" + keyword + "%";
            queryCriteria.or().andLike("userName", likeStr);
            queryCriteria.or().andLike("email", likeStr);
            queryCriteria.or().andLike("cellphone", likeStr);
        }

        return queryCriteria;
    }


}
