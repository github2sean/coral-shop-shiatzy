package com.dookay.coral.adapter.wechat.model;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by YinQichao on 2017-03-24.
 */
public class MerchantTransferModel {
    private String mch_appid;

    private String mchid;

    private String nonce_str;

    private String partner_trade_no;

    private String openid;

    private String check_name;

    private String re_user_name;

    private String amount;

    private String desc;

    private String spbill_create_ip;

    private String sign;

    private String return_code;

    private String return_msg;

    private String result_code;

    private String err_code;

    private String err_code_des;

    private String payment_no;

    private String payment_time;

    private Boolean success = Boolean.FALSE;

    public static MerchantTransferModel toModel(String mch_appid, String mchid, String nonce_str, String partner_trade_no, String openid,
                                                String check_name, String re_user_name, String amount, String desc, String spbill_create_ip, String sign, Map<String, String> map) {
        MerchantTransferModel model = new MerchantTransferModel();
        model.setMch_appid(mch_appid);
        model.setMchid(mchid);
        model.setNonce_str(nonce_str);
        model.setPartner_trade_no(partner_trade_no);
        model.setOpenid(openid);
        model.setCheck_name(check_name);
        model.setRe_user_name(re_user_name);
        model.setAmount(amount);
        model.setDesc(desc);
        model.setSpbill_create_ip(spbill_create_ip);
        model.setSign(sign);

        String returnCode = map.get("return_code");
        model.setReturn_code(returnCode);
        model.setReturn_msg(map.get("return_msg"));
        if (StringUtils.equals(returnCode, "SUCCESS")) {
            String resultCode = map.get("result_code");
            model.setErr_code(map.get("err_code"));
            model.setErr_code_des(map.get("err_code_des"));
            model.setResult_code(resultCode);
            if (StringUtils.equals(resultCode, "SUCCESS")) {
                model.setPayment_no(map.get("payment_no"));
                model.setPayment_time(map.get("payment_time"));
                model.setSuccess(Boolean.TRUE);
            }
        }
        return model;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMch_appid() {
        return mch_appid;
    }

    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCheck_name() {
        return check_name;
    }

    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    public String getRe_user_name() {
        return re_user_name;
    }

    public void setRe_user_name(String re_user_name) {
        this.re_user_name = re_user_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getPayment_no() {
        return payment_no;
    }

    public void setPayment_no(String payment_no) {
        this.payment_no = payment_no;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }
}
