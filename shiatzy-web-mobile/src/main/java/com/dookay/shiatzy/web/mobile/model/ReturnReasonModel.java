package com.dookay.shiatzy.web.mobile.model;

import lombok.Data;

/**
 * Created by admin on 2017/5/4.
 */
@Data
public class ReturnReasonModel {

    private Long skuId;
    private Long orderId;
    private Long orderItemId;

    private ReturnReasonTypeModel type1;
    private ReturnReasonTypeModel type2;
    private ReturnReasonTypeModel type3;
}
