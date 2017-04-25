package com.dookay.coral.common.utils;


import com.alibaba.fastjson.JSON;
import com.dookay.coral.common.model.FileModel;
import com.dookay.coral.common.model.ImageModel;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YinQichao on 2017-03-10.
 */
public class ImageUtil {

    public static ImageModel toModel(String json) {
        if (!StringUtils.isNotBlank(json)) {
            return new ImageModel();
        }
        try {
            List<ImageModel> modelList = JSON.parseArray(json, ImageModel.class);
            if (modelList == null || modelList.size() == 0) {
                return new ImageModel();
            }
            return modelList.get(0);
        } catch (Exception e) {
            return new ImageModel();
        }
    }

    public static FileModel toFileModel(String json){
        if (!StringUtils.isNotBlank(json)) {
            return new FileModel();
        }
        try {
            List<FileModel> modelList = JSON.parseArray(json, FileModel.class);
            if (modelList == null || modelList.size() == 0) {
                return new FileModel();
            }
            return modelList.get(0);
        } catch (Exception e) {
            return new FileModel();
        }
    }

    public static List<ImageModel> toModelList(String json) {
        if (!StringUtils.isNotBlank(json)) {
            return new ArrayList<>();
        }
        try {
            List<ImageModel> list = JSON.parseArray(json, ImageModel.class);
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
