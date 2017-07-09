package com.dookay.shiatzy.web.mobile.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;

/**
 * Created by admin on 2017/5/4.
 */
@Data
public class ReturnReasonTypeModel {
    private String name;
    private String reason1;
    private String reason2;
    private String reason3;
    private String reason4;

    public  Boolean isChooseReason(){
        Boolean result = false;
        if(StringUtils.isNoneBlank(this.getReason1())){
            result = true;
        }
        if(StringUtils.isNoneBlank(this.getReason2())){
            result = true;
        }
        if(StringUtils.isNoneBlank(this.getReason3())){
            result = true;
        }
        if(StringUtils.isNoneBlank(this.getReason4())){
            result = true;
        }
        return result;
    }

    public String allReason(){
        String allReason = "";
        if(isChooseReason()){
            allReason += "\""+this.name+"\":{";

            if(StringUtils.isNotBlank(this.getReason1())){
                allReason += "\"reason1\":"+"\""+this.reason1+"\"";
            }
            if(StringUtils.isNotBlank(this.getReason2())){
                allReason += allReason.contains("reason1")?",\"reason2\":"+"\""+this.reason2+"\"":"\"reason2\":"+"\""+this.reason2+"\"";
            }
            if(StringUtils.isNotBlank(this.getReason3())){
                allReason += allReason.contains("reason1")||allReason.contains("reason2")?",\"reason3\":"+"\""+this.reason3+"\"":"\"reason3\":\""+this.reason3+"\"";
            }
            if(StringUtils.isNotBlank(this.getReason4())){
                allReason += allReason.contains("reason1")||allReason.contains("reason2")||allReason.contains("reason3")?",\"reason4\":"+"\""+this.reason4+"\"":"\"reason4\":\""+this.reason4+"\"";
            }

            allReason += "}";
        }else{
            return "";
        }
        return allReason;
    }
}
