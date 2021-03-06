package com.dookay.coral.host.user.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.security.BCrypt;
import com.dookay.coral.common.security.Digests;
import com.dookay.coral.common.utils.EncodeUtil;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.mapper.AccountMapper;
import com.dookay.coral.host.user.query.AccountQuery;
import com.dookay.coral.host.user.service.IAccountService;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 统一账户表的业务实现类
 *
 * @author : stone
 * @version : v0.0.1
 * @since : 2017年03月02日
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("accountService")
public class AccountServiceImpl extends BaseServiceImpl<AccountDomain> implements IAccountService {
    private static final int SALT_SIZE = 8;
    private static final int HASH_INTERATIONS = 1024;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountDomain getAccount(long id) {
        return super.get(id);
    }

    @Override
    public AccountDomain getAccount(AccountQuery accountQuery) {
        return super.getFirst(accountQuery);
    }

    @Override
    public AccountDomain getAccount(String userName) {
        AccountQuery query = new AccountQuery();
        query.setUserName(userName);
        return super.getOne(query);
    }

    @Override
    public AccountDomain getAccountByEmail(String email) {
        AccountQuery query = new AccountQuery();
        query.setEmail(email);
        return super.getOne(query);
    }

    @Override
    public Boolean validateAccount(String userName, String password) {
        AccountDomain accountDomain=this.getAccount(userName);
        if (accountDomain==null){
            throw new ServiceException("账户不存在");
        }else{
            System.out.println("accountDomain"+accountDomain);
           /* if(accountDomain.getIsValid()==null){//||1!=accountDomain.getIsValid()
                throw new ServiceException("账户未激活，请在邮箱中激活帐号");
            }*/
        }
        return this.comparePassword(accountDomain,password);
    }

    @Override
    public Boolean isExist(String userName) {
        AccountDomain accountDomain=this.getAccount(userName);
        if (accountDomain!=null){
            throw new ServiceException("账户存在");
        }
        return false;
    }

    @Override
    public void registerAccount(AccountDomain accountDomain) {
        if (!StringUtils.isNotBlank(accountDomain.getUserName())) {
            throw new ServiceException("用户名不能为空");
        }
        if(!StringUtils.isNotBlank(accountDomain.getEmail())){
            throw new ServiceException("邮箱不能为空");
        }
        if (!StringUtils.isNotBlank(accountDomain.getPassword())) {
            throw new ServiceException("密码不能为空");
        }
        AccountDomain checkUserName = getAccount(accountDomain.getUserName());
        if (checkUserName != null) {
            throw new ServiceException("用户名已存在");
        }
        AccountDomain checkEmail = getAccountByEmail(accountDomain.getEmail());
        if(checkEmail !=null){
            throw new ServiceException("邮箱已存在");
        }
        encryptPassword(accountDomain);
        super.create(accountDomain);
    }

    @Override
    public List<AccountDomain> getAccountList(AccountQuery accountQuery) {
        return super.getList(accountQuery);
    }

    @Override
    public PageList<AccountDomain> getAccountPageList(AccountQuery accountQuery) {
        return super.getPageList(accountQuery);
    }

    @Override
    public AccountDomain createAccount(AccountDomain accountDomain) {
        AccountDomain account = getAccount(accountDomain.getUserName());
        if (account != null) {
            throw new ServiceException("账户已存在");
        }
        if (!StringUtils.isNotBlank(accountDomain.getUserName())) {
            throw new ServiceException("用户名不能为空");
        }
        if (!StringUtils.isNotBlank(accountDomain.getPassword())) {
            throw new ServiceException("密码不能为空");
        }
        encryptPassword(accountDomain);
        super.create(accountDomain);
        return accountDomain;
    }

    @Override
    public void updateAccount(AccountDomain accountDomain) {
        if (accountDomain == null) {
            throw new ServiceException("账户不存在");
        }
        super.update(accountDomain);
    }

    @Override
    public void resetPassword(AccountDomain accountDomain, String newPassword) {
        if (accountDomain == null) {
            throw new ServiceException("账户不存在");
        }
        if (!StringUtils.isNotBlank(newPassword)) {
            throw new ServiceException("密码不能为空");
        }
        accountDomain.setPassword(newPassword);
        encryptPassword(accountDomain);
        super.update(accountDomain);
    }

    @Override
    public void changePassword(AccountDomain accountDomain, String oldPassword, String newPassword) {
        if (accountDomain == null) {
            throw new ServiceException("账户不存在");
        }
        if (!StringUtils.isNotBlank(newPassword)) {
            throw new ServiceException("密码不能为空");
        }
        if (!StringUtils.isNotBlank(oldPassword)) {
            throw new ServiceException("原密码不能为空");
        }
        accountDomain.setPassword(newPassword);
        encryptPassword(accountDomain);
        super.update(accountDomain);
    }

    @Override
    public void setNewPassword(AccountDomain accountDomain, String newPassword) {
        if (accountDomain == null) {
            throw new ServiceException("账户不存在");
        }
        accountDomain.setPassword(newPassword);
        accountDomain.setRegisterDate(System.currentTimeMillis());
        encryptPassword(accountDomain);
        super.update(accountDomain);
    }

    @Override
    public void changeUserName(AccountDomain accountDomain, String newUserName) {
        if (StringUtils.equals(accountDomain.getUserName(), newUserName)) {
            throw new ServiceException("用户名不能和当前的相同");
        }
        AccountDomain account = getAccount(newUserName);
        if (account != null) {
            throw new ServiceException("该用户名已存在");
        }
        accountDomain.setUserName(newUserName);
        super.update(accountDomain);
    }

    @Override
    public Boolean updateEmailOrPassword(AccountDomain accountDomain, String newEmail, String newPassword) {
        Boolean isSuccess = true;
        try {
            if(StringUtils.isNotBlank(newEmail)){
                changeUserName(accountDomain,newEmail);
            }
            if(StringUtils.isNotBlank(newPassword)){
                changePassword(accountDomain,accountDomain.getPassword(),newPassword);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("修改失败");
        }
            return isSuccess;
    }

    /**
     * 加密admin中的password并修改password
     *
     * @param accountDomain 帐号信息adminDomain
     */
    private void encryptPassword(AccountDomain accountDomain) {
        String generatedSecuredPasswordHash = BCrypt.hashpw(accountDomain.getPassword(), BCrypt.gensalt(10));

        accountDomain.setPassword(generatedSecuredPasswordHash);
    }

    /**
     * 对比admin加密过的password和原始未加密密码是否相等
     *
     * @param accountDomain    帐号信息adminDomain
     * @param originalPassword 未加密密码
     * @return 是否相等
     */
    private boolean comparePassword(AccountDomain accountDomain, String originalPassword) {
       return BCrypt.checkpw(originalPassword,accountDomain.getPassword());
    }
}
