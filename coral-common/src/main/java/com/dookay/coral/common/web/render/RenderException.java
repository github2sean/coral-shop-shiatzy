package com.dookay.coral.common.web.render;

/**
 * 渲染异常
 * @author Luxor
 * @version v0.1.1
 * @since 2016/11/22
 */
public class RenderException extends RuntimeException {

    private static final long serialVersionUID = -6448434551667513804L;

    public RenderException() {
        super();
    }

    public RenderException(String message) {
        super(message);
    }

    public RenderException(Throwable cause) {
        super(cause);
    }

    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }
}
