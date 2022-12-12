package com.wpp;

import org.objectweb.asm.*;

public class Base$TOY$1Dump implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        RecordComponentVisitor recordComponentVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, "com/wpp/Base$TOY$1", null, "com/wpp/Base", new String[]{"com/wpp/ToyProxy"});

        classWriter.visitSource("Base$TOY$1.java", null);

        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "voidnoargMethod", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "voidnoargMethodProxy", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "noargMethod", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "noargMethodProxy", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "workMethod", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "workMethodProxy", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "toStringMethod", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "toStringMethodProxy", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "hashCodeMethod", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "hashCodeMethodProxy", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "equalsMethod", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "equalsMethodProxy", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE, "interceptor", "Lcom/wpp/Interceptor;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(10, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/wpp/Base", "<init>", "()V", false);
            methodVisitor.visitInsn(RETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label1, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "voidnoarg", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(63, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "com/wpp/Base$TOY$1", "interceptor", "Lcom/wpp/Interceptor;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "voidnoargMethod", "Ljava/lang/reflect/Method;");
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "voidnoargMethodProxy", "Ljava/lang/reflect/Method;");
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "com/wpp/Interceptor", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;", true);
            methodVisitor.visitInsn(POP);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(64, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label2, 0);
            methodVisitor.visitMaxs(5, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "__super__voidnoarg", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(67, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/wpp/Base", "voidnoarg", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(68, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label2, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "__super__noarg", "()Ljava/lang/String;", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(71, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/wpp/Base", "noarg", "()Ljava/lang/String;", false);
            methodVisitor.visitInsn(ARETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label1, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "noarg", "()Ljava/lang/String;", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(76, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "com/wpp/Base$TOY$1", "interceptor", "Lcom/wpp/Interceptor;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "noargMethod", "Ljava/lang/reflect/Method;");
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "noargMethodProxy", "Ljava/lang/reflect/Method;");
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "com/wpp/Interceptor", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;", true);
            methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/String");
            methodVisitor.visitInsn(ARETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label1, 0);
            methodVisitor.visitMaxs(5, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "__super__work", "(Ljava/lang/String;)Ljava/lang/String;", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(80, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/wpp/Base", "work", "(Ljava/lang/String;)Ljava/lang/String;", false);
            methodVisitor.visitInsn(ARETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label1, 0);
            methodVisitor.visitLocalVariable("arg", "Ljava/lang/String;", null, label0, label1, 1);
            methodVisitor.visitMaxs(2, 2);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "work", "(Ljava/lang/String;)Ljava/lang/String;", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(85, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "com/wpp/Base$TOY$1", "interceptor", "Lcom/wpp/Interceptor;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "workMethod", "Ljava/lang/reflect/Method;");
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Object");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitInsn(AASTORE);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "workMethodProxy", "Ljava/lang/reflect/Method;");
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "com/wpp/Interceptor", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;", true);
            methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/String");
            methodVisitor.visitInsn(ARETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label1, 0);
            methodVisitor.visitLocalVariable("arg", "Ljava/lang/String;", null, label0, label1, 1);
            methodVisitor.visitMaxs(7, 2);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "__super__toString", "()Ljava/lang/String;", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(89, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "toString", "()Ljava/lang/String;", false);
            methodVisitor.visitInsn(ARETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label1, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(94, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "com/wpp/Base$TOY$1", "interceptor", "Lcom/wpp/Interceptor;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "toStringMethod", "Ljava/lang/reflect/Method;");
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "toStringMethodProxy", "Ljava/lang/reflect/Method;");
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "com/wpp/Interceptor", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;", true);
            methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/String");
            methodVisitor.visitInsn(ARETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label1, 0);
            methodVisitor.visitMaxs(5, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "__proxy__setInterceptor", "(Lcom/wpp/Interceptor;)V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(98, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitFieldInsn(PUTFIELD, "com/wpp/Base$TOY$1", "interceptor", "Lcom/wpp/Interceptor;");
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(99, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label2, 0);
            methodVisitor.visitLocalVariable("interceptor", "Lcom/wpp/Interceptor;", null, label0, label2, 1);
            methodVisitor.visitMaxs(2, 2);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "hashCode", "()I", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(104, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "com/wpp/Base$TOY$1", "interceptor", "Lcom/wpp/Interceptor;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "hashCodeMethod", "Ljava/lang/reflect/Method;");
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "hashCodeMethodProxy", "Ljava/lang/reflect/Method;");
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "com/wpp/Interceptor", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;", true);
            methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            methodVisitor.visitInsn(IRETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label1, 0);
            methodVisitor.visitMaxs(5, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "__super__hashCode", "()I", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(108, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "hashCode", "()I", false);
            methodVisitor.visitInsn(IRETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label1, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "equals", "(Ljava/lang/Object;)Z", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(114, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "com/wpp/Base$TOY$1", "interceptor", "Lcom/wpp/Interceptor;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "equalsMethod", "Ljava/lang/reflect/Method;");
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/wpp/Base$TOY$1", "equalsMethodProxy", "Ljava/lang/reflect/Method;");
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "com/wpp/Interceptor", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;", true);
            methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
            methodVisitor.visitInsn(IRETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label1, 0);
            methodVisitor.visitLocalVariable("obj", "Ljava/lang/Object;", null, label0, label1, 1);
            methodVisitor.visitMaxs(5, 2);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "__super__equals", "(Ljava/lang/Object;)Z", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(118, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z", false);
            methodVisitor.visitInsn(IRETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lcom/wpp/Base$TOY$1;", null, label0, label1, 0);
            methodVisitor.visitLocalVariable("obj", "Ljava/lang/Object;", null, label0, label1, 1);
            methodVisitor.visitMaxs(2, 2);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            Label label1 = new Label();
            Label label2 = new Label();
            methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/Exception");
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(35, label0);
            methodVisitor.visitLdcInsn("com.wpp.Base");
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
            methodVisitor.visitVarInsn(ASTORE, 0);
            Label label3 = new Label();
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLineNumber(36, label3);
            methodVisitor.visitLdcInsn("com.wpp.Base$TOY$1");
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
            methodVisitor.visitVarInsn(ASTORE, 1);
            Label label4 = new Label();
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLineNumber(37, label4);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitLdcInsn("work");
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLdcInsn(Type.getType("Ljava/lang/String;"));
            methodVisitor.visitInsn(AASTORE);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "workMethod", "Ljava/lang/reflect/Method;");
            Label label5 = new Label();
            methodVisitor.visitLabel(label5);
            methodVisitor.visitLineNumber(38, label5);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitLdcInsn("__super__work");
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLdcInsn(Type.getType("Ljava/lang/String;"));
            methodVisitor.visitInsn(AASTORE);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "workMethodProxy", "Ljava/lang/reflect/Method;");
            Label label6 = new Label();
            methodVisitor.visitLabel(label6);
            methodVisitor.visitLineNumber(40, label6);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitLdcInsn("noarg");
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "noargMethod", "Ljava/lang/reflect/Method;");
            Label label7 = new Label();
            methodVisitor.visitLabel(label7);
            methodVisitor.visitLineNumber(41, label7);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitLdcInsn("__super__noarg");
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "noargMethodProxy", "Ljava/lang/reflect/Method;");
            Label label8 = new Label();
            methodVisitor.visitLabel(label8);
            methodVisitor.visitLineNumber(43, label8);
            methodVisitor.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
            methodVisitor.visitLdcInsn("toString");
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "toStringMethod", "Ljava/lang/reflect/Method;");
            Label label9 = new Label();
            methodVisitor.visitLabel(label9);
            methodVisitor.visitLineNumber(44, label9);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitLdcInsn("__super__toString");
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "toStringMethodProxy", "Ljava/lang/reflect/Method;");
            Label label10 = new Label();
            methodVisitor.visitLabel(label10);
            methodVisitor.visitLineNumber(46, label10);
            methodVisitor.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
            methodVisitor.visitLdcInsn("hashCode");
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "hashCodeMethod", "Ljava/lang/reflect/Method;");
            Label label11 = new Label();
            methodVisitor.visitLabel(label11);
            methodVisitor.visitLineNumber(47, label11);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitLdcInsn("__super__hashCode");
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "hashCodeMethodProxy", "Ljava/lang/reflect/Method;");
            Label label12 = new Label();
            methodVisitor.visitLabel(label12);
            methodVisitor.visitLineNumber(49, label12);
            methodVisitor.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
            methodVisitor.visitLdcInsn("equals");
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
            methodVisitor.visitInsn(AASTORE);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "equalsMethod", "Ljava/lang/reflect/Method;");
            Label label13 = new Label();
            methodVisitor.visitLabel(label13);
            methodVisitor.visitLineNumber(50, label13);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitLdcInsn("__super__equals");
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
            methodVisitor.visitInsn(AASTORE);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "equalsMethodProxy", "Ljava/lang/reflect/Method;");
            Label label14 = new Label();
            methodVisitor.visitLabel(label14);
            methodVisitor.visitLineNumber(52, label14);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitLdcInsn("voidnoarg");
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "voidnoargMethod", "Ljava/lang/reflect/Method;");
            Label label15 = new Label();
            methodVisitor.visitLabel(label15);
            methodVisitor.visitLineNumber(53, label15);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitLdcInsn("__super__voidnoarg");
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/wpp/Base$TOY$1", "voidnoargMethodProxy", "Ljava/lang/reflect/Method;");
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(56, label1);
            Label label16 = new Label();
            methodVisitor.visitJumpInsn(GOTO, label16);
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(55, label2);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});
            methodVisitor.visitVarInsn(ASTORE, 0);
            methodVisitor.visitLabel(label16);
            methodVisitor.visitLineNumber(57, label16);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitLocalVariable("c1", "Ljava/lang/Class;", null, label3, label1, 0);
            methodVisitor.visitLocalVariable("c2", "Ljava/lang/Class;", null, label4, label1, 1);
            methodVisitor.visitMaxs(6, 2);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
