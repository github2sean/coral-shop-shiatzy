package com.dookay.coral.common.security;

import com.dookay.coral.common.utils.Md5Utils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

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

    @Test
    public void  crypt() throws Exception {
        String  originalPassword = "100001";
        String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(10));
        System.out.println(generatedSecuredPasswordHash);

        boolean matched = BCrypt.checkpw(originalPassword, "$2y$10$iwC5hKV.5pxIQ2E4K61KN.DOT1ZU/J4GEAbDBxwfiSQ3r76CJ2kkK");
        System.out.println(matched);
    }
}