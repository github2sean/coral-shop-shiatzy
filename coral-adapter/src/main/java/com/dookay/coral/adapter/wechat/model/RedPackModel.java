package com.dookay.coral.adapter.wechat.model;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by YinQichao on 2017-03-24.
 */

public class RedPackModel {

    private String mch_billno;

    private String total_amount;

    private String total_num;

    private String wishing;

    private String act_name;

    private String remark;

    private String re_openid;

    private String client_ip;

    private String nonce_str;

    private String mch_id;

    private String wxappid;

    private String send_name;

    private String sign;

    private String send_listid;

    private String return_code;

    private String return_msg;

    private String err_code_des;

    private String err_code;

    private String result_code;

    private Boolean success = Boolean.FALSE;

    public static RedPackModel toModel(String mch_billno, String nonce_str, String mch_id, String wxappid, String send_name, String re_openid,
                                       String sign, String total_amount, String total_num, String wishing, String client_ip, String act_name, String remark, Map<String, String> map) {
        RedPackModel model = new RedPackModel();
        model.setMch_billno(mch_billno);
        model.setTotal_amount(total_amount);
        model.setTotal_num(total_num);
        model.setWishing(wishing);
        model.setAct_name(act_name);
        model.setRemark(remark);
        model.setRe_openid(re_openid);
        model.setClient_ip(client_ip);
        model.setNonce_str(nonce_str);
        model.setMch_id(mch_id);
        model.setWxappid(wxappid);
        model.setSend_name(send_name);
        model.setSign(sign);

        String return_code = map.get("return_code");
        model.setReturn_msg(map.get("return_msg"));
        model.setReturn_code(return_code);
        if (StringUtils.equals(return_code, "SUCCESS")) {
            String result_code = map.get("result_code");
            model.setSign(map.get("sign"));
            model.setResult_code(result_code);
            model.setErr_code(map.get("err_code"));
            model.setErr_code_des(map.get("err_code_des"));
            if (StringUtils.equals(result_code, "SUCCESS")) {
                model.setSend_listid(map.get("send_listid"));
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

    public String getMch_billno() {
        return mch_billno;
    }

    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRe_openid() {
        return re_openid;
    }

    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getWxappid() {
        return wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSend_listid() {
        return send_listid;
    }

    public void setSend_listid(String send_listid) {
        this.send_listid = send_listid;
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

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }
}
