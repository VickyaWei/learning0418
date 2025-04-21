package com.learning.multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file MyThread
 */
public class MyThread implements Runnable, Callable {
//AtomicInteger atomicInteger = new AtomicInteger();
//Lock lock = new ReentrantLock() {
//
//}
String name = "vicky";

  @Override
  public void run() {

  }

  @Override
  public Object call() throws Exception {
    return null;
  }


  public static void main(String[] args) {
    MyThread myThread = new MyThread();
    Thread thread = new Thread(myThread);
    thread.start();
    ExecutorService threadPool = Executors.newSingleThreadExecutor();

    //threadPool.submit(myThread);

  }
}
