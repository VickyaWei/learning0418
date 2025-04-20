package com.learning.multithreading;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author vickyaa
 * @date 4/19/25
 * @file SharedResource
 */
public class SharedResource {
  private Queue<Integer> sharedBuffer;
  private int bufferSize;

  public SharedResource(int bufferSize) {
    sharedBuffer = new LinkedList<>();
    this.bufferSize = bufferSize;
  }

  public  synchronized void produce(int item) throws Exception {
    // If buffer is full, wait ufor the consumer to consume the item
    while (sharedBuffer.size() == bufferSize) {
      System.out.println("Buffer is full, waiting for consumer to consume the item");
      wait();
    }
    sharedBuffer.add(item);
    System.out.println("Produced item: " + item);
    // Notify the consumer that the item has been produced
    notifyAll();
  }

  public synchronized int consume() throws Exception {
    // If buffer is empty, wait for the producer to produce the item
    while(sharedBuffer.isEmpty()) {
      System.out.println("Buffer is empty, waiting for producer to produce the item");
      wait();
    }
    int item = sharedBuffer.poll();
    System.out.println("Consumed item: " + item);
    notify();
    return item;
  }


}
