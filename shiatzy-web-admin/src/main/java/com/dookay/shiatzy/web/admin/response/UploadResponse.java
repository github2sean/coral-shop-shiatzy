package com.dookay.shiatzy.web.admin.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/24
 */
public class UploadResponse extends SuccessResponse {
    @ApiModelProperty("文件地址")
    private String fileUrl;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
