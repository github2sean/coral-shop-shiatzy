package com.dookay.coral.adapter.wechat.pay;

import com.beust.jcommander.internal.Maps;
import com.dookay.coral.adapter.wechat.model.MerchantTransferModel;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.RedPackApi;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.utils.HttpUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by 磊 on 2017/3/22.
 */
public class MerchantTransferUtils {
    private static String merchantTransferUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    static Log log = Log.getLog(MerchantTransferUtils.class);

    /**
     * 企业付款
     *
     * @param apiConfig       微信公众号配置
     * @param wechatPayConfig 微信支付配置
     * @param orderId         商户订单id
     * @param total_amount    转账总金额
     * @param remark          描述信息
     * @param reOpenid        用户openid
     * @param userName        用户真实姓名，可为空
     * @return 微信订单号
     */
    public static MerchantTransferModel transfer(ApiConfig apiConfig, WechatPayConfig wechatPayConfig, String orderId,
                                                 String total_amount, String remark, String reOpenid, String userName) throws WechatPayException {
        //商户相关资料
        String wxappid = apiConfig.getAppId();
        // 微信支付分配的商户号
        String partner = wechatPayConfig.getMerchantId();
        //API密钥
        String paternerKey = wechatPayConfig.getMerchantKey();

        String certPath = wechatPayConfig.getCertPath();

        String ip = wechatPayConfig.getServerIp();

        Map<String, String> params = Maps.newHashMap();

        // 公众账号ID
        params.put("mch_appid", wxappid);
        // 商户号
        params.put("mchid", partner);
        // 随机字符串
        String nonce_str = System.currentTimeMillis() / 1000 + "";
        params.put("nonce_str", nonce_str);
        // 商户订单号
        params.put("partner_trade_no", orderId);
        // 用户OPENID
        params.put("openid", reOpenid);
        String check_name = "";
        if (StringUtils.isEmpty(userName)) {
            // 校验用户姓名选项
            check_name = "NO_CHECK";
            params.put("check_name", check_name);
        } else {
            // 校验用户姓名选项
            check_name = "OPTION_CHECK";
            params.put("check_name", check_name);
            // 收款用户姓名
            params.put("re_user_name", userName);
        }

        // 金额,单位为分
        params.put("amount", total_amount);
        //企业付款描述信息
        params.put("desc", remark);
        // 终端IP
        params.put("spbill_create_ip", ip);

        //创建签名
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);

        System.out.println(params);

        String xmlResult = HttpUtils.postSSL(merchantTransferUrl, PaymentKit.toXml(params), certPath, partner);

        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        log.warn(JsonKit.toJson(result));
        //此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        String return_code = result.get("return_code");
        //业务结果
        String result_code = result.get("result_code");

        if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
            throw new WechatPayException(result.get("return_msg"));
        }

        if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
            throw new WechatPayException(result.get("err_code_des"));
        }
        MerchantTransferModel model = MerchantTransferModel.toModel(wxappid, partner, nonce_str, orderId, reOpenid, check_name, userName, total_amount, remark, ip, sign, result);
        return model;
    }
}
