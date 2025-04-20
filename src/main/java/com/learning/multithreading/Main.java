package com.learning.multithreading;

/**
 * @author vickyaa
 * @date 4/19/25
 * @file Main
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
//    System.out.println("Going inside main method" + Thread.currentThread().getName());
//    MultithreadingLearning runnableOjb = new MultithreadingLearning();
//    Thread thread = new Thread(runnableOjb);
//    thread.start();
//    System.out.println("Finish main method" + Thread.currentThread().getName());

    // creting producer thread using lambda expression
    SharedResource sharedResource = new SharedResource(10);
    Thread producerThread = new Thread(() -> {
      try {
        for (int i = 0; i < 10; i++) {
          sharedResource.produce(i);
          Thread.sleep(200);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    // creating consumer thread using lambda expression
    Thread consumerThread = new Thread(() -> {
      try {
        for (int i = 0; i <= 6 ; i++) {
          sharedResource.consume();
          Thread.sleep(200);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    producerThread.start();
    Thread.sleep(500);
    consumerThread.start();
  }
}
