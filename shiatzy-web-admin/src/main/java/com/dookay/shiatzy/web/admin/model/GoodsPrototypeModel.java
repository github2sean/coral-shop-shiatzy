package com.dookay.shiatzy.web.admin.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/3
 */
public class GoodsPrototypeModel {
    @ApiModelProperty("id")
    private Long id;

    /*原型名称*/
    @ApiModelProperty("原型名称")
    private String name;

    /*原型编码*/
    @ApiModelProperty("原型编码")
    private String code;

    /*描述*/
    @ApiModelProperty("描述")
    private String description;

    /*创建时间*/
    @ApiModelProperty("创建时间")
    private Date createTime;

    /*创建人id*/
    @ApiModelProperty("创建人id")
    private Long creatorId;
}
