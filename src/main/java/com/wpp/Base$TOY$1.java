package com.wpp;

import java.lang.reflect.Method;

/**
 * Function:
 * Author: wpp
 * Date: 2022/12/8 21:22
 */
public class Base$TOY$1 extends Base implements ToyProxy {

    private static Method noargMethod;
    private static Method noargMethodProxy;

    private static Method workMethod;
    private static Method workMethodProxy;
    private static Method toStringMethod;
    private static Method toStringMethodProxy;

    static {
        try {

            Class c1 = Class.forName("com.wpp.Base");
            Class c2 = Class.forName("com.wpp.Base$TOY$1");
            workMethod = c1.getDeclaredMethod("work", String.class);
            workMethodProxy = c2.getDeclaredMethod("__super__work", String.class);

            noargMethod = c1.getDeclaredMethod("noarg");
            noargMethodProxy = c2.getDeclaredMethod("__super__noarg");

            toStringMethod = Object.class.getDeclaredMethod("toString");
            toStringMethodProxy = c2.getDeclaredMethod("__super__toString");

        } catch (Exception e) {
        }
    }

    private Interceptor interceptor;

    public String __super__noarg() {
        return super.noarg();
    }

    @Override
    public String noarg() {
        return (String) interceptor.invoke(this, noargMethod, null, noargMethodProxy);
    }

    public String __super__work(String arg) {
        return super.work(arg);
    }

    @Override
    public String work(String arg) {
        return (String) interceptor.invoke(this, workMethod, new Object[]{arg}, workMethodProxy);
    }

    public String __super__toString() {
        return super.toString();
    }

    @Override
    public String toString() {
        return (String) interceptor.invoke(this, toStringMethod, null, toStringMethodProxy);
    }

    public void __proxy__setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

}
