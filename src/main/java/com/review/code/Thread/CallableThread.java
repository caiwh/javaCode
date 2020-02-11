package com.review.code.Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *
 *
 * Future接口，FutureTask是Future的唯一实现类，可以对Runnable、Callable任务的执行进行取消，查询是否完成，获取结果等
 * FutureTask同时继承了Runnable、Future，既可以作为Runnable被线程执行，也可以作为Future获取Callable的返回值x
 */

//1、创建Callable的实现类
class NumberThread implements Callable{
    private int num = 0;
    //2、实现call方法，将需要线程执行的方法写在call()中
    @Override
    public Object call() throws Exception { //返回100以内偶数的和
        for (int i =0 ;i<100;i++){
            if(i%2==0){
                System.out.println(i);
                num+=i;
            }
        }
        return num;
    }
}


public class CallableThread {
    public static void main(String[] args) {
        //3、创建Callable实现类的对象
        Callable numberThread = new NumberThread();
        //FutreTask 实现Runnable、Future接口
        //4、将callable实现类的对象作为参数，构建FutureTask
        FutureTask futureTask = new FutureTask(numberThread);
        //5、启动线程，线程启动都需要Thread.start()
        new Thread(futureTask).start();
        try {
            Object num = futureTask.get();//获取Callable.call()的执行结果
            System.out.println("总和为"+num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
