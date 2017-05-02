package com.dookay.coral.common.cache;


import com.dookay.coral.common.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/30
 */
public class RedisCacheTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisCache redisCache;

    @Test
    public void putCache() throws Exception {
        redisCache.putCache("key","123123");
        String val = redisCache.getCache("key",String.class);
        logger.info(val);
    }

}