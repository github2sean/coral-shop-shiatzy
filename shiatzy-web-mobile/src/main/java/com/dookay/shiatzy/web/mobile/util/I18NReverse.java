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
    private  String encrypDataErro;
    private  String timeOut;
    private  String noBindVip;


    //boutiqueController
    private  String noMatchGoods;
    private  String unPre;
    private  String sellOut;
    private  String addSuccess;
    private  String delSuccess;
    private  String existInPre;

    //checkoutController
    private  String searchSuccess;
    private  String paramError;
    private  String orderTimeOut;
    private  String stockOut;
    private  String inconsistentCondition;

    public I18NReverse(int languageType){
        this.languageType = languageType;
    }

    public String getStockOut() {
        return languageType==0?"优惠条件不符":"Don't match Condition";
    }
    public String getInconsistentCondition() {
        return languageType==0?"库存不足":"The goods selled out";
    }
    public String getOrderTimeOut() {
        return languageType==0?"订单已经失效":"The Order is Time out";
    }
    public String getParamErro() {
        return languageType==0?"参数出错":"parameter Error";
    }
    public String getSearchSuccess() {
        return languageType==0?"查询成功":"Search Successful";
    }

    public String getExistInPre() {
        return languageType==0?"精品店已存在此商品":"The goods exist in reservation";
    }
    public String getAddSuccess() {
        return languageType==0?"添加成功":"Add Successful";
    }
    public String getAdderror() {
        return languageType==0?"精品店预约数量不能超过5件":"Boutique reservation number can not be more than 5 ";
    }
    public String getDelSuccess() {
        return languageType==0?"删除成功":"Delete Successful";
    }

    public String getNoBindVip() {
        return languageType==0?"暂未绑定会员":"Don't bind VIP";
    }

    public String getSellOut() {
        return languageType==0?"该商品已售罄":"The goods was selled out";
    }

    public String getUnPre() {
        return languageType==0?"该商品不能预约":"The goods can't make a reservation";
    }

    public String getNoMatchGoods() {
        return languageType==0?"没有对应颜色和尺寸的商品":"Don't Match Goods";
    }

    public String getUpdateFailed() {
        return languageType==0?"修改失败":"Failed to Modify";
    }

    public String getTimeOut() {
        return languageType==0?"修改时间已过期":"Time Out";
    }

    public String getEncrypDataErro() {
        return languageType==0?"加密数据不正确":" Data Error";
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

    public String getInvalidCode() {
        return languageType==0?"优惠券代码无效":"Invalid code";
    }
}
