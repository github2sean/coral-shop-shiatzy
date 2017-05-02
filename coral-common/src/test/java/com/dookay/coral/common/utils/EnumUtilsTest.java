package com.dookay.coral.common.utils;

import com.dookay.coral.common.enums.IEnum;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;


/**
 * 枚举工具测试类
 *
 * @author : houkun
 * @version : v0.0.1
 * @since : 2016/11/22
 */
public class EnumUtilsTest {
    @Test
    public void getDescriptionByValue() throws Exception {
        String des = EnumUtils.getDescriptionByValue(1, TestEnum.class).toString();
        Assert.isTrue(des.equals("测试1"));

        Assert.isTrue(EnumUtils.getDescriptionByValue(3, TestEnum.class).equals(""));
    }

    @Test
    public void getNameByValue() throws Exception {
        Assert.isTrue(EnumUtils.getNameByValue(1, TestEnum.class).equals("TEST_1"));
        Assert.isTrue(EnumUtils.getNameByValue(0, TestEnum.class).equals(""));
    }

    @Test
    public void getValueByDescription() throws Exception {
        Assert.isTrue(Objects.equals(
                EnumUtils.getValueByDescription("测试1", TestEnum.class), 1));
    }

    @Test
    public void toMap() throws Exception {
        Map enumMap = EnumUtils.ToMap(TestEnum.class);
        Assert.isTrue(enumMap.get(1).equals("测试1"));
        Assert.isTrue(enumMap.get(2).equals("测试2"));
    }

}

enum TestEnum implements IEnum {
    TEST_1(1, "测试1"),
    TEST_2(2, "测试2");

    private int value;
    private String description;

    TestEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
