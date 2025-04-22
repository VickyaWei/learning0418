package com.learning.collection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExercise {

  public static void main(String[] args) {

    Map<String, Integer> map = new ConcurrentHashMap<>();
    map.put("a", 1);
    map.put("a", 2);
    System.out.println(map);
  }
}
