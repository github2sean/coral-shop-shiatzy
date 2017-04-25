package com.dookay.coral.common.utils;

import com.dookay.coral.common.web.utils.UrlUtils;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * UrlUtils测试
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/11/27
 */
public class UrlUtilsTest {
    @Test
    public void removeParam() throws Exception {
        String path = "/home";
        String queryString = "page=2&test=3";
        System.out.println(UrlUtils.removeParam(path, queryString, "page"));
        System.out.println(UrlUtils.removeParam(path, queryString, "test"));
        Assert.isTrue(UrlUtils.removeParam(path, queryString, "page").equals("/home?test=3"));
        Assert.isTrue(UrlUtils.removeParam(path, queryString, "test").equals("/home?page=2"));
    }

    @Test
    public void replaceParamValue() throws Exception {
        String path = "/home";
        String queryString = "page=2&test=3";

        Assert.isTrue(UrlUtils.setParam(path, queryString, "page", "4").equals("/home?page=4&test=3"));
        Assert.isTrue(UrlUtils.setParam(path, queryString, "test", "5").equals("/home?page=2&test=5"));
        queryString = "";
        Assert.isTrue(UrlUtils.setParam(path, queryString, "page", "4").equals("/home?page=4"));
        Assert.isTrue(UrlUtils.setParam(path, queryString, "test", "5").equals("/home?test=5"));
    }

    @Test
    public void removeParam1() throws Exception {

    }

    @Test
    public void replaceParamValue1() throws Exception {

    }

}
