package com.dookay.shiatzy.web.admin.response;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/30
 */
public class SuccessResponse {

    @ApiModelProperty("成功代码")
    private Integer code = HttpStatus.OK.value();
    @ApiModelProperty("返回消息")
    private String message ="操作成功";

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
