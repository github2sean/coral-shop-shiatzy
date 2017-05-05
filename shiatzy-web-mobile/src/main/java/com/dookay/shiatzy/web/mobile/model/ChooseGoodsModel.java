package com.dookay.shiatzy.web.mobile.model;

import lombok.Data;

import java.util.List;

/**
 * Created by admin on 2017/5/4.
 */
@Data
public class ChooseGoodsModel {

    private Long orderItemId;

    private Long skuId;

    private String goodsCode;

    private String reason;


}
