package com.learning.collection;

import java.util.HashMap;
import java.util.Map;

public class HashMapExercise {

  public static void main(String[] args) {

    Map<String, Integer> map = new HashMap<>();

    map.put("a", 1);
    map.put("b", 2);
    map.putIfAbsent("a", 3);

   System.out.println(map);


    Map<String, Integer> map2 = new HashMap<>();
    map2.put("c", 2);
    map2.putIfAbsent("d", 3);
    map.putAll(map2);
    System.out.println(map);

    System.out.println(map.get("a"));
    int res = map.getOrDefault("e", 2);
    System.out.println(res);


  }
}
