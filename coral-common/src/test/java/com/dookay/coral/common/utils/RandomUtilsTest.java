package com.dookay.coral.common.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/23
 */
public class RandomUtilsTest {
    private Logger logger = LoggerFactory.getLogger(RandomUtilsTest.class);

    @Test
    public void randomLowerWords() throws Exception {
       System.out.print(RandomUtils.randomLowerWords(6));
    }

    @Test
    public void randomUpperWords() throws Exception {
        System.out.print(RandomUtils.randomUpperWords(6));
    }

    @Test
    public void randomNumbers() throws Exception {
        System.out.print(RandomUtils.randomNumbers(6));
    }

    @Test
    public void randomCustomUUID() throws Exception {
        System.out.print(RandomUtils.randomCustomUUID());
    }

    @Test
    public void randomUUID() throws Exception {
        System.out.print(RandomUtils.randomUUID());
    }

    @Test
    public void randomString() throws Exception {
        System.out.print(RandomUtils.randomString(12));
    }

}