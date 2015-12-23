package com.javagoo.sajdi;

/**
 * Created by jiaojianfeng on 15/12/23.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        System.out.println("lang");
        String a = "a";
        String b = "b";
        while (true) {
            Thread.sleep(100000);
            System.out.println(a + b);
        }
    }
}
