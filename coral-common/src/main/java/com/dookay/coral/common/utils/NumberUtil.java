package com.dookay.coral.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : ~我很伤心~
 * @version : v0.0.1
 * @Description:Double数字工具
 * @since :
 */
public class NumberUtil {
    private static DecimalFormat format = new DecimalFormat("#.00");

    public static String format(Double d, String format) {
        return new DecimalFormat(format).format(d);
    }

    public static String format(Double d) {
        return format.format(d);
    }

    public static double sum(double... values) {
        BigDecimal bd1 = null;
        for (double value : values) {
            if (bd1 == null)
                bd1 = new BigDecimal(value);
            else
                bd1 = bd1.add(new BigDecimal(Double.toString(value)));
        }
        return bd1.doubleValue();
    }

    public static double sub(double... values) {
        BigDecimal bd1 = null;
        for (double value : values) {
            if (bd1 == null)
                bd1 = new BigDecimal(value);
            else
                bd1 = bd1.subtract(new BigDecimal(Double.toString(value)));
        }
        return bd1.doubleValue();
    }

    public static double multiply(double... values) {
        BigDecimal bd1 = new BigDecimal(1);
        for (double value : values) {
            bd1 = bd1.multiply(new BigDecimal(Double.toString(value)));
        }
        return bd1.doubleValue();
    }

    public static double divide(int scale, double... values) {
        BigDecimal bd1 = new BigDecimal(1);
        for (double value : values) {
            bd1 = bd1.divide(new BigDecimal(Double.toString(value)), scale, BigDecimal.ROUND_HALF_UP);
        }
        return bd1.doubleValue();
    }

    public static double divide(double... values) {
        BigDecimal bd1 = new BigDecimal(1);
        for (double value : values) {
            bd1 = bd1.divide(new BigDecimal(Double.toString(value)));
        }
        return bd1.doubleValue();
    }

    public static double round(double value, int scale) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    public static Long toLong(double value) {
        BigDecimal bd1 = null;
        try {
            bd1 = new BigDecimal(Double.toString(value));
        } catch (Exception e) {
            return 0L;
        }
        return bd1.multiply(new BigDecimal(100)).longValue();
    }

    public static double toDouble(Long value) {
        BigDecimal bd1 = null;
        try {
            bd1 = new BigDecimal(Double.toString(value));
        } catch (Exception e) {
            return 0d;
        }
        return bd1.divide(new BigDecimal(100)).doubleValue();
    }

    /**
     * 获取数字大约数
     *
     * @param num
     * @return
     * @throws Exception
     */
    public static String dimDigit(Double num) throws Exception {
        String unitArr[] = {"万亿", "亿", "万", ""}; //把钱数分成段,每四个一段,实际上得到的是一个二维数组
        int lengthArr[] = {13, 9, 5, 1};
        BigDecimal bigDecimal = BigDecimal.valueOf(num);
        String strVal = String.valueOf(bigDecimal.toBigInteger());
        char[] chars = strVal.toCharArray();
        String result = strVal;
        for (int i = 0; i < lengthArr.length; i++) {
            if (chars.length >= lengthArr[i]) {
                result = strVal.substring(0, chars.length - lengthArr[i] + 1) + unitArr[i];
                break;
            }
        }
        return result;
    }

}
