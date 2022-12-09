package com.wpp;

import java.lang.reflect.Method;

/**
 * Function:
 * Author: wpp
 * Date: 2022/12/8 21:25
 */
public interface Interceptor {
    public Object invoke(Object proxy,
                         Method method,
                         Object[] args,
                         Object mp);
}
