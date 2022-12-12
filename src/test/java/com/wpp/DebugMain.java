package com.wpp;

import org.objectweb.asm.util.ASMifier;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DebugMain {
    public static void main(String[] args) throws Throwable {
        printBase$TOY$1();
    }

    private static void printBase$TOY$1() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        ASMifier.main(new String[]{"com.wpp.Base$TOY$1"});

        final FileOutputStream out = new FileOutputStream("/Users/wpp/test/toy-cglib/target/Base$TOY$1Dump.java");
        PrintWriter printWriter = new PrintWriter(out, true);
        Method main = ASMifier.class.getDeclaredMethod("main", String[].class, PrintWriter.class, PrintWriter.class);
        main.setAccessible(true);
        main.invoke(null, new String[]{"com.wpp.Base$TOY$1"}, printWriter, printWriter);

    }
}