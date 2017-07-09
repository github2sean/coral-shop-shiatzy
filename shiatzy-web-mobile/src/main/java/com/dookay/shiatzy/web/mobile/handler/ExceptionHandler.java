package com.dookay.shiatzy.web.mobile.handler;

import com.dookay.coral.common.exception.BaseException;
import com.dookay.coral.common.exception.ConstraintViolationsException;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.CookieUtil;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.common.web.ResultCodes;
import com.dookay.coral.common.web.render.JsonRender;
import com.dookay.coral.common.web.render.RenderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 异常处理 托管给spring
 *
 * @author : kezhan
 * @version : v0.0.1
 * @since : 2016年11月8日
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    private static final Logger logger = Logger.getLogger(ExceptionHandler.class);

    private String msg = "系统繁忙,请稍后再试";

    /* 异常信息地址 */
    private String errorUrl = "errorUrl";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("自定义异常：" + ex.getMessage(), ex);
        String errorMsg = "";
        errorMsg = hibernateValidator(ex, errorMsg);
        errorMsg = BaseExceptionValidator(ex, errorMsg);
        errorMsg = ServiceExceptionValidator(ex, errorMsg);
        if (StringUtils.isEmpty(errorMsg)) {
            errorMsg = msg;
        }
        String  lang =  CookieUtil.getCookieValueByKey(HttpContext.current().getRequest(),"Language");

        if("en_US".equals(lang)){
            errorMsg = errorMsg
                    .replace("优惠券代码无效","Invalid code")
                    .replace("优惠券代码已过期","Code expired")
                    .replace("优惠券使用次数不足","Code use limited")
                    .replace("心愿单中已经存在","Already added");
        }
        if (JsonUtils.isAjax(handler)) {
            try {
                JsonResult result = new JsonResult(ResultCodes.ERROR, errorMsg);
                JsonRender render = new JsonRender(result);
                render.render();
            } catch (RenderException e) {
                logger.trace(e.getStackTrace());
            }
            return new ModelAndView();
        }
        return new ModelAndView("redirect:" + errorUrl); // 关于异常拦截页面异常跳转的地址后期配置为动态
    }

    /**
     * hibernateValidator数据验证异常
     *
     * @param ex
     * @param errorMsg
     * @return
     * @author : kezhan
     * @since : 2016年12月7日
     */
    @SuppressWarnings("rawtypes")
    public String hibernateValidator(Exception ex, String errorMsg) {
        if (StringUtils.isNotEmpty(errorMsg)) {
            return errorMsg;
        }

        if (ex instanceof ConstraintViolationsException) {
            Set constraintViolations = (Set) ((ConstraintViolationsException) ex).getObject();
            Object[] objects = constraintViolations.toArray();
            ConstraintViolationImpl constraintViolationImpl = null;
            if (objects != null) {
                constraintViolationImpl = (ConstraintViolationImpl) objects[0];
            }
            if (constraintViolationImpl != null) {
                return constraintViolationImpl.getMessageTemplate();
            }
        }
        return errorMsg;
    }

    /**
     * 基类异常
     *
     * @param ex
     * @return
     * @author : kezhan
     * @since : 2016年12月7日
     */
    public String BaseExceptionValidator(Exception ex, String errorMsg) {
        if (StringUtils.isNotEmpty(errorMsg)) {
            return errorMsg;
        }

        if (ex instanceof BaseException) {
            return ex.getMessage();
        }
        return errorMsg;
    }

    public String ServiceExceptionValidator(Exception ex, String errorMsg) {
        if (StringUtils.isNotEmpty(errorMsg)) {
            return errorMsg;
        }

        if (ex instanceof ServiceException) {
            return ex.getMessage();
        }
        return errorMsg;
    }

    public String IllegalArgumentExceptionValidator(Exception ex, String errorMsg){
        if (StringUtils.isNotEmpty(errorMsg)) {
            return errorMsg;
        }

        if (ex instanceof IllegalArgumentException) {
            return String.format("[Assertion failed] - this argument %s is required; it must not be null",ex.getMessage());
        }
        return errorMsg;
    }
}
