package com.dookay.coral.common.utils;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/6/22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 若str字符串已tag结束则剔除tag
     * @param str 待剔除的字符串
     * @param tag 要剔除的标签
     * @return 剔除后的字符串
     * @throws Exception
     */
    public static String trimEnd(String str,String tag) throws Exception{
        String result = str;
        if(str == null || str.equals("")){
            return str;
        }
        if(tag == null || tag.equals("")){
            throw new Exception("参数tag 不能为null或");
        }

        int tagPosition = str.lastIndexOf(tag);
        if(tagPosition+tag.length() == str.length()){
            result = str.trim().substring(0,tagPosition);
        }
        return result;
    }
}

