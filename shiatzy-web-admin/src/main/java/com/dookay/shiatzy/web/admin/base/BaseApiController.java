package com.dookay.shiatzy.web.admin.base;


import com.dookay.coral.common.exception.BaseException;
import com.dookay.coral.common.exception.ConstraintViolationsException;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.web.BaseController;
import com.dookay.shiatzy.web.admin.exception.InvalidArgumentException;
import com.dookay.shiatzy.web.admin.exception.NotFoundException;
import com.dookay.shiatzy.web.admin.exception.ValidException;
import com.dookay.shiatzy.web.admin.response.ErrorResponse;
import com.dookay.shiatzy.web.admin.response.SuccessResponse;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Created by 磊 on 2017/3/7.
 */
public class BaseApiController extends BaseController {
    /**
     * 返回成功结果
     * @param message
     * @return
     */
    public ResponseEntity<Object> successResponse(String message){
        HttpHeaders headers = new HttpHeaders();
        SuccessResponse response = new SuccessResponse();
        response.setMessage(message);
        return new ResponseEntity<Object>(response, headers, HttpStatus.OK);
    }

    /**
     * 自定义返回资源未找到异常
     * @param ex
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> exception(NotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse response = new ErrorResponse(ex.status.value(),ex.getMessage());
        return new ResponseEntity<Object>(response, headers, HttpStatus.NOT_FOUND);
    }

    /**
     * 自定义返回缺少必要的请求参数异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> exception(MissingServletRequestParameterException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String message = String.format("缺少必要的请求参数:%s",ex.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),message);
        return new ResponseEntity<Object>(response, headers, HttpStatus.BAD_REQUEST);
    }

    /**
     * 自定义请求参数格式不正确异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> exception(MethodArgumentTypeMismatchException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String message = String.format("请求参数格式不正确:%s",ex.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),message);
        return new ResponseEntity<Object>(response, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> exception(BaseException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
        return new ResponseEntity<Object>(response, headers, HttpStatus.OK);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> exception(ServiceException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse response = new ErrorResponse(400,ex.getMessage());
        return new ResponseEntity<Object>(response, headers, HttpStatus.OK);
    }

    /**
     * 业务验证错误，返回200，需要app提示用户错误信息
     * @param ex
     * @return
     */
    @ExceptionHandler(ValidException.class)
    public ResponseEntity<Object> exception(ValidException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String message = String.format("验证错误:%s",ex.getMessage());
        ErrorResponse response = new ErrorResponse(ex.getCode(),message);
        return new ResponseEntity<Object>(response, headers, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<Object> exception(InvalidArgumentException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse response = new ErrorResponse(ex.getCode(),ex.getMessage());
        return new ResponseEntity<Object>(response, headers, HttpStatus.OK);
    }

     @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<Object> exception(UnauthenticatedException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse response = new ErrorResponse(403,"用户没有登录");
        return new ResponseEntity<Object>(response, headers, HttpStatus.OK);
    }


    @ExceptionHandler(ConstraintViolationsException.class)
    public ResponseEntity<Object> exception(ConstraintViolationsException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse response = new ErrorResponse(500,"验证异常");
        return new ResponseEntity<Object>(response, headers, HttpStatus.OK);
    }



}
