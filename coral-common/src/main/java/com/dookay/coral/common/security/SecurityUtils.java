package com.dookay.coral.common.security;

import com.dookay.coral.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/26
 */
public class SecurityUtils {

    /**
     * 验证密码是否合法
     * 6-16位数字或英文字母
     * @param input
     * @return
     */
    public static boolean validPassword(String input){
        if(StringUtils.isBlank(input)){
            return false;
        }
        String reg="(?!^\\d+$)(?!^[a-zA-Z]+$)(^.{6,16}$)";
        return input.matches(reg);
    }

}
