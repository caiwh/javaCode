package com.review.code.Thread;


/**
 * 线程通信的经典例题：生产者/消费者问题
 *
 * 生产者(product)将产品交给店员(clerk),消费者(consumer)从店员处取走产品，
 * 店员一次只能持有固定数量的产品(比如:20），如果生产者试图生产更多的产品，店员
 * 会较叫生产者挺一下，如果店中有空位放产品再通知生产者继续生产；如果店中没产品了
 * 店员会告诉消费者等一下，如果店中有产品了在通知消费者来取走产品
 *
 * 存在线程安全问题，线程通信问题
 */
//店员
class Clerk {
    private int currentCount = 0;//当前产品数量
    private int total = 0;//已生产的产品总数量
    //同步监视器是clerk对象
    public synchronized void produceProduct()  {
        if(currentCount < 20 && total <200){
            currentCount++;
            total++;
            System.out.println(Thread.currentThread().getName()+":开始生产第"+currentCount+"个产品,total："+total);
            notify();//生产一个就可以唤醒消费者
        }else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void consumeProduct() {
        if(currentCount > 0){
            System.out.println(Thread.currentThread().getName()+":开始消费第"+currentCount+"个产品");
            currentCount--;
            notify();//消费一个就可以唤醒生产者
        }else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//生产者
class Productor extends Thread{
    private Clerk clerk = null;
    public Productor(Clerk clerk) {
        this.clerk=clerk;
    }

    @Override
    public void run() {
        System.out.println(getName()+":开始生产产品");
        while (true){
            try {
                sleep(10);
                clerk.produceProduct();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//消费者
class Consumer extends Thread{
    private Clerk clerk = null;
    public Consumer(Clerk clerk) {
        this.clerk=clerk;
    }

    @Override
    public void run() {
        System.out.println(getName()+":开始消费产品");
        while (true){
            try {
                sleep(10);
                clerk.consumeProduct();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


public class ProductTest {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Thread productor = new Productor(clerk);
        Thread productor1 = new Productor(clerk);
        Thread consumer = new Consumer(clerk);
        Thread consumer1 = new Consumer(clerk);

        productor.setName("生产者");
        productor1.setName("生产者1");
        consumer.setName("消费者");
        consumer1.setName("消费者1");

        productor.start();
        productor1.start();
        consumer.start();
        consumer1.start();
    }
}

