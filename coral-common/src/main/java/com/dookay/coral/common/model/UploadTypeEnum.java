package com.dookay.coral.common.model;

/**
 * 文件上传文件类型
 */
public enum UploadTypeEnum {
    Document("文档", 1), Image("图片", 2), Audio("音频", 3), Media("媒体", 4);

    private String description;
    private int value;

    UploadTypeEnum(String descrition, Integer value) {
        this.description = descrition;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }
}
