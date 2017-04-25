package com.dookay.coral.common.web.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Spring 上下文获取工具
 *
 * @author Luxor
 * @version v0.1.1
 * @since 2016/11/21
 */
public class WebApplicationContextUtil {

    /**
     * 通过类型名称获取对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
        return (T) ctx.getBean(name);
    }

    /**
     * 通过类型获取对象
     */
    public static <T> T getBean(Class<T> requiredType) {
        WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
        return (T) ctx.getBean(requiredType);
    }
}
