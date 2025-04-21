package com.learning;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file MyThread
 */
public class MyThread implements Runnable {
//AtomicInteger atomicInteger = new AtomicInteger();
//Lock lock = new ReentrantLock() {
//
//}
String name = "vicky";

  @Override
  public void run() {

  }


  public static void main(String[] args) {
    MyThread myThread = new MyThread();
    Thread thread = new Thread(myThread);
    thread.start();
    ExecutorService threadPool = Executors.newSingleThreadExecutor();

    threadPool.submit(myThread);

  }

}
