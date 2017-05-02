package com.dookay.coral.common.utils;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/26
 */
public class NumUtils {
    public static Integer fixZero(Integer num){
        if(num == null)
        {
            num =0;
        }
        return num;
    }

    public static Double fixZero(Double num){
        if(num == null)
        {
            num =0D;
        }
        return num;
    }
}
