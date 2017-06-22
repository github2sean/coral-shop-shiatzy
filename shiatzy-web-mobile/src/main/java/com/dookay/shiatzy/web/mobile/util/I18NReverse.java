package com.dookay.shiatzy.web.mobile.util;

/**
 * Created by admin on 2017/6/22.
 */
public class I18NReverse {

    private int languageType ;


    //accountController
    private  String updateFailed;
    private  String updateSuccess;
    private  String emailIsExsit;
    private  String passwordAndUserNameErro;
    private  String oldPasswordErro;
    private  String vlaidSuccess;
    private  String operateSuccess;

    public I18NReverse(int languageType){
        this.languageType = languageType;
    }

    public String getUpdateFailed() {
        return languageType==0?"修改失败":"Failed to Modify";
    }

    public String getUpdateSuccess() {
        return languageType==0?"修改成功":"Modify Successfully";
    }

    public String getEmailIsExsit() {
        return languageType==0?"邮箱已存在":"The Email Already Exists";
    }


    public String getPasswordAndUserNameErro() {
        return languageType==0?"帐号与密码不匹配":"The Account Mismatching The Password";
    }

    public String getOldPasswordErro() {
        return languageType==0?"密码不正确":"The Password is Incorrect";
    }


    public String getVlaidSuccess() {
        return languageType==0?"验证成功":"Verify Success";
    }

    public String getOperateSuccess() {
        return languageType==0?"操作成功":"Operate Success";
    }

}
