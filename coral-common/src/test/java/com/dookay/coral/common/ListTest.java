package com.dookay.coral.common;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/18
 */
public class ListTest {

    @Test
    public void  toArray(){
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("name");
        fields.toArray(new String[0]);

        for(String i : fields)
            System.out.println(i);
    }
}
