package com.dookay.coral.common.config;

import com.dookay.coral.common.config.ConfigManager;

/**
 * 理财端用到的各种常量
 * Miscellaneous constants used for invest service.
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/8
 */
public final class INVConstants {

    public static final String ENCRYPTION_KEY = ConfigManager.getProperty("fixed.secret");			//加密key
    public static final String APP_ENCRYPTION_KEY = ConfigManager.getProperty("app.fixed.secret");			//APP加密key
}
