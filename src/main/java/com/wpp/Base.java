package com.wpp;

/**
 * Function:
 * Author: wpp
 * Date: 2022/12/8 21:22
 */
public class Base {
    public void voidnoarg() {
        System.out.println("voidnoarg");
    }
    public String noarg() {
        return "noarg";
    }
// TODO
//    public String twoarg(String arg1, String arg2) {
//        return arg1 + arg2;
//    }

    public String work(String arg) {
        String result = "Base:work:" + arg;
        return result;
    }
}
