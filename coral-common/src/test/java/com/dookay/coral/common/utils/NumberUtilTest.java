package com.dookay.coral.common.utils;

import org.junit.Test;

/**
 * xxxxx
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/12/29
 */
public class NumberUtilTest {
    @Test
    public void dimDigit() throws Exception {
        Double d = 1648741549744586d;
        System.out.println(NumberUtil.dimDigit(d));

         d = 1648741549786d;
        System.out.println(NumberUtil.dimDigit(d));
         d = 16487415486d;
        System.out.println(NumberUtil.dimDigit(d));
         d = 1648435d;
        System.out.println(NumberUtil.dimDigit(d));
    }

}
