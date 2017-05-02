package com.dookay.shiatzy.web.admin.exception;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.utils.BeanValidators;
import com.dookay.coral.common.web.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Map;

/**
 * 自定义ExceptionHandler，专门处理Restful异常.
 * @since 2016年11月9日
 * @author Luxor
 * @version v0.0.1
 */
// 会被Spring-MVC自动扫描，但又不属于Controller的annotation
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 处理RestException.
     */
    @ExceptionHandler(value = { NotFoundException.class })
    public final ResponseEntity<?> handleException(NotFoundException ex, WebRequest request) {
        logger.error(ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaTypes.TEXT_PLAIN_UTF_8));
        return handleExceptionInternal(ex, ex.getMessage(), headers, ex.status, request);
    }

    /**
     * 处理JSR311 Validation异常.
     */
    @ExceptionHandler(value = { ConstraintViolationException.class })
    public final ResponseEntity<?> handleException(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = BeanValidators.extractPropertyAndMessage(ex.getConstraintViolations());
        String body = JsonUtils.toJSONString(errors);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaTypes.TEXT_PLAIN_UTF_8));
        return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
    }



}
