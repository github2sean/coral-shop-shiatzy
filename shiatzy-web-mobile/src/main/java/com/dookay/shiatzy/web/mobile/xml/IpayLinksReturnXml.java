package com.dookay.shiatzy.web.mobile.xml;

import lombok.Data;

/**
 * Created by admin on 2017/5/18.
 */
@Data
public class IpayLinksReturnXml {

    private String   orderId;
    private String   resultCode;
    private String   resultMsg;
    private String   orderAmount;
    private String   payAmount;
    private String   currencyCode;
    private String   merchantBillName;
    private String   settlementCurrencyCode;
    private String   acquiringTime;
    private String   completeTime;
    private String   dealId;
    private String   partnerId;
    private String   remark;
    private String   charset;
    private String   signType;
    private String   signMsg;

}
