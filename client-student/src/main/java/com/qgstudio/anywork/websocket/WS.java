package com.qgstudio.anywork.websocket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WebSocket的订阅方法注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface WS {
    /**
     * @return 回调的线程
     */
    ThreadMode threadMode() default ThreadMode.MAIN;
}
