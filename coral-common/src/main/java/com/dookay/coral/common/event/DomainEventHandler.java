package com.dookay.coral.common.event;

import com.dookay.coral.common.exception.ServiceException;
import org.springframework.context.ApplicationListener;

import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/8
 */
public abstract class DomainEventHandler<T extends DomainEvent> implements ApplicationListener<T> {

    @Override
    public void onApplicationEvent(T t) {
        handle(t);
    }

    public abstract void handle(T event);

}
