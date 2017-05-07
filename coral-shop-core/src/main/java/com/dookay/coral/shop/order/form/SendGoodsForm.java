package com.dookay.coral.shop.order.form;

import lombok.Data;

/**
 * Created by admin on 2017/5/7.
 */
@Data
public class SendGoodsForm {

    private Long orderId;
    private String trackingNumber;
    private String shipperCompany;

}
