package com.dookay.shiatzy.web.admin.model;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/3/25
 */
public class LoginModel {
    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空")
    private String userName;
    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

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
}
