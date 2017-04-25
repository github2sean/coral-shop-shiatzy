package com.dookay.coral.common.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 枚举工具类
 *
 * @author : houkun
 * @version : v0.0.1
 * @since : 2016/11/22
 */
public class EnumUtils {

    public static Map<String, String> getEnumValues(String className, String key) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Class clazz = Class.forName(className);
        for (Object obj : clazz.getEnumConstants()) {
            Method m = obj.getClass().getDeclaredMethod("values", null);
            Object[] results = (Object[]) m.invoke(obj, null);
            for (Object result : results) {
                Field nameField = result.getClass().getDeclaredField(key);
                ReflectionUtils.makeAccessible(nameField);
                Field codeField = result.getClass().getDeclaredField("value");
                ReflectionUtils.makeAccessible(codeField);
                map.put(String.valueOf(ReflectionUtils.getField(codeField, result)),
                        (String) ReflectionUtils.getField(nameField, result));
            }
            return map;
        }
        return null;
    }

    /**
     * 通过value值获取对应的描述信息
     *
     * @param value
     * @param enumT
     * @param methodNames
     * @return Object description
     */
    public static <T> Object getDescriptionByValue(Object value, Class<T> enumT, String... methodNames) {
        //不是枚举则返回""
        if (!enumT.isEnum()) {
            return "";
        }
        //获取枚举的所有枚举属性
        T[] enums = enumT.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return "";
        }
        int count = methodNames.length;
        //默认枚举方法，与接口方法一致
        String valueMethod = "getValue";
        String desMethod = "getDescription";
        if (count >= 1 && !"".equals(methodNames[0])) {
            valueMethod = methodNames[0];
        }
        if (count == 2 && !"".equals(methodNames[1])) {
            desMethod = methodNames[1];
        }
        for (int i = 0, len = enums.length; i < len; i++) {
            T t = enums[i];
            try {
                Object resultValue = getMethodValue(valueMethod, t);//获取枚举对象value
                if (resultValue.toString().equals(value + "")) {
                    Object resultDes = getMethodValue(desMethod, t); //存在则返回对应描述
                    return resultDes;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 通过描述获取对应枚举值
     * @param description
     * @param enumT
     * @param methodNames
     * @param <T>
     * @return
     */
    public static <T> Object getValueByDescription(Object description, Class<T> enumT, String... methodNames) {
       // 不是枚举，返回null
        if (!enumT.isEnum()) {
            return null;
        }
        //获取枚举的所有枚举属性
        T[] enums = enumT.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return null;
        }
        int count = methodNames.length;
        //默认枚举方法，与接口方法一致
        String valueMethod = "getValue";
        String desMethod = "getDescription";
        if (count >= 1 && !"".equals(methodNames[0])) {
            valueMethod = methodNames[0];
        }
        if (count == 2 && !"".equals(methodNames[1])) {
            desMethod = methodNames[1];
        }
        for (int i = 0, len = enums.length; i < len; i++) {
            T t = enums[i];
            try {
                Object descriptionValue = getMethodValue(desMethod, t);//获取枚举对象value
                if (Objects.equals(descriptionValue, description)) {
                    Object resultValue = getMethodValue(valueMethod, t); // 存在则返回对应值
                    return resultValue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 通过枚举value获取枚举属性名
     *
     * @param value
     * @param enumT
     * @param methodNames
     * @return enum key
     */
    public static <T> String getNameByValue(Object value, Class<T> enumT,
                                            String... methodNames) {
        if (!enumT.isEnum()) {
            return "";
        }
        T[] enums = enumT.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return "";
        }
        int count = methodNames.length;
        String valueMathod = "getValue"; //默认方法
        if (count >= 1 && !"".equals(methodNames[0])) { //独立方法
            valueMathod = methodNames[0];
        }
        for (int i = 0, len = enums.length; i < len; i++) {
            T tobj = enums[i];
            try {
                Object resultValue = getMethodValue(valueMathod, tobj);
                if (resultValue != null
                        && resultValue.toString().equals(value + "")) { //存在则返回对应值
                    return tobj + "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 枚举转map, value作为map的key,description作为map的value
     *
     * @param enumT
     * @param methodNames
     * @return enum mapcolloction
     */
    public static <T> Map<Object, String> ToMap(Class<T> enumT,
                                                String... methodNames) {
        Map<Object, String> enummap = new HashMap<Object, String>();
        if (!enumT.isEnum()) {
            return enummap;
        }
        T[] enums = enumT.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return enummap;
        }
        int count = methodNames.length;
        String valueMathod = "getValue"; //默认接口value方法
        String desMathod = "getDescription";//默认接口description方法
        if (count >= 1 && !"".equals(methodNames[0])) { //扩展方法
            valueMathod = methodNames[0];
        }
        if (count == 2 && !"".equals(methodNames[1])) {
            desMathod = methodNames[1];
        }
        for (int i = 0, len = enums.length; i < len; i++) {
            T tobj = enums[i];
            try {
                Object resultValue = getMethodValue(valueMathod, tobj); //获取value值
                if ("".equals(resultValue)) {
                    continue;
                }
                Object resultDes = getMethodValue(desMathod, tobj); //获取description描述值
                if ("".equals(resultDes)) { //如果描述不存在获取属性值
                    resultDes = tobj;
                }
                enummap.put(resultValue, resultDes + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return enummap;
    }

    /**
     * 根据反射，通过方法名称获取方法值
     *
     * @param methodName
     * @param obj
     * @param args
     * @return return value
     */
    private static <T> Object getMethodValue(String methodName, T obj,
                                             Object... args) {
        Object result = "";
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName); ///确定方法
            if (method == null) {
                return result;
            }
            result = method.invoke(obj, args); //方法执行
            if (result == null) {
                result = "";
            }
            return result; //返回结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据value获取description
     *
     * @param <T>
     * @param clazz
     * @param ordinal
     * @return
     */
    public static String valueOf(Class clazz, String value, String key) {
        try {
            return getEnumValues(clazz.getName(), key).get(value);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

}
