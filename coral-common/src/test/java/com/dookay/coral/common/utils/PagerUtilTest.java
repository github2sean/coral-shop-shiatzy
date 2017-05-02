package com.dookay.coral.common.utils;


import com.dookay.coral.common.web.utils.PagerUtil;
import org.junit.Test;

/**
 * xxxxx
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/11/21
 */
public class PagerUtilTest {
    @Test
    public void generateUrl() throws Exception {
        System.out.println(PagerUtil.generateUrl("/admin","size=20",2));
    }
}
