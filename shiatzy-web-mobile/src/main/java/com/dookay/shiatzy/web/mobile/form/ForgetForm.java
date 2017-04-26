package com.dookay.shiatzy.web.mobile.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by admin on 2017/4/26.
 */
@Data
public class ForgetForm {

    @NotBlank(message = "电子邮箱不能为空")
    private String userName;

    @NotBlank(message = "验证码不能为空")
    private String validCode;

}
