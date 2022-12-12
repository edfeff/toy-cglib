package com.wpp;

import org.objectweb.asm.*;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Function: 增强类构建器
 * Author: wpp
 * Date: 2022/12/8 21:36
 */
public class Enhancer implements Opcodes {
    private static final String FLAG = "$TOY$";
    private static AtomicInteger counter = new AtomicInteger(1);
    private Class superClass;
    private Interceptor interceptor;
    private ClassLoader classLoader;
    private String classname;
    private String classDesc;
    private String supername;
    private String superDesc;

    public ClassLoader getClassLoader() {
        if (classLoader == null) {
            classLoader = Enhancer.class.getClassLoader();
        }
        return classLoader;
    }

    public void setSuperClass(Class superClass) {
        this.superClass = superClass;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public <T> T create() throws Exception {
        validate();
        try {
            supername = getSuperName();
            superDesc = Type.getInternalName(superClass);
            classname = generateClassName();
            classDesc = classname.replace(".", "/");

            //生成字节码
            byte[] bytes = genClass();

            //字节码后处理
            bytes = postProcessClassBytes(bytes);

            //定义类
            Class clazz = defineClass(classname, bytes);

            //创建实例对象
            Object instance = genInstance(clazz);

            //实例对象后处理
            instance = postProcessInstance(instance);

            return (T) instance;
        } catch (Exception e) {
            throw e;
        }
    }

    private Object postProcessInstance(Object instance) {
        if (instance instanceof ToyProxy) {
            ((ToyProxy) instance).__proxy__setInterceptor(getInterceptor());
        }
        return instance;
    }

    private Object genInstance(Class clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();//todo 实例化优化
    }

    private byte[] genClass() {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, classDesc, null, superDesc, new String[]{Type.getInternalName(ToyProxy.class)});
        addFields(classWriter);
        addStaticCode(classWriter);
        addConstructor(classWriter);
        addMethods(classWriter);
        classWriter.visitEnd();
        return classWriter.toByteArray();
    }

