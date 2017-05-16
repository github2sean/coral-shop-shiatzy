package com.dookay.shiatzy.web.mobile.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import javax.persistence.Entity;

/**
 * Created by admin on 2017/4/25.
 */
@Data
@Entity
public class RegisterForm {

    @NotBlank(message = "用户名不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String validCode;


}
