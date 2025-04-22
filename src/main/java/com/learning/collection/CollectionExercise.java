package com.learning.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionExercise {

  public static void main(String[] args) {
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 4, 5, 6, 7);

    System.out.println(Collections.min(list));

    System.out.println(Collections.max(list));
    System.out.println(Collections.frequency(list, 5));
  }

}
