package com.dookay.shiatzy.web.mobile.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

/**
 * Created by admin on 2017/4/25.
 */
@Data
@Entity
public class LoginForm {
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[\\w\\+\\-]+(\\.[\\w\\+\\-]+)*@[a-z\\d\\-]+(\\.[a-z\\d\\-]+)*\\.([a-z]{2,4})$",message = "请填写有效的邮箱")
    private String userName;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[\\S]{6,16}$",message = "请填写6-16位字符，不能包含空格")
    private String password;
}
