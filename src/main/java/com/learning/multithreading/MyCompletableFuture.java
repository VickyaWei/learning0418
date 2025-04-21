package com.learning.multithreading;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyCompletableFuture {

    public static void main(String[] args) {
        try {
            ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,
                    1,
                    1,
                    TimeUnit.HOURS,
                    new ArrayBlockingQueue<>(10),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());

            CompletableFuture<String> asyncTask = CompletableFuture.supplyAsync(() -> {
                return "task completed";
            }, poolExecutor);

            System.out.println(asyncTask.get());
        } catch(Exception e) {

        }
    }

}
