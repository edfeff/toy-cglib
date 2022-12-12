package com.wpp;

import org.objectweb.asm.Type;

/**
 * @author wpp
 * @desc 内部常量
 * @see
 * @since 2022/12/12
 */
public class Constants {
    public final static String CLASS_INIT_METHOD = "<clinit>";
    public final static String INSTANCE_INIT_METHOD = "<init>";
    public final static String CLASS_INIT_METHOD_DESC = "()V";
    public final static String INSTANCE_INIT_METHOD_DESC = "()V";
    public final static String interceptorInternalName = Type.getInternalName(Interceptor.class);
    public final static String interceptorInvokeMethodName = "invoke";
    public final static String interceptorInvokeMethodDesc = "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;";
    public final static String interceptorFieldName = "interceptor";
    public final static String interceptorFieldDesc = "Lcom/wpp/Interceptor;";

    public final static String interceptorMethodName = "__proxy__setInterceptor";
    public final static String interceptorMethodDesc = "(Lcom/wpp/Interceptor;)V";


    public final static String INTER_NAME_OBJECT = "java/lang/Object";

    public final static String INTER_NAME_CLASS = "java/lang/Class";
    public final static String NAME_METHOD_GETDECLAREDMETHOD = "getDeclaredMethod";
    public final static String NAME_METHOD_FORNAME = "forName";
    public final static String DESC_METHOD_GETDECLAREDMETHOD = "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;";
    public final static String DESC_NAME_METHOD_FORNAME = "(Ljava/lang/String;)Ljava/lang/Class;";

    public final static String DESC_TYPE_METHOD = "Ljava/lang/reflect/Method;";
}
