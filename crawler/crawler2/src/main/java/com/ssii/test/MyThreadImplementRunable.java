package com.ssii.test;

/**
 * 实现runnable接口创建线程
 * Created by jeker on 17-6-25.
 */
public class MyThreadImplementRunable implements  Runnable {

    private int i;

    //实现Runnable接口的run()方法来创建用户线程的执行体
    @Override
    public void run() {
        //迭代输出0到100的数字
        for (;i<1000;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }

    //main主线程
    public static void main(String[] args){
                //创建用户线程类实例
                MyThreadImplementRunable myThreadImplementRunable = new MyThreadImplementRunable();
                //以用户线程类的实例作为Thread的target来创建thrad对象,并制定线程名称,即线程对象
                new Thread(myThreadImplementRunable,"线程1").start();
                new Thread(myThreadImplementRunable,"线程2").start();


    }
}
