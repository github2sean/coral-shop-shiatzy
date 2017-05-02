package com.dookay.coral.common.event;

import com.dookay.coral.common.web.utils.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/24
 */
public class DomainEventBus {

   public static void publishEvent(DomainEvent event){
       ApplicationContext ctx = SpringContextHolder.getApplicationContext();
       ctx.publishEvent(event);
    }
}
