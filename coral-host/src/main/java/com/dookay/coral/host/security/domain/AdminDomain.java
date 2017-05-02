package com.dookay.coral.host.security.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 管理员信息表的domain
 *
 * @author : luxor
 * @version : v0.0.1
 * @since : 2017年03月02日
 */
@Data
@Table(name = "t_security_admin")
public class AdminDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /*管理员id*/
    @Id
    private Long id;

    /*姓名*/
    private String realName;

    /*用户名*/
    private String userName;

    /*密码*/
    private String password;

    /*盐*/
    private String salt;

    /*手机*/
    private String phone;

    /*邮箱*/
    private String email;

    /*是否可用 0:true 1:false*/
    private Integer locked ;

    /*描述*/
    private String description;

    /*最后登录时间*/
    private Date lastLoginTime;

    /*最后登录ip*/
    private String lastLoginIp;

    /*登录次数*/
    private Integer loginCount;

    /*创建时间*/
    private Date createTime;

    /*最后修改信息时间*/
    private Date updateTime;

    /*创建人*/
    private Long creatorId;

}
