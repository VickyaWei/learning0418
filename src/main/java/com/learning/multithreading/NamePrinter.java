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
  static int currentThread = 0;

  public static void main(String[] args) {
    Thread oddThread = new Thread(() -> {
      for (int i = 0; i < name.length(); i += 3) {
        synchronized (lock) {
          while (currentThread != 0) {
            try {
              lock.wait();
            } catch (Exception e) {

            }
          }

          if (i < name.length()) {
            System.out.print("thread 1" + name.charAt(i) + "\n");
          }

          currentThread = 1;

          try {
            lock.notifyAll();
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }

//          try{
//            lock.notify();
//            Thread.sleep(1000);
//            if(i + 2 < name.length()){
//              lock.wait();
//            }
//          } catch (InterruptedException e) {
//            e.printStackTrace();
//          }
        }
      }
    });

    Thread evenThread = new Thread(() -> {
      for (int i = 1; i < name.length(); i += 3) {
        synchronized (lock) {
          while (currentThread != 1) {
            try {
              lock.wait();
            } catch (Exception e) {

            }
          }

          if (i < name.length()) {
            System.out.print("thread 2" + name.charAt(i) + "\n");
          }

          currentThread = 2;

          try {
            lock.notifyAll();
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }

//          try{
//            lock.notify();
//            Thread.sleep(1000);
//            if(i + 2 < name.length()){
//              lock.wait();
//            }
//          } catch (InterruptedException e) {
//            e.printStackTrace();
//          }
        }

//        synchronized (lock){
//          System.out.print(name.charAt(i) + "\n");
//          try{
//            lock.notify();
//            Thread.sleep(1000);
//            if(i + 2 < name.length()){
//              lock.wait();
//            }
//          } catch (InterruptedException e) {
//            e.printStackTrace();
//          }
//        }
      }
    });

    Thread thirdThread = new Thread(() -> {
      for (int i = 2; i < name.length(); i += 3) {
        synchronized (lock) {
          while (currentThread != 2) {
            try {
              lock.wait();
            } catch (Exception e) {

            }
          }

          if (i < name.length()) {
            System.out.print("thread 3" + name.charAt(i) + "\n");
          }

          currentThread = 0;

          try {
            lock.notifyAll();
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }

//          try{
//            lock.notify();
//            Thread.sleep(1000);
//            if(i + 2 < name.length()){
//              lock.wait();
//            }
//          } catch (InterruptedException e) {
//            e.printStackTrace();
//          }
        }
//        synchronized (lock){
//          System.out.print(name.charAt(i) + "\n");
//          try{
//            lock.notify();
//            Thread.sleep(1000);
//            if(i + 2 < name.length()){
//              lock.wait();
//            }
//          } catch (InterruptedException e) {
//            e.printStackTrace();
//          }
//        }
      }
    });

    try {
      oddThread.start();
      evenThread.start();
      thirdThread.start();
      oddThread.join();
      evenThread.join();
      thirdThread.join();
    } catch (Exception e) {

    }
  }

}
