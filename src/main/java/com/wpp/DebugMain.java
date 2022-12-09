package com.wpp;

import org.objectweb.asm.util.ASMifier;

import java.io.IOException;

public class DebugMain {
    public static void main(String[] args) throws Throwable {
        printBase$TOY$1();
    }

    private static void printBase$TOY$1() throws IOException {
        ASMifier.main(new String[]{"com.wpp.Base$TOY$1"});
    }
}