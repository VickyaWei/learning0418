package com.learning.features.stream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file Stream
 */
public class Stream {

  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    List<String> stringList = list.stream().filter(i -> i > 5).map(i -> i.toString()).toList();
  }
}
