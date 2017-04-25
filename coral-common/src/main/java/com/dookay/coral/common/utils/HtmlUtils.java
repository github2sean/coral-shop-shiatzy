package com.dookay.coral.common.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 页面工具类
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/11/26
 */
public class HtmlUtils {

    /**
     * 隐藏银行卡信息
     *
     * @param account
     * @return
     */
    public static String hideBankAccount(String account) {
        int length = account.length();
        int starLength = length / 2;
        StringBuilder sb = new StringBuilder();
        int halfLength = length / 4;
        sb.append(account.substring(0, halfLength));
        for (int i = 0; i < starLength; i++) {
            sb.append("*");
        }

        sb.append(account.substring(account.length() - halfLength, account.length()));

        return sb.toString();
    }

    /**
     * 隐藏用户真实姓名，如：张**
     *
     * @param name
     * @return
     */
    public static String hideRealName(String name) {
        if (StringUtils.isEmpty(name))
            return "";
        int length = name.length();
        StringBuilder sb = new StringBuilder();
        sb.append(name.charAt(0));
        for (int i = 0; i < length - 1; i++) {
            sb.append("*");
        }
        return sb.toString();
    }

    /**
     * 隐藏手机号信息，如：138****1234
     *
     * @param mobile
     * @return
     */
    public static String hideMobile(String mobile) {
        if (mobile == null || mobile.length() != 11) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(mobile.substring(0, 3));
        sb.append("****");
        sb.append(mobile.substring(7));
        return sb.toString();
    }

    /**
     * 隐藏身份证信息，如：
     *
     * @param idNo
     * @return
     */
    public static String hideIdNo(String idNo) {
        if (idNo == null || idNo.length() != 18) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(idNo.substring(0, 3));
        sb.append("***********");
        sb.append(idNo.substring(14));
        return sb.toString();
    }


    /**
     * 过滤html标签
     *
     * @param html html内容
     * @return 过滤后的内容
     */
    public static String removeTag(String html) {
        String regExScript = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regExStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regExHtml = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
        Matcher mScript = pScript.matcher(html);
        html = mScript.replaceAll(""); //过滤script标签

        Pattern pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
        Matcher mStyle = pStyle.matcher(html);
        html = mStyle.replaceAll(""); //过滤style标签

        Pattern pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
        Matcher mHtml = pHtml.matcher(html);
        html = mHtml.replaceAll(""); //过滤html标签

        return html.trim(); //返回文本字符串
    }
}
