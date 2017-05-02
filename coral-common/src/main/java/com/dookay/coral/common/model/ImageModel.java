package com.dookay.coral.common.model;

import com.dookay.coral.common.config.ConfigManager;

/**
 * Created by YinQichao on 2017-03-10.
 */
public class ImageModel {

    private String alt;

    private String file;

    //todo 域名配置
    public String getFile() {
        return ConfigManager.getBackendDomain() + file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
