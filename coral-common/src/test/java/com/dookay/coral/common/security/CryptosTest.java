package com.dookay.coral.common.security;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/1/22
 */
public class CryptosTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    public void aesEncrypt() throws Exception {
        String str = Cryptos.aesEncrypt("12345678901");
        logger.info(str);
    }

    @Test
    public void aesDecrypt() throws Exception {
        String str = Cryptos.aesDecrypt("22c9f962b159e40b8a3b830bb5003a9c");
        logger.info(str);
    }

}