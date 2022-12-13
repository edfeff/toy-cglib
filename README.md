# toy-cglib

一个玩具性质的cglib实现，基于ASM,目前仅测试过JAVA8,高版本不支持

# 与cglib的不同点

- cglib使用MethodProxy来承接super方法和代理方法的链接，本项目采用直接暴露父方法的处理方式，不需要生成FastClass这样的中间类

# 示例
```java
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
```
# TODO List

- 超类/方法检查 ✅
- 多参数方法代理 ✅
- 基本类型返回值方法代理 ✅
- 基本类型参数方法代理 ✅
- 多参数构造函数生成
- 字节码缓存
- 重载方法的处理
- ...等