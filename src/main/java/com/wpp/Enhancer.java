package com.wpp;

import org.objectweb.asm.*;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Function:
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

    public void setSuperClass(Class superClass) {
        this.superClass = superClass;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public <T> T create() {
        validate();
        try {
            supername = getSuperName();
            superDesc = supername.replace(".", "/");
            classname = generateClassName();
            classDesc = classname.replace(".", "/");

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, classDesc, null, superDesc, new String[]{"com/wpp/ToyProxy"});

            addFields(classWriter);
            addStaticCode(classWriter);
            addConstructor(classWriter);
            addMethods(classWriter);
            classWriter.visitEnd();
            byte[] bytes = classWriter.toByteArray();
            debugFile(bytes);

            Class<?> aClass = defineClass(classname, bytes);
            Object o = aClass.newInstance();//todo 实例化优化
            if (o instanceof ToyProxy) {
                ((ToyProxy) o).__proxy__setInterceptor(getInterceptor());
            }
            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addStaticCode(ClassWriter classWriter) {
        MethodVisitor mv = classWriter.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        mv.visitCode();

        mv.visitLdcInsn(supername);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
        mv.visitVarInsn(ASTORE, 0);

        mv.visitLdcInsn(classname);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
        mv.visitVarInsn(ASTORE, 1);

        Class clazz = getSuperClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
                String name = method.getName();
                String superMethodName = "__super__" + name;
                String methodDesc = Type.getMethodDescriptor(method);
                String methodFieldName = name + "Method";
                String methodProxyFieldName = name + "MethodProxy";
//                    System.out.println("static " + name + " -> " + methodDesc);
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 0) {
                    System.out.println("无参数的");

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitLdcInsn(name);
                    mv.visitInsn(ICONST_0);
                    mv.visitTypeInsn(ANEWARRAY, "java/lang/Class");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
                    mv.visitFieldInsn(PUTSTATIC, classDesc, methodFieldName, "Ljava/lang/reflect/Method;");

                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitLdcInsn(superMethodName);
                    mv.visitInsn(ICONST_0);
                    mv.visitTypeInsn(ANEWARRAY, "java/lang/Class");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
                    mv.visitFieldInsn(PUTSTATIC, classDesc, methodProxyFieldName, "Ljava/lang/reflect/Method;");

                } else if (parameterTypes.length == 1) {
                    Class<?> parameterType = parameterTypes[0];
                    {
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitLdcInsn(name);
                        mv.visitInsn(ICONST_1);
                        mv.visitTypeInsn(ANEWARRAY, "java/lang/Class");
                        mv.visitInsn(DUP);
                        mv.visitInsn(ICONST_0);
                        mv.visitLdcInsn(Type.getType(parameterType));
                        mv.visitInsn(AASTORE);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
                        mv.visitFieldInsn(PUTSTATIC, classDesc, methodFieldName, "Ljava/lang/reflect/Method;");
                        //
                        mv.visitVarInsn(ALOAD, 1);
                        mv.visitLdcInsn(superMethodName);
                        mv.visitInsn(ICONST_1);
                        mv.visitTypeInsn(ANEWARRAY, "java/lang/Class");
                        mv.visitInsn(DUP);
                        mv.visitInsn(ICONST_0);
                        mv.visitLdcInsn(Type.getType(parameterType));
                        mv.visitInsn(AASTORE);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
                        mv.visitFieldInsn(PUTSTATIC, classDesc, methodProxyFieldName, "Ljava/lang/reflect/Method;");
                    }

                } else {
                    //TODO
                    System.out.println("多参数的");
                }
            }
        }
        //Object 方法 TODO 待补充 equals hashcode
        {
            mv.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
            mv.visitLdcInsn("toString");
            mv.visitInsn(ICONST_0);
            mv.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            mv.visitFieldInsn(PUTSTATIC, classDesc, "toStringMethod", "Ljava/lang/reflect/Method;");
            //
            mv.visitVarInsn(ALOAD, 1);
            mv.visitLdcInsn("__super__toString");
            mv.visitInsn(ICONST_0);
            mv.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            mv.visitFieldInsn(PUTSTATIC, classDesc, "toStringMethodProxy", "Ljava/lang/reflect/Method;");
        }
        mv.visitInsn(RETURN);
        mv.visitMaxs(6, 2);
        mv.visitEnd();
    }

    private void debugFile(byte[] bytes) {
        final String debugLocation = System.getProperty("com.wpp.debug.location");
        if (debugLocation != null) {
            FileUtil.saveBytesToFile(bytes, debugLocation + File.separator + classname.substring(classname.lastIndexOf(".") + 1) + ".class");
        }
    }

    private void addConstructor(ClassWriter classWriter) {
        //添加无参数构造函数
        {
            MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            // aload0
            // invokespecial
            // return
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, superDesc, "<init>", "()V", false);
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

    public ClassLoader getClassLoader() {
        if (classLoader == null) {
            classLoader = Enhancer.class.getClassLoader();
        }
        return classLoader;
    }

    private void addFields(ClassWriter classWriter) {
        FieldVisitor fv = classWriter.visitField(ACC_PRIVATE, "interceptor", "Lcom/wpp/Interceptor;", null, null);
        fv.visitEnd();
        fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "toStringMethod", "Ljava/lang/reflect/Method;", null, null);
        fv.visitEnd();
        fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "toStringMethodProxy", "Ljava/lang/reflect/Method;", null, null);
        fv.visitEnd();

        Class clazz = getSuperClass();

        for (Method method : clazz.getDeclaredMethods()) {
            String name = method.getName();
//            System.out.println("addFields: " + name);
            String methodFieldName = name + "Method";//TODO 暂不考虑方法重载问题
            String methodProxyFieldName = name + "MethodProxy";

            fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, methodFieldName, "Ljava/lang/reflect/Method;", null, null);
            fv.visitEnd();
            fv = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, methodProxyFieldName, "Ljava/lang/reflect/Method;", null, null);
            fv.visitEnd();
        }

    }

    private void addMethods(ClassWriter classWriter) {
        addSuperMethod(classWriter);
        addProxyMethod(classWriter);
        addObjectMethod(classWriter);
    }


    private void addProxyMethod(ClassWriter classWriter) {
        {
            MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "__proxy__setInterceptor", "(Lcom/wpp/Interceptor;)V", null, null);
            mv.visitCode();
            //aload0
            //aload1
            //putfield
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(PUTFIELD, classDesc, "interceptor", "Lcom/wpp/Interceptor;");
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
            mv.visitFieldInsn(GETFIELD, classDesc, "interceptor", "Lcom/wpp/Interceptor;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETSTATIC, classDesc, "toStringMethod", "Ljava/lang/reflect/Method;");
            mv.visitInsn(ACONST_NULL);
            mv.visitFieldInsn(GETSTATIC, classDesc, "toStringMethodProxy", "Ljava/lang/reflect/Method;");
            mv.visitMethodInsn(INVOKEINTERFACE, "com/wpp/Interceptor", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
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
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "toString", "()Ljava/lang/String;", false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
    }

    private void addSuperMethod(ClassWriter classWriter) {
        Class clazz = getSuperClass();
        for (Method method : clazz.getDeclaredMethods()) {
            //todo 方法签名判断

            String name = method.getName();
            String methodDesc = Type.getMethodDescriptor(method);
            String superMethodName = "__super__" + name;
            String methodFieldName = name + "Method";
            String methodProxyFieldName = name + "MethodProxy";
            Class<?> methodReturnType = method.getReturnType();
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 0) {
                {
                    MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, superMethodName, methodDesc, null, null);
                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKESPECIAL, superDesc, name, methodDesc, false);

                    if (isVoid(methodReturnType)) {
                        mv.visitInsn(RETURN);
                    } else {
                        mv.visitInsn(ARETURN);
                    }
                    mv.visitMaxs(1, 1);
                    mv.visitEnd();
                }
                {
                    MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, name, methodDesc, null, null);
                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, classDesc, "interceptor", "Lcom/wpp/Interceptor;");
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETSTATIC, classDesc, methodFieldName, "Ljava/lang/reflect/Method;");
                    mv.visitInsn(ACONST_NULL);
                    mv.visitFieldInsn(GETSTATIC, classDesc, methodProxyFieldName, "Ljava/lang/reflect/Method;");
                    mv.visitMethodInsn(INVOKEINTERFACE, "com/wpp/Interceptor", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
                    //TODO  返回值类型检查 各种返回值处理
                    if (isVoid(methodReturnType)) {
                        mv.visitInsn(RETURN);
                    } else {
                        mv.visitTypeInsn(CHECKCAST, Type.getInternalName(methodReturnType)); //类型转换
                        mv.visitInsn(ARETURN);
                    }
                    mv.visitMaxs(5, 1);
                    mv.visitEnd();
                }

            } else if (parameterTypes.length == 1) {
                {
                    MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, name, methodDesc, null, null);
                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, classDesc, "interceptor", "Lcom/wpp/Interceptor;");
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETSTATIC, classDesc, methodFieldName, "Ljava/lang/reflect/Method;");
                    mv.visitInsn(ICONST_1);
                    mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
                    mv.visitInsn(DUP);
                    mv.visitInsn(ICONST_0);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitInsn(AASTORE);
                    mv.visitFieldInsn(GETSTATIC, classDesc, methodProxyFieldName, "Ljava/lang/reflect/Method;");
                    mv.visitMethodInsn(INVOKEINTERFACE, "com/wpp/Interceptor", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
                    if (isVoid(methodReturnType)) {
                        mv.visitInsn(RETURN);
                    } else {
                        mv.visitTypeInsn(CHECKCAST, Type.getInternalName(methodReturnType)); //类型转换
                        mv.visitInsn(ARETURN);
                    }
                    mv.visitMaxs(7, 2);
                    mv.visitEnd();
                }
                {
                    MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, superMethodName, methodDesc, null, null);
                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKESPECIAL, superDesc, name, methodDesc, false);
                    //TODO 不同返回值类型
                    mv.visitInsn(ARETURN);
                    mv.visitMaxs(2, 2);
                    mv.visitEnd();
                }
            } else {
                //todo 多参数的调用
                System.out.println("todo 多参数的调用");
            }
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
        //todo 类、方法 final校验
    }

    private boolean isVoid(Class<?> methodReturnType) {
        return methodReturnType.isAssignableFrom(Void.class) || methodReturnType.isAssignableFrom(void.class);
    }
}
