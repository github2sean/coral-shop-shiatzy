package com.dookay.coral.common;

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
}
