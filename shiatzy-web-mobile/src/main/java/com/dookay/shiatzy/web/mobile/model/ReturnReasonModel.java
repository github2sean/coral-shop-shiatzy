package com.dookay.shiatzy.web.mobile.model;

import lombok.Data;

/**
 * Created by admin on 2017/5/4.
 */
@Data
public class ReturnReasonModel {

    private Long skuId;
    private Long orderId;
    private Long orderItemId;

    private ReturnReasonTypeModel type1;
    private ReturnReasonTypeModel type2;
    private ReturnReasonTypeModel type3;
    private ReturnReasonTypeModel type4;


    public Boolean itemSelected(){
        return null !=orderItemId;
    }

    public  Boolean isChooseReason(){
        Boolean result = false;
        if(type1NotNull()){
            result = true;
        }
        if(type2NotNull()){
            result = true;
        }
        if(type3NotNull()){
            result = true;
        }
        if(type4NotNull()){
            result = true;
        }
        return result;
    }
    public  Boolean type1NotNull(){
        return this.getType1().isChooseReason();
    }
    public  Boolean type2NotNull(){
        return this.getType2().isChooseReason();
    }
    public  Boolean type3NotNull(){
        return this.getType3().isChooseReason();
    }
    public  Boolean type4NotNull(){
        return this.getType4().isChooseReason();
    }

    public String allReason(){
        String result = "{";
        if(type1NotNull()){
            result += this.getType1().allReason();
        }
        if(type2NotNull()){
            result += type1NotNull()?","+this.getType2().allReason():this.getType2().allReason();
        }
        if(type3NotNull()){
            result += type1NotNull()||type2NotNull()?","+this.getType3().allReason():this.getType3().allReason();
        }
        if(type4NotNull()){
            result += type1NotNull()||type2NotNull()||type3NotNull()?","+this.getType4().allReason():this.getType4().allReason();
        }
        result +="}";
        System.out.println("result："+result);
        return result;
    }


}
