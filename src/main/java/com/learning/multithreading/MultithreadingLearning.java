package com.learning.multithreading;

/**
 * @author vickyaa
 * @date 4/19/25
 * @file MultithreadingLearning
 */
public class MultithreadingLearning implements Runnable{

  public void run() {
    System.out.println("code executed by thread: " + Thread.currentThread().getName());
  }

}
