package com.dookay.shiatzy.web.admin.response.admin;



import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.shiatzy.web.admin.response.SuccessResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by YinQichao on 2017-03-06.
 */
public class ListAdminResponse extends SuccessResponse {


    @ApiModelProperty(value = "分页列表")
    private PageList<AdminDomain> adminDomainPageList;

    @ApiModelProperty(value = "标签列表")
    private List<AdminResponse> adminResponseList;

    public PageList<AdminDomain> getAdminDomainPageList() {
        return adminDomainPageList;
    }

    public void setAdminDomainPageList(PageList<AdminDomain> adminDomainPageList) {
        this.adminDomainPageList = adminDomainPageList;
    }

    public List<AdminResponse> getAdminResponseList() {
        return adminResponseList;
    }

    public void setAdminResponseList(List<AdminResponse> adminResponseList) {
        this.adminResponseList = adminResponseList;
    }
}
