package com.wpp;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wpp
 * @desc
 * @see
 * @since 2022/12/9
 */
public class Bese2 {
    public String hello(String msg) {
        System.out.println("Base2 hello: " + msg);
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(200));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "hello" + msg;
    }

}
