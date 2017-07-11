package com.dookay.coral.common.security;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/27
 */
public class SecurityUtilsTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void validPassword() throws Exception {
        Boolean flag = SecurityUtils.validPassword("12345g");
        logger.info(flag.toString());
    }

    @Test
    public void dbPassword() throws Exception {
        logger.info(com.alibaba.druid.filter.config.ConfigTools.encrypt("wIBcphtD8VWT"));
    }
}