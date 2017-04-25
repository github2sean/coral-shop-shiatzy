package com.dookay.coral.common.utils;

import org.junit.Test;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 时间处理工具
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/12/10
 */
public class DateUtilsTest {


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void compareDate() throws Exception {
        Date dt = sdf.parse("2016-09-20 13:30:22");
        Date dt2 = sdf.parse("2016-09-21 13:30:22");
        Date dt3 = sdf.parse("2016-09-19 13:30:22");
        System.out.println(DateUtils.compareDate(dt, dt2));
        System.out.println(DateUtils.compareDate(dt, dt3));
    }

    @Test
    public void getDayStartEnd() throws Exception {
        Date dt = sdf.parse("2016-09-20 13:30:22");
        Date[] dateArr = DateUtils.getDayStartEnd(dt);
        Assert.isTrue("2016-09-20 00:00:00".equals(sdf.format(dateArr[0])));
        Assert.isTrue("2016-09-20 23:59:59".equals(sdf.format(dateArr[1])));
    }

    @Test
    public void getDayStart() throws Exception {
        Date dt = sdf.parse("2016-09-20 13:30:22");
        Date start = DateUtils.getDayStart(dt);
        Assert.isTrue("2016-09-20 00:00:00".equals(sdf.format(start)));
    }

    @Test
    public void getDayEnd() throws Exception {
        Date dt = sdf.parse("2016-09-20 13:30:22");
        Date start = DateUtils.getDayEnd(dt);
        Assert.isTrue("2016-09-20 23:59:59".equals(sdf.format(start)));
    }

    @Test
    public void getNaturalDayCount() throws Exception {
        Date dt1 = sdf.parse("2016-09-20 13:30:22");
        Date dt2 = sdf.parse("2016-09-21 23:30:22");
        Date dt3 = sdf.parse("2016-09-22 11:30:22");
        Assert.isTrue(DateUtils.getNaturalDayCount(dt1, dt2) == 0);
        Assert.isTrue(DateUtils.getNaturalDayCount(dt1, dt3) == 1);
    }
}
