package com.dookay.shiatzy.web.mobile.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/6/2
 */
@Data
public class UpdatePasswordForm {

    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;

    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    @AssertTrue(message="密码两次输入不一致",groups={UpdatePasswordForm.class})
    private boolean isConfirmPassword() {
        return this.newPassword.equals(this.confirmPassword);
    }
}
