package com.review.code.Thread;


import java.util.concurrent.*;

/**
 * 创建线程的方式：线程池
 *
 * 背景：经常创建和销毁使用量特别大的资源，比如并发情况下的线程，对性能影响特别大
 * 思路：提前创建好多个线程，放在线程池中，使用时从池中获取，使用完成放回池中，可以避免重复创建和销毁，实现线程重复利用。
 *
 * 好处：
 *  提高响应速度
 *  降低资源消耗
 *  便于线程管理
 */
class NumberCallable implements Callable {
    int num = 0 ;
    @Override
    public Object call() throws Exception {
        for (int i=0;i<100;i++){
            num+=i;
        }
        return num;
    }
}

class NumberRunnable implements Runnable {
    int num = 0 ;

    @Override
    public void run() {
        num+=2;
        System.out.println("Runnable:"+num);
    }
}

class NumberRunnable2 implements Runnable {
    int num = 0 ;

    @Override
    public void run() {
        num+=3;
        System.out.println("Runnable2:"+num);
    }
}

/**
 * ExecutorService:线程池接口，常见子类ThreadPoolExecutor
 *
 * Executors：工具类、线程池的工厂类，用于创建并返回不同类型的线程池。
 */
public class ThreadPool {
    public static void main(String[] args) {
        //创建线程池
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        //设置线程池属性
//        executorService.setCorePoolSize();//设置核心池的大小
//        executorService.setKeepAliveTime();//设置线程没有任务时最多保持多长时间会终止
//        executorService.setMaximumPoolSize();//设置最大线程数
//        executorService.setRejectedExecutionHandler();
//        executorService.setThreadFactory();

        //执行线程，适用于Runnable
        executorService.execute(new NumberRunnable());
        executorService.execute(new NumberRunnable2());


        //执行线程，适用于Callable
        try {
            Future future = executorService.submit(new NumberCallable());//执行线程，适用于Callable
            System.out.println("Callable.call()执行结果："+future.get());//获取Callable.call()的结果并输出
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //关闭线程池
        executorService.shutdownNow();
    }
}
