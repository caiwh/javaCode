package com.review.code.Thread;

/**
 * 线程间的通信
 * 例子：两个线程交替打印100以内的数字
 *
 * wait() 执行wait()，当前线程进入阻塞，并释放同步监视器
 * notify() 执行notify()，将唤醒被wait的一个线程，如果有多个线程被wait，就唤醒优先级高的线程
 * notifyAll() 执行notifyAll()，唤醒所有被wait()的线程
 * 以上三个方法都是Object的方法，并且调用者需要是同步代码块或同步方法中的同步监视器，所以三个方法必须在同步代码块或同步方法中使用。
 */
public class ThreadConnection {
    public static void main(String[] args) {
        Runnable number = new Number();
        Thread t1 = new Thread(number);
        Thread t2 = new Thread(number);

        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        t2.start();
    }
}

class Number implements Runnable {
    int num = 1;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                //执行notify()，将唤醒被wait的一个线程，如果有多个线程被wait，就唤醒优先级高的线程
                //可以写成 this.notify()  this指main线程中的 number对象
                notify();
                if (num <= 100) {
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    num++;
                } else {
                    break;
                }
                try {
                    wait();//执行wait()，当前线程进入阻塞，并释放同步监视器
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
