package com.dookay.coral.adapter.payment.unionpay.acp.config;

import lombok.Data;

/**
 * Created by admin on 2017/5/17.
 */
@Data
public class UnionConfig {

    /***商户接入参数***/
    private String merId; //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
    private String accessType;//接入类型，0：直连商户
    private String currencyCode;//交易币种（境内商户一般是156 人民币）
    //private String reqReserved;//请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节

    private String version; //版本号，全渠道默认值
    private String encoding;//字符集编码，可以使用UTF-8,GBK两种方式
    private String signMethod;//签名方法，只支持 01：RSA方式证书加密
    private String txnType;   //交易类型 ，01：消费
    private String txnSubType;//交易子类型， 01：自助消费
    private String bizType; //业务类型，B2C网关支付，手机wap支付
    private String channelType; //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
    private String frontUrl;
    private String backUrl;


}
