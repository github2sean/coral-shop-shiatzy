package com.dookay.shiatzy.web.admin.config;

import org.apache.xbean.spring.context.XmlWebApplicationContext;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/21
 */
public class MyWebApplicationContext extends XmlWebApplicationContext {
    @Override
    protected DefaultListableBeanFactory createBeanFactory() {
        DefaultListableBeanFactory beanFactory =  super.createBeanFactory();
        beanFactory.setAllowRawInjectionDespiteWrapping(true);
        return beanFactory;
    }
}
