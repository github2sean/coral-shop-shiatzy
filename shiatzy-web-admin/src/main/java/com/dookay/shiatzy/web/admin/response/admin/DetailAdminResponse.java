package com.dookay.shiatzy.web.admin.response.admin;

import com.dookay.shiatzy.web.admin.response.SuccessResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by YinQichao on 2017-03-06.
 */
public class DetailAdminResponse extends SuccessResponse {


    @ApiModelProperty(value = "管理员详情")
    private AdminResponse adminResponse;


    public AdminResponse getAdminResponse() {
        return adminResponse;
    }

    public void setAdminResponse(AdminResponse adminResponse) {
        this.adminResponse = adminResponse;
    }
}
