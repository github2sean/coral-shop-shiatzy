package com.dookay.coral.adapter.payment.ipaylinks.config;

import lombok.Data;

/**
 * Created by admin on 2017/5/18.
 */
@Data
public class IpayLinksConfig {

    private String version; //版本 标识接口版本信息，当前版本1.0
    private String siteId; //商户网站域名
    private String tradeType ;  //交易类型 1000：即时支付
    private String currencyCode; //交易币种
    private String settlementCurrencyCode;//结算币种
    private String borrowingMarked; //资金来源借贷标识 0：无特殊要求
    private String noticeUrl;//商户通知地址
    private String partnerId;//支付系统提供给商户的唯一ID号
    private String payMode;// 支付方式 10 国际信用卡
    private String charset;//编码方式 UTF-8
    private String signType;//签名类型 MD5


}
