package com.wpp;

import java.lang.reflect.Method;

/**
 * Function: 方法拦截
 * Author: wpp
 * Date: 2022/12/8 21:25
 */
public interface Interceptor {
    /**
     * 方法拦截器
     *
     * @param proxy  代理对象
     * @param method 超类的方法
     * @param args   调用的参数列表
     * @param mp     代理的方法（Method类型）
     * @return
     */
    public Object invoke(Object proxy, Method method, Object[] args, Object mp);
}
