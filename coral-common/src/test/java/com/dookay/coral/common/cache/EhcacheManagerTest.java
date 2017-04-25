package com.dookay.coral.common.cache;


import com.dookay.coral.common.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/17
 */
public class EhcacheManagerTest extends BaseTest {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void get() throws Exception {
        Object result =   EhcacheManager.get("123");
        logger.info(result.toString());
    }

    @Test
    public void put() throws Exception {
        String key = "foo";
        String value = "boo";
        EhcacheManager.put(key,value);
        Object result = EhcacheManager.get(key);
        assertEquals(result,value);
    }

    @Test
    public void remove() throws Exception {
        EhcacheManager.remove("12313");
    }

}