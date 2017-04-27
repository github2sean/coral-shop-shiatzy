package com.dookay.shiatzy.web.mobile.form;

import lombok.Data;

/**
 * Created by admin on 2017/4/27.
 */
@Data
public class AddShoppingCartForm {

    private int type ;//1:购物车 2：心愿单

    private Long skuId;

    private int num;

    private String goodsName;

    private String goodsCode;

    private Double goodsPrice;



}
