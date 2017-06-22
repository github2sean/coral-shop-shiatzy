package com.dookay.shiatzy.web.mobile.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by admin on 2017/4/26.
 */
@Data
public class UpdateEmailForm {

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "确认邮箱不能为空")
    private String confirmEmail;

    @NotBlank(message = "密码不能为空")
    private String password;
}
