package com.dookay.coral.common.event;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/8
 */
public class DomainEventException extends RuntimeException{

    public DomainEventException(String message) {
        super(message);
    }
}
