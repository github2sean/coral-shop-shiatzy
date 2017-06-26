package com.dookay.coral.host.user.domain;

import com.dookay.coral.host.user.enums.AccountTypeEnum;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 统一账户表的domain
 *
 * @author : stone
 * @version : v0.0.1
 * @since : 2017年03月02日
 */
@Data
@Table(name = "t_user_account")
public class AccountDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /*账号id*/
    @Id
    private Long id;

    /*用户名*/
    private String userName;

    /*密码*/
    private String password;

    /*盐*/
    private String salt;

    /*账户类型*/
    private Integer accountType;

    /*账户状态*/
    private Integer accountStatus;

    /*创建时间*/
    private Date createTime;

    /*邮箱*/
    private String email;

    /*手机*/
    private String cellphone;

    /*是否有效*/
    private Integer isValid;

    /*修改密码校验码*/
    private String validateCode;

    /*是否有效*/
    private Long registerDate;

    /*激活校验码*/
    private String activeCode;


}
