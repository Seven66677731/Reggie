package com.seven.reggie.common;

/**
 * 基于ThreadLocal类封装工具类，用于保存和获取当前用户id
 */
public class BaseContext {

    private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();


    public static void setCurrentId(Long id) {
        THREAD_LOCAL.set(id);
    }

    public static Long getCurrentId() {
        return THREAD_LOCAL.get();
    }
}
