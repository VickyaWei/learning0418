package com.learning.multithreading;

import java.util.concurrent.Callable;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file MyThread
 */
public class NamePrinter {

  static final String name = "vicky";
  static final Object lock = new Object();



  public static void main(String[] args) {
    Thread oddThread = new Thread(new Runnable() {
      @Override
      public void run() {
        for(int i = 0; i < name.length(); i+=2){
          synchronized (lock){
            System.out.print(name.charAt(i) + "\n" );
            try{
              lock.notify();
              Thread.sleep(1000);
              if(i + 2 < name.length()){
                lock.wait();
              }
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    });


    Thread evenThread = new Thread(new Runnable() {
      @Override
      public void run() {
        for(int i = 1; i < name.length(); i+=2){
          synchronized (lock){
            System.out.print(name.charAt(i) + "\n");
            try{
              lock.notify();
              Thread.sleep(1000);
              if(i + 2 < name.length()){
                lock.wait();
              }
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    });


    try{
      oddThread.start();
      evenThread.start();
      oddThread.join();
      evenThread.join();
    } catch (Exception e){

    }
  }

}
