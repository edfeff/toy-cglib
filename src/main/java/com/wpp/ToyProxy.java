package com.wpp;

/**
 * Function: 代理对象都会实现的接口
 * Author: wpp
 * Date: 2022/12/8 21:52
 */
public interface ToyProxy {
    /**
     * 为创建的代理对象设置拦截器
     *
     * @param interceptor
     */
    public void __proxy__setInterceptor(Interceptor interceptor);
}
