package com.dookay.coral.common.web.jcaptcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dookay.coral.common.web.validate.ValidateResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * jcaptcha 验证码验证
 * @since : 2016年11月17日
 * @author : kezhan
 * @version : v0.0.1
 */
@Controller
@RequestMapping("/jcaptcha-validate")
public class AjaxJCaptchaValidateController {

    @Autowired
    private MessageSource messageSource;

    /**
     * 返回验证码
     * @param request
     * @param fieldId
     * @param fieldValue
     * @return
     * @since : 2016年11月17日
     * @author : kezhan
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object jqueryValidationEngineValidate(
            HttpServletRequest request,
            @RequestParam(value = "fieldId", required = false) String fieldId,
            @RequestParam(value = "fieldValue", required = false) String fieldValue) {

        ValidateResponse response = ValidateResponse.newInstance();

        if (JCaptcha.hasCaptcha(request, fieldValue) == false) {
            response.validateFail(fieldId, messageSource.getMessage("jcaptcha.validate.error", null, null));
        } else {
            response.validateSuccess(fieldId, messageSource.getMessage("jcaptcha.validate.success", null, null));
        }

        return response.result();
    }
}
