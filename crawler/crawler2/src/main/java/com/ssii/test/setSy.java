package com.ssii.test;

import java.util.*;

/**
 * Created by Administrator on 2017/9/1.
 */
public class setSy implements Runnable {
    @Override
    public void run() {

        List<Integer> list=new ArrayList<Integer>(10000);
        for(int i=0;i<10000;i++){
            list.add(i);
            System.out.println(Thread.currentThread().getName()+" "+i);
        }

    }

    public static void main(String[] args) {

        setSy ss1=new setSy();
        setSy ss2=new setSy();
        Thread th1=new Thread(ss1,"线程一");
        Thread th2=new Thread(ss1,"线程二");
        th1.start();
        th2.start();
    }
}
