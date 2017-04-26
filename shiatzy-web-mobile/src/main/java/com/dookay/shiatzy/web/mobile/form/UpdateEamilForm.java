package com.dookay.shiatzy.web.mobile.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by admin on 2017/4/26.
 */
@Data
public class UpdateEamilForm {

    @NotBlank(message = "登录密码不能为空")
    private String password;

    @NotBlank(message = "新的登录密码不能为空")
    private String newPassword;

    @NotBlank(message = "邮箱不能为空")
    private String newEmail;


}
