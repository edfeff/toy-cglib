package com.wpp;

import java.lang.reflect.Method;

public class EnhancerDemoMain {
    public static void main(String[] args) throws Throwable {
        genClass();
//        genClass2();
    }

    private static void genClass2() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperClass(Bese2.class);
        enhancer.setInterceptor(new Interceptor() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args, Object mp) {
                Method m = (Method) mp;
                try {
                    final long start = System.currentTimeMillis();
                    Object result = m.invoke(proxy, args);
                    long time = System.currentTimeMillis() - start;
                    System.out.println("方法 " + method.getName() + " 耗时:" + time + "ms");
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        Bese2 bese2 = enhancer.create();

        for (int i = 0; i < 3; i++) {
            bese2.hello("第一次调用" + i);
        }
    }

    private static void genClass() throws Exception {
        System.setProperty("com.wpp.debug.location", "/Users/wpp/test/toy-cglib/target");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperClass(Base.class);
        enhancer.setInterceptor(new Interceptor() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args, Object mp) {
                if (method.getName().equals("toString")) {
                    return "这是拦截器的toString";
                }
                Method m = (Method) mp;
                try {
                    System.out.println("拦截方法-before" + m.getName());
                    Object result = m.invoke(proxy, args);
                    if (result instanceof String) {
                        result = "[" + result + "]";
                    }
                    System.out.println("拦截方法-after" + m.getName());
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        Base proxy = (Base) enhancer.create();
        System.out.println(proxy.toString());

        String result = proxy.work("你好");
        System.out.println(result);

        String noarg = proxy.noarg();
        System.out.println(noarg);
    }

}