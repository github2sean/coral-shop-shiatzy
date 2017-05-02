package com.dookay.coral.common.exception;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/12
 */
public class DomainNotFoundException extends RuntimeException{
    public DomainNotFoundException(String message){
        super(message);
    }
}
