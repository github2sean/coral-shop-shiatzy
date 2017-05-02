package com.dookay.coral.common.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页帮助工具
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/11/21
 */
public class PagerUtil {
    public static String generateUrl(HttpServletRequest request, int page) {
        return UrlUtils.setParam(request, "pageIndex", page + "");
    }

    public static String generateUrl(String path, String queryString, int page) {
        return UrlUtils.setParam(path, queryString, "pageIndex", page + "");
    }
}
