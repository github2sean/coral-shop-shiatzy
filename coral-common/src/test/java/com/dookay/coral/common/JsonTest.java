package com.dookay.coral.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONArray;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/8
 */
public class JsonTest {
    @Test
    public void test1(){
        String json = "[12,11]";
        List<Long> colorIds = new ArrayList<>();
        JSONArray jsonArray = JSONArray.fromObject(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            colorIds.add(jsonArray.getLong(i));
        }
    }

    @Test
    public void  test2(){
        String str = "{\"服务\":{\"reason1\":\"错误商品\",\"reason2\":\"货运过长\"},\"其他\":{\"reason1\":\"瑕紙品\"}}";
        JSONObject jsonObject = JSON.parseObject(str);
        List<String> reasonList = new ArrayList<>();
        if(jsonObject.containsKey("服务")){
           JSONObject jsonObject1 = jsonObject.getJSONObject("服务");
           if(jsonObject1.containsKey("reason1")){
               reasonList.add(jsonObject1.getString("reason1"));
           }
            if(jsonObject1.containsKey("reason2")){
                reasonList.add(jsonObject1.getString("reason2"));
            }
            if(jsonObject1.containsKey("reason3")){
                reasonList.add(jsonObject1.getString("reason3"));
            }
            if(jsonObject1.containsKey("reason4")){
                reasonList.add(jsonObject1.getString("reason4"));
            }
        }
        if(jsonObject.containsKey("品质")){
            JSONObject jsonObject1 = jsonObject.getJSONObject("品质");
            if(jsonObject1.containsKey("reason1")){
                reasonList.add(jsonObject1.getString("reason1"));
            }
            if(jsonObject1.containsKey("reason2")){
                reasonList.add(jsonObject1.getString("reason2"));
            }
            if(jsonObject1.containsKey("reason3")){
                reasonList.add(jsonObject1.getString("reason3"));
            }
            if(jsonObject1.containsKey("reason4")){
                reasonList.add(jsonObject1.getString("reason4"));
            }
        }
        if(jsonObject.containsKey("选择尺寸")){
            JSONObject jsonObject1 = jsonObject.getJSONObject("选择尺寸");
            if(jsonObject1.containsKey("reason1")){
                reasonList.add(jsonObject1.getString("reason1"));
            }
            if(jsonObject1.containsKey("reason2")){
                reasonList.add(jsonObject1.getString("reason2"));
            }
            if(jsonObject1.containsKey("reason3")){
                reasonList.add(jsonObject1.getString("reason3"));
            }
            if(jsonObject1.containsKey("reason4")){
                reasonList.add(jsonObject1.getString("reason4"));
            }
        }
        if(jsonObject.containsKey("其他")){
            JSONObject jsonObject1 = jsonObject.getJSONObject("其他");
            if(jsonObject1.containsKey("reason1")){
                reasonList.add(jsonObject1.getString("reason1"));
            }
            if(jsonObject1.containsKey("reason2")){
                reasonList.add(jsonObject1.getString("reason2"));
            }
            if(jsonObject1.containsKey("reason3")){
                reasonList.add(jsonObject1.getString("reason3"));
            }
            if(jsonObject1.containsKey("reason4")){
                reasonList.add(jsonObject1.getString("reason4"));
            }
        }

        System.out.print(JSON.toJSONString(reasonList));
    }

}
