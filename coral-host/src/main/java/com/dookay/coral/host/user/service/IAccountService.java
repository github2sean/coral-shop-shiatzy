package com.dookay.coral.host.user.service;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.host.user.query.AccountQuery;

import java.util.List;

/**
 * 统一账户表的业务层接口
 *
 * @author : stone
 * @version : v0.0.1
 * @since : 2017年03月02日
 */
public interface IAccountService extends IBaseService<AccountDomain> {

    AccountDomain getAccount(long id);

    AccountDomain getAccount(AccountQuery accountQuery);

    AccountDomain getAccount(String userName);

    AccountDomain getAccountByEmail(String email);

    Boolean isExist(String userName);

    List<AccountDomain> getAccountList(AccountQuery accountQuery);

    PageList<AccountDomain> getAccountPageList(AccountQuery accountQuery);

    Boolean validateAccount(String userName,String password);

    void  registerAccount(AccountDomain accountDomain);

    AccountDomain createAccount(AccountDomain accountDomain);

    void updateAccount(AccountDomain accountDomain);

    void resetPassword(AccountDomain accountDomain, String newPassword);

    void changePassword(AccountDomain accountDomain, String oldPassword, String newPassword);

    void changeUserName(AccountDomain accountDomain,String newUserName);

    Boolean updateEmailOrPassword(AccountDomain accountDomain,String newEmail,String newPassword);

}
