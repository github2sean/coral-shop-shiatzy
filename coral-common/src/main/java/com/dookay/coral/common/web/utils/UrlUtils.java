package com.dookay.coral.common.web.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * url工具集
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/11/26
 */
public class UrlUtils {
    /**
     * 移除url参数
     *
     * @param path        url 路径部分
     * @param queryString url 参数部分
     * @param paramName   参数名称
     * @return 移除后的完全url
     */
    public static String removeParam(String path, String queryString, String paramName) {
        String url = path + "?" + (StringUtils.isEmpty(queryString) ? "" : queryString);
        String regStr = ".*(?i)[?&]" + paramName + "=?[^&]*.*";
        String regStr1 = "(?i)([?&])" + paramName + "=?[^&]*";
        if (url.matches(regStr)) {
            url = url.replaceAll(regStr1, "$1");
        }

        url = url.replaceAll("[?&]$", "").replaceAll("\\?&", "?");
        return url;
    }

    /**
     * 设置url参数
     *
     * @param path        url 路径部分
     * @param queryString url 参数部分
     * @param paramName   参数名称
     * @param value       要设置的值
     * @return 设置后的完全url
     */
    public static String setParam(String path, String queryString, String paramName, String value) {
        String url = path + "?" + (StringUtils.isEmpty(queryString) ? "" : queryString);
        String regStr = ".*(?i)[?&]" + paramName + "=?[^&]*.*";
        String regStr1 = "(?i)([?&])" + paramName + "=?[^&]*";

        if (url.matches(regStr))
            url = url.replaceAll(regStr1, "$1" + paramName + "=" + value);
        else if (StringUtils.isEmpty(queryString)) {
            url += String.format("%s=%s", paramName, value);
        } else {
            url += String.format("&%s=%s", paramName, value);
        }
        return url;
    }

    /**
     * 移除url参数
     *
     * @param request   请求
     * @param paramName 参数名称
     * @return 移除后的完全url
     */
    public static String removeParam(HttpServletRequest request, String paramName) {
        return removeParam((String) request.getAttribute("javax.servlet.forward.request_uri"),
                (String) request.getAttribute("javax.servlet.forward.query_string"), paramName);
    }

    /**
     * 设置url参数
     *
     * @param request   请求
     * @param paramName 参数名称
     * @param value     要设置的值
     * @return 设置后的完全url
     */
    public static String setParam(HttpServletRequest request, String paramName, String value) {
        return setParam((String) request.getAttribute("javax.servlet.forward.request_uri"),
                (String) request.getAttribute("javax.servlet.forward.query_string"), paramName, value);
    }


    /**
     * 获取domain完全体（http://www.xxx.com）
     *
     * @param request 请求
     * @return 协议域名。
     */
    public static String getFullDomain(HttpServletRequest request) {
        String strBackUrl = request.getScheme() + "://" + request.getServerName() //服务器地址
                + (request.getServerPort() != 80 ? (":" + request.getServerPort()) : "")
                + request.getContextPath();      //项目名称
        return strBackUrl;
    }
}
