package com.learning.multithreading;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MyFuture {
    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,
                1,
                1,
                TimeUnit.HOURS,
                new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        // new thread will be created and it will perfrom the tasks
        Future<?> futureObj = poolExecutor.submit(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("this is the task, which thread will execute");
            } catch (Exception e) {

            }


        });

        //caller is checking the status of the thread is created
        System.out.println(futureObj.isDone());

        try {
            futureObj.get(2, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("TimeoutException happened");
        } catch (Exception e) {

        }

        try {
            futureObj.get();
        } catch (Exception e) {

        }

        System.out.println("is Done:" + futureObj.isDone());
        System.out.println("is Cancelled: " + futureObj.isCancelled());


        // runnable
        // we need the future object, but we don't need to return anything, so ? can be anything
        Future<?> futureObj1 = poolExecutor.submit(() -> {
            System.out.println("Task1 with Runnable");
        });

        try{
            Object object = futureObj1.get();
            System.out.println(object == null);
        } catch (Exception e) {

        }


        List<Integer> output = new ArrayList<>();
        Future<List<Integer>> futureObject = poolExecutor.submit(new MyRunnable(output), output);

        try{
            futureObject.get();
            System.out.println(output.get(0));


            List<Integer> result = futureObject.get();
            System.out.println(result.get(0));
        } catch (Exception e){

        }


    }



}
