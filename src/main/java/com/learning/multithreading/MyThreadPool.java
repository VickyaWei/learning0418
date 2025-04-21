package com.learning.multithreading;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.*;

public class MyThreadPool {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                4,
                10,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(2),
                new CustomThreadFactory(),
                new CustomerRejectHandler()
        );
        executor.allowCoreThreadTimeOut(true);
        for (int i = 1; i <= 7; i++) {
            executor.submit(() -> {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {

                }
                System.out.println("Task processed by" + Thread.currentThread().getName());
            });
        }
        executor.shutdown();
    }
}

class CustomThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.setDaemon(false);
        return thread;
    }
}

class CustomerRejectHandler implements ThreadPoolExecutor.RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor threadPoolExecutor) {
        System.out.println("Task rejected" + r.toString());
    }
}
