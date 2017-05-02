package com.dookay.shiatzy.web.mobile.form;

import lombok.Data;

/**
 * Created by admin on 2017/4/27.
 */
@Data
public class QueryGoodsForm {
    private Integer pageIndex = 1;//当前页码
    private Integer pageSize = 5; //页面大小，默认20
    private Integer offset = 0; // 行偏移
    private Integer limit;    //获取最大数量
    private String orderBy;// 排序字段


    private Long categoryId;//分类Id

    private Long prototypeId;//原型Id

    private String goodsName;//商品名

}
