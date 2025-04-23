package com.learning.collection;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExercise {


  public static void main(String[] args) {
    List list = new CopyOnWriteArrayList();

    list.add("a");
    list.add("b");
    list.add(1, "c");

    System.out.println(list);


    Iterator iterator = list.iterator();
    while(iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }

}
