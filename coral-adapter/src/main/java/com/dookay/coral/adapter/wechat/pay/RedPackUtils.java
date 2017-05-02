package com.dookay.coral.adapter.wechat.pay;

import com.beust.jcommander.internal.Maps;
import com.dookay.coral.adapter.wechat.model.RedPackModel;
import com.dookay.coral.adapter.wechat.model.RedQueryModel;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.RedPackApi;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by 磊 on 2017/3/22.
 */
public class RedPackUtils {

    static Log log = Log.getLog(RedPackUtils.class);

    /**
     * 发送红包
     *
     * @param request         当前请求
     * @param apiConfig       微信公众号配置
     * @param wechatPayConfig 微信支付配置
     * @param orderId         商户订单id
     * @param total_amount    红包总金额
     * @param total_num       红包数量
     * @param wishing         红包祝福语
     * @param act_name        活动名称
     * @param remark          备注
     * @param reOpenid        用户openid
     * @return 微信单号
     */
    public static RedPackModel send(HttpServletRequest request, ApiConfig apiConfig, WechatPayConfig wechatPayConfig,
                                    String orderId,
                                    String total_amount, String total_num, String wishing, String act_name, String remark, String reOpenid) throws WechatPayException {
        //商户相关资料
        String wxappid = apiConfig.getAppId();
        // 微信支付分配的商户号
        String partner = wechatPayConfig.getMerchantId();
        //API密钥
        String paternerKey = wechatPayConfig.getMerchantKey();

        String certPath = wechatPayConfig.getCertPath();

        String sendName = wechatPayConfig.getMerchantName();

        String ip = IpKit.getRealIp(request);

        Map<String, String> params = Maps.newHashMap();
        // 随机字符串
        String nonce_str = System.currentTimeMillis() / 1000 + "";
        params.put("nonce_str", nonce_str);
        // 商户订单号
        params.put("mch_billno", orderId);
        // 商户号
        params.put("mch_id", partner);
        // 公众账号ID
        params.put("wxappid", wxappid);
        // 商户名称
        params.put("send_name", "中粮食品");
        // 用户OPENID
        params.put("re_openid", reOpenid);
        // 付款现金(单位分)
        params.put("total_amount", total_amount);
        // 红包发放总人数
        params.put("total_num", total_num);
        // 红包祝福语
        params.put("wishing", wishing);
        // 终端IP
        params.put("client_ip", ip);
        // 活动名称
        params.put("act_name", act_name);
        // 备注
        params.put("remark", remark);
        //创建签名
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);

        String xmlResult = RedPackApi.sendRedPack(params, certPath, partner);
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
        RedPackModel redPackModel = RedPackModel.toModel(orderId, nonce_str, partner, wxappid, sendName, reOpenid, sign, total_amount, total_num, wishing, ip, act_name, remark, result);
        return redPackModel;
    }

    /**
     * 根据商户订单号查询红包
     *
     * @param mch_billno 商户订单号
     * @return
     */
    public static RedQueryModel query(String mch_billno, ApiConfig apiConfig, WechatPayConfig wechatPayConfig) {

        //商户相关资料
        String wxappid = apiConfig.getAppId();
        // 微信支付分配的商户号
        String partner = wechatPayConfig.getMerchantId();
        //API密钥
        String paternerKey = wechatPayConfig.getMerchantKey();

        String certPath = wechatPayConfig.getCertPath();

        Map<String, String> params = Maps.newHashMap();
        // 随机字符串
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        // 商户订单号
        params.put("mch_billno", mch_billno);
        // 商户号
        params.put("mch_id", partner);
        // 公众账号ID
        params.put("appid", wxappid);
        params.put("bill_type", "MCHT");
        //创建签名
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);

        String xmlResult = RedPackApi.getHbInfo(params, certPath, partner);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        System.out.println(result);
        return RedQueryModel.toModel(result);
    }
}
