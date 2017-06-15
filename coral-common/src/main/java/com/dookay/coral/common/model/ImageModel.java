package com.dookay.coral.common.model;

import com.dookay.coral.common.config.ConfigManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YinQichao on 2017-03-10.
 */
public class ImageModel {

    private String alt;

    private String file;

    //todo 域名配置
    public String getFile() {
        return this.file;
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

    public static ImageModel toFirst(String json){
        if(StringUtils.isBlank(json)){
            return new ImageModel();
        }
        ImageModel imageModel = new ImageModel();
        JSONArray jsonArray =  JSONArray.fromObject(json);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        imageModel.setFile(jsonObject.getString("file"));
        imageModel.setAlt(jsonObject.getString("alt"));
        return imageModel;
    }

    public static List<ImageModel> toList(String json){
        List<ImageModel> imageModelList = new ArrayList<>();
        JSONArray jsonArray =  JSONArray.fromObject(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            ImageModel imageModel = new ImageModel();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            imageModel.setFile(jsonObject.getString("file"));
            imageModel.setAlt(jsonObject.getString("alt"));
            imageModelList.add(imageModel);
        }

        return imageModelList;
    }
}
