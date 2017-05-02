package com.dookay.coral.common.config;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 配置管理器
 *
 * @author Luxor
 * @version v0.1.1
 * @since 2016/11/21
 */
public class ConfigManager {
    private static ConfigManager config = new ConfigManager();

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader loader = new PropertiesLoader("appConfig.properties");

    /**
     * 获取当前对象实例
     */
    public static ConfigManager getInstance() {
        return config;
    }

    public static String getProperty(String key) {
        String value = map.get(key);
        if (value == null) {
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

    public static String getBackendDomain() {
        return getProperty("BackendDomain");
    }

    public static String getFrontendDomain() {
        return getProperty("FrontendDomain");
    }
}