    private void addStaticCode(ClassWriter classWriter) {
        MethodVisitor mv = classWriter.visitMethod(ACC_STATIC, Constants.CLASS_INIT_METHOD, Constants.CLASS_INIT_METHOD_DESC, null, null);
        mv.visitCode();

        mv.visitLdcInsn(supername);
        mv.visitMethodInsn(INVOKESTATIC, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_FORNAME, Constants.DESC_NAME_METHOD_FORNAME, false);
        mv.visitVarInsn(ASTORE, 0);

        mv.visitLdcInsn(classname);
        mv.visitMethodInsn(INVOKESTATIC, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_FORNAME, Constants.DESC_NAME_METHOD_FORNAME, false);
        mv.visitVarInsn(ASTORE, 1);

        Class clazz = getSuperClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
                MethodInfo mi = MethodInfo.create(method);
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 0) {
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitLdcInsn(mi.name);
                    mv.visitInsn(ICONST_0);
                    mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
                    mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
                    mv.visitFieldInsn(PUTSTATIC, classDesc, mi.methodFieldName, Constants.DESC_TYPE_METHOD);

                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitLdcInsn(mi.superMethodName);
                    mv.visitInsn(ICONST_0);
                    mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
                    mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
                    mv.visitFieldInsn(PUTSTATIC, classDesc, mi.methodProxyFieldName, Constants.DESC_TYPE_METHOD);

                } else if (parameterTypes.length == 1) {
                    Class<?> parameterType = parameterTypes[0];
                    {
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitLdcInsn(mi.name);
                        mv.visitInsn(ICONST_1);
                        mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
                        mv.visitInsn(DUP);
                        mv.visitInsn(ICONST_0);
                        mv.visitLdcInsn(Type.getType(parameterType));
                        mv.visitInsn(AASTORE);
                        mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
                        mv.visitFieldInsn(PUTSTATIC, classDesc, mi.methodFieldName, Constants.DESC_TYPE_METHOD);
                        //
                        mv.visitVarInsn(ALOAD, 1);
                        mv.visitLdcInsn(mi.superMethodName);
                        mv.visitInsn(ICONST_1);
                        mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
                        mv.visitInsn(DUP);
                        mv.visitInsn(ICONST_0);
                        mv.visitLdcInsn(Type.getType(parameterType));
                        mv.visitInsn(AASTORE);
                        mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
                        mv.visitFieldInsn(PUTSTATIC, classDesc, mi.methodProxyFieldName, Constants.DESC_TYPE_METHOD);
                    }
                } else {
                    if (parameterTypes.length <= 5) {
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitLdcInsn(mi.name);
                        mv.visitInsn(3 + parameterTypes.length); //
                        mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
                        for (int i = 0; i < parameterTypes.length; i++) {
                            mv.visitInsn(DUP);
                            mv.visitInsn(3 + i);
                            mv.visitLdcInsn(Type.getType(parameterTypes[i]));
                            mv.visitInsn(AASTORE);
                        }
                        mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
                        mv.visitFieldInsn(PUTSTATIC, classDesc, mi.methodFieldName, Constants.DESC_TYPE_METHOD);
                        mv.visitVarInsn(ALOAD, 1);
                        mv.visitLdcInsn(mi.superMethodName);
                        mv.visitInsn(3 + parameterTypes.length);
                        mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
                        for (int i = 0; i < parameterTypes.length; i++) {
                            mv.visitInsn(DUP);
                            mv.visitInsn(3 + i);
                            mv.visitLdcInsn(Type.getType(parameterTypes[i]));
                            mv.visitInsn(AASTORE);
                        }
                        mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
                        mv.visitFieldInsn(PUTSTATIC, classDesc, mi.methodProxyFieldName, Constants.DESC_TYPE_METHOD);
                    } else {
                        throw new IllegalArgumentException("暂不支持参数个数大于5的方法");
                    }
                }
            }
        }
        //Object 方法
        {//toString
            mv.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
            mv.visitLdcInsn("toString");
            mv.visitInsn(ICONST_0);
            mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
            mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
            mv.visitFieldInsn(PUTSTATIC, classDesc, "toStringMethod", Constants.DESC_TYPE_METHOD);
            //
            mv.visitVarInsn(ALOAD, 1);
            mv.visitLdcInsn("__super__toString");
            mv.visitInsn(ICONST_0);
            mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
            mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
            mv.visitFieldInsn(PUTSTATIC, classDesc, "toStringMethodProxy", Constants.DESC_TYPE_METHOD);
        }
        {//hashcode
            mv.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
            mv.visitLdcInsn("hashCode");
            mv.visitInsn(ICONST_0);
            mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
            mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
            mv.visitFieldInsn(PUTSTATIC, classDesc, "hashCodeMethod", Constants.DESC_TYPE_METHOD);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitLdcInsn("__super__hashCode");
            mv.visitInsn(ICONST_0);
            mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
            mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
            mv.visitFieldInsn(PUTSTATIC, classDesc, "hashCodeMethodProxy", Constants.DESC_TYPE_METHOD);
        }
        {//equals
            mv.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
            mv.visitLdcInsn("equals");
            mv.visitInsn(ICONST_1);
            mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
            mv.visitInsn(AASTORE);
            mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
            mv.visitFieldInsn(PUTSTATIC, classDesc, "equalsMethod", Constants.DESC_TYPE_METHOD);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitLdcInsn("__super__equals");
            mv.visitInsn(ICONST_1);
            mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_CLASS);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
            mv.visitInsn(AASTORE);
            mv.visitMethodInsn(INVOKEVIRTUAL, Constants.INTER_NAME_CLASS, Constants.NAME_METHOD_GETDECLAREDMETHOD, Constants.DESC_METHOD_GETDECLAREDMETHOD, false);
            mv.visitFieldInsn(PUTSTATIC, classDesc, "equalsMethodProxy", Constants.DESC_TYPE_METHOD);

        }
        mv.visitInsn(RETURN);
        mv.visitMaxs(6, 2);
        mv.visitEnd();
    }

    private byte[] postProcessClassBytes(byte[] bytes) {
        final String debugLocation = System.getProperty("com.wpp.debug.location");
        if (debugLocation != null) {
            FileUtil.saveBytesToFile(bytes, debugLocation + File.separator + classname.substring(classname.lastIndexOf(".") + 1) + ".class");
        }
        return bytes;
    }

    private void addConstructor(ClassWriter classWriter) {
        //todo 父没有默认构造函数的处理
        //添加无参数构造函数
        {
            MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, Constants.INSTANCE_INIT_METHOD, Constants.INSTANCE_INIT_METHOD_DESC, null, null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, superDesc, Constants.INSTANCE_INIT_METHOD, Constants.INSTANCE_INIT_METHOD_DESC, false);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        //TODO 父类的其他构造函数

    }

    private Class<?> defineClass(String className, byte[] bytes) {
        ClassLoader loader = getClassLoader();

        try {
            Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            if (!defineClass.isAccessible()) {
                defineClass.setAccessible(true);
            }
            return (Class) defineClass.invoke(loader, className, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void addFields(ClassWriter classWriter) {
        addObjectFields(classWriter);
        addSuperFields(classWriter);
    }

    private void addSuperFields(ClassWriter classWriter) {
        Class clazz = getSuperClass();

        for (Method method : clazz.getDeclaredMethods()) {
            final MethodInfo mi = MethodInfo.create(method);
            FieldVisitor fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, mi.methodFieldName, Constants.DESC_TYPE_METHOD, null, null);
            fv.visitEnd();
            fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, mi.methodProxyFieldName, Constants.DESC_TYPE_METHOD, null, null);
            fv.visitEnd();
        }
    }

    private void addObjectFields(ClassWriter classWriter) {
        //toString
        FieldVisitor fv = classWriter.visitField(ACC_PRIVATE, Constants.interceptorFieldName, Constants.interceptorFieldDesc, null, null);
        fv.visitEnd();
        fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "toStringMethod", Constants.DESC_TYPE_METHOD, null, null);
        fv.visitEnd();
        fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "toStringMethodProxy", Constants.DESC_TYPE_METHOD, null, null);
        fv.visitEnd();

        //hashCode
        fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "hashCodeMethod", Constants.DESC_TYPE_METHOD, null, null);
        fv.visitEnd();
        fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "hashCodeMethodProxy", Constants.DESC_TYPE_METHOD, null, null);
        fv.visitEnd();

        //equals
        fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "equalsMethod", Constants.DESC_TYPE_METHOD, null, null);
        fv.visitEnd();
        fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "equalsMethodProxy", Constants.DESC_TYPE_METHOD, null, null);
        fv.visitEnd();
    }

    private void addMethods(ClassWriter classWriter) {
        addSuperMethod(classWriter);
        addProxyMethod(classWriter);
        addObjectMethod(classWriter);
    }


    private void addProxyMethod(ClassWriter classWriter) {
        {
            MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, Constants.interceptorMethodName, Constants.interceptorMethodDesc, null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(PUTFIELD, classDesc, Constants.interceptorFieldName, Constants.interceptorFieldDesc);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }
    }

    private void addObjectMethod(ClassWriter classWriter) {
        //toString
        {
            MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, classDesc, Constants.interceptorFieldName, Constants.interceptorFieldDesc);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETSTATIC, classDesc, "toStringMethod", Constants.DESC_TYPE_METHOD);
            mv.visitInsn(ACONST_NULL);
            mv.visitFieldInsn(GETSTATIC, classDesc, "toStringMethodProxy", Constants.DESC_TYPE_METHOD);
            mv.visitMethodInsn(INVOKEINTERFACE, Constants.interceptorInternalName, Constants.interceptorInvokeMethodName, Constants.interceptorInvokeMethodDesc, true);
            mv.visitTypeInsn(CHECKCAST, "java/lang/String");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(5, 1);
            mv.visitEnd();
        }
        //__super__toString
        {
            MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "__super__toString", "()Ljava/lang/String;", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, Constants.INTER_NAME_OBJECT, "toString", "()Ljava/lang/String;", false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        // hashCode
        {
            MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "hashCode", "()I", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, classDesc, Constants.interceptorFieldName, Constants.interceptorFieldDesc);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETSTATIC, classDesc, "hashCodeMethod", Constants.DESC_TYPE_METHOD);
            mv.visitInsn(ACONST_NULL);
            mv.visitFieldInsn(GETSTATIC, classDesc, "hashCodeMethodProxy", Constants.DESC_TYPE_METHOD);
            mv.visitMethodInsn(INVOKEINTERFACE, Constants.interceptorInternalName, Constants.interceptorInvokeMethodName, Constants.interceptorInvokeMethodDesc, true);
            mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(5, 1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "__super__hashCode", "()I", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, Constants.INTER_NAME_OBJECT, "hashCode", "()I", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        { // equals
            MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "equals", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, classDesc, Constants.interceptorFieldName, Constants.interceptorFieldDesc);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETSTATIC, classDesc, "equalsMethod", Constants.DESC_TYPE_METHOD);
            mv.visitInsn(ACONST_NULL);
            mv.visitFieldInsn(GETSTATIC, classDesc, "equalsMethodProxy", Constants.DESC_TYPE_METHOD);
            mv.visitMethodInsn(INVOKEINTERFACE, Constants.interceptorInternalName, Constants.interceptorInvokeMethodName, Constants.interceptorInvokeMethodDesc, true);
            mv.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(5, 2);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "__super__equals", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKESPECIAL, Constants.INTER_NAME_OBJECT, "equals", "(Ljava/lang/Object;)Z", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }
    }

    private void addSuperMethod(ClassWriter classWriter) {
        Class clazz = getSuperClass();
        for (Method method : clazz.getDeclaredMethods()) {
            //todo 方法签名判断
            final MethodInfo mi = MethodInfo.create(method);
            String name = mi.name;
            String methodDesc = mi.methodDesc;
            String superMethodName = mi.superMethodName;
            String methodFieldName = mi.methodFieldName;
            String methodProxyFieldName = mi.methodProxyFieldName;

            Class<?> methodReturnType = method.getReturnType();
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 0) {
                {
                    MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, superMethodName, methodDesc, null, null);
                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKESPECIAL, superDesc, name, methodDesc, false);
                    handleMethodReturn(mv, methodReturnType);
                    mv.visitMaxs(1, 1);
                    mv.visitEnd();
                }
                {
                    MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, name, methodDesc, null, null);
                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, classDesc, Constants.interceptorFieldName, Constants.interceptorFieldDesc);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETSTATIC, classDesc, methodFieldName, Constants.DESC_TYPE_METHOD);
                    mv.visitInsn(ACONST_NULL);
                    mv.visitFieldInsn(GETSTATIC, classDesc, methodProxyFieldName, Constants.DESC_TYPE_METHOD);
                    mv.visitMethodInsn(INVOKEINTERFACE, Constants.interceptorInternalName, Constants.interceptorInvokeMethodName, Constants.interceptorInvokeMethodDesc, true);
                    handleMethodReturn(mv, methodReturnType);
                    mv.visitMaxs(5, 1);
                    mv.visitEnd();
                }

            } else if (parameterTypes.length == 1) {
                {
                    MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, name, methodDesc, null, null);
                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, classDesc, Constants.interceptorFieldName, Constants.interceptorFieldDesc);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETSTATIC, classDesc, methodFieldName, Constants.DESC_TYPE_METHOD);
                    mv.visitInsn(ICONST_1);
                    mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_OBJECT);
                    mv.visitInsn(DUP);
                    mv.visitInsn(ICONST_0);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitInsn(AASTORE);
                    mv.visitFieldInsn(GETSTATIC, classDesc, methodProxyFieldName, Constants.DESC_TYPE_METHOD);
                    mv.visitMethodInsn(INVOKEINTERFACE, Constants.interceptorInternalName, Constants.interceptorInvokeMethodName, Constants.interceptorInvokeMethodDesc, true);
                    handleMethodReturn(mv, methodReturnType);
                    mv.visitMaxs(7, parameterTypes.length + 1);
                    mv.visitEnd();
                }
                {
                    MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, superMethodName, methodDesc, null, null);
                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKESPECIAL, superDesc, name, methodDesc, false);
                    handleMethodReturn(mv, methodReturnType);
                    mv.visitMaxs(2, 2);
                    mv.visitEnd();
                }
            } else {
                // 多参数的调用
                if (parameterTypes.length <= 5) {
                    {
                        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, name, methodDesc, null, null);
                        mv.visitCode();
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitFieldInsn(GETFIELD, classDesc, Constants.interceptorFieldName, Constants.interceptorFieldDesc);
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitFieldInsn(GETSTATIC, classDesc, methodFieldName, Constants.DESC_TYPE_METHOD);
                        mv.visitInsn(3 + parameterTypes.length);
                        mv.visitTypeInsn(ANEWARRAY, Constants.INTER_NAME_OBJECT);
                        for (int i = 0; i < parameterTypes.length; i++) {
                            mv.visitInsn(DUP);
                            mv.visitInsn(3 + i); // i->iconst_x
                            mv.visitVarInsn(ALOAD, 1 + i); //i-> index
                            mv.visitInsn(AASTORE);
                        }
                        mv.visitFieldInsn(GETSTATIC, classDesc, methodProxyFieldName, Constants.DESC_TYPE_METHOD);
                        mv.visitMethodInsn(INVOKEINTERFACE, Constants.interceptorInternalName, Constants.interceptorInvokeMethodName, Constants.interceptorInvokeMethodDesc, true);
                        handleMethodReturn(mv, methodReturnType);
                        mv.visitMaxs(parameterTypes.length, parameterTypes.length);
                        mv.visitEnd();
                    }
                    {
                        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, superMethodName, methodDesc, null, null);
                        mv.visitCode();
                        for (int i = 0; i <= parameterTypes.length; i++) {
                            mv.visitVarInsn(ALOAD, i);
                        }
                        mv.visitMethodInsn(INVOKESPECIAL, classDesc, name, methodDesc, false);
                        handleMethodReturn(mv, methodReturnType);
                        mv.visitMaxs(parameterTypes.length, parameterTypes.length);
                        mv.visitEnd();
                    }
                } else {
                    throw new IllegalArgumentException("暂不支持参数个数大于5的方法");
                }
            }
        }
    }

    private void handleMethodReturn(MethodVisitor mv, Class methodReturnType) {
        if (isVoid(methodReturnType)) {
            mv.visitInsn(RETURN);
        } else {
            mv.visitTypeInsn(CHECKCAST, Type.getInternalName(methodReturnType)); //类型转换
            mv.visitInsn(ARETURN);
        }
    }

    private String getSuperName() {
        return superClass.getName();
    }

    private String generateClassName() {
        return getSuperName() + FLAG + counter.getAndIncrement();
    }


    public Class getSuperClass() {
        return superClass;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }

    private void validate() {
        // 类、方法 final校验
        Class clazz = getSuperClass();
        if (clazz.isInterface()) {
            throw new IllegalArgumentException("不能增强接口,请使用JDK动态代理");
        }
        if (Modifier.isFinal(clazz.getModifiers())) {
            throw new IllegalArgumentException("不能增强final类");
        }
        if (Modifier.isAbstract(clazz.getModifiers())) {
            throw new IllegalArgumentException("不能增强abstract类");
        }
        final Method[] declaredMethods = clazz.getDeclaredMethods();

        Set<String> methodNameSet = new HashSet<>();
        int methodCount = declaredMethods.length;
        for (Method method : declaredMethods) {
            if (Modifier.isPrivate(method.getModifiers()) || Modifier.isFinal(method.getModifiers())) {
                methodCount -= 1;
            }
            if (methodNameSet.contains(method.getName())) {
                throw new IllegalArgumentException("暂不支持方法的重载");
            }
            methodNameSet.add(method.getName());
        }
        if (methodCount == 0) {
            throw new IllegalArgumentException("没有可以增强的方法");
        }
    }

    private boolean isVoid(Class<?> methodReturnType) {
        return methodReturnType.isAssignableFrom(Void.class) || methodReturnType.isAssignableFrom(void.class);
    }

    static class MethodInfo {
        final String name;
        final String superMethodName;
        final String methodDesc;
        final String methodFieldName;
        final String methodProxyFieldName;
        final Method method;

        public MethodInfo(Method method) {
            this.method = method;
            this.name = method.getName();
            this.superMethodName = genSuperMethodName(this.method);
            this.methodDesc = Type.getMethodDescriptor(method);
            this.methodFieldName = genMethodFieldName(this.method);
            this.methodProxyFieldName = genMethodProxyFieldName(this.method);
        }

        public static MethodInfo create(Method method) {
            //todo cache
            return new MethodInfo(method);
        }

        private String genMethodProxyFieldName(Method method) {
            return method.getName() + "MethodProxy";
        }

        private String genMethodFieldName(Method method) {
            return method.getName() + "Method"; //TODO 暂不考虑方法重载问题
        }

        private String genSuperMethodName(Method method) {
            return "__super__" + method.getName();
        }
    }
}
