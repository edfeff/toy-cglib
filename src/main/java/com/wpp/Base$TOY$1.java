package com.wpp;

import java.lang.reflect.Method;

/**
 * Function:
 * Author: wpp
 * Date: 2022/12/8 21:22
 */
public class Base$TOY$1 extends Base implements ToyProxy {


    private static Method voidnoargMethod;

    private static Method voidnoargMethodProxy;

    private static Method noargMethod;
    private static Method noargMethodProxy;

    private static Method workMethod;
    private static Method workMethodProxy;
    private static Method toStringMethod;
    private static Method toStringMethodProxy;

    private static Method hashCodeMethod;
    private static Method hashCodeMethodProxy;

    private static Method equalsMethod;
    private static Method equalsMethodProxy;


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

            hashCodeMethod = Object.class.getDeclaredMethod("hashCode");
            hashCodeMethodProxy = c2.getDeclaredMethod("__super__hashCode");

            equalsMethod = Object.class.getDeclaredMethod("equals", Object.class);
            equalsMethodProxy = c2.getDeclaredMethod("__super__equals", Object.class);

            voidnoargMethod = c1.getDeclaredMethod("voidnoarg");
            voidnoargMethodProxy = c2.getDeclaredMethod("__super__voidnoarg");

        } catch (Exception e) {
        }
    }

    private Interceptor interceptor;

    @Override
    public void voidnoarg() {
        interceptor.invoke(this, voidnoargMethod, null, voidnoargMethodProxy);
    }

    public void __super__voidnoarg() {
        super.voidnoarg();
    }

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


    @Override
    public int hashCode() {
        return (int) interceptor.invoke(this, hashCodeMethod, null, hashCodeMethodProxy);
    }

    public int __super__hashCode() {
        return super.hashCode();

    }

    @Override
    public boolean equals(Object obj) {
        return (boolean) interceptor.invoke(this, equalsMethod, null, equalsMethodProxy);
    }

    public boolean __super__equals(Object obj) {
        return super.equals(obj);
    }

}
