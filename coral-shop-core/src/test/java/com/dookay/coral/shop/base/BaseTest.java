package com.dookay.coral.shop.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Luxor
 * @version v0.0.1.
 * @since 2016/11/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@Rollback(false)
//@Transactional
@ContextConfiguration({"classpath:spring-test.xml"})
public abstract class BaseTest {

}
