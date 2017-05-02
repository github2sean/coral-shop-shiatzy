package com.dookay.shiatzy.web.admin.response.admin;


import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.host.security.domain.RoleDomain;
import com.dookay.shiatzy.web.admin.response.SuccessResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/3/28
 */
public class ListRoleResponse extends SuccessResponse {
    @ApiModelProperty(value = "分页列表")
    private PageList<RoleDomain> roleDomainPageList;

    public PageList<RoleDomain> getRoleDomainPageList() {
        return roleDomainPageList;
    }

    public void setRoleDomainPageList(PageList<RoleDomain> roleDomainPageList) {
        this.roleDomainPageList = roleDomainPageList;
    }
}
