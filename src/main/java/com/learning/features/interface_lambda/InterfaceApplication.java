package com.learning.features.interface_lambda;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file InterfaceApplication
 */
public class InterfaceApplication {

  public static void main(String[] args) {

    //
    // lambda expression
    Calculator additionCalculator = (a, b) -> a + b;
    int result = additionCalculator.calculate(5, 6);
    System.out.println("Result: " + result);

    Calculator subtractionCalculator = (a, b) -> a - b;
    int result2 = subtractionCalculator.calculate(5, 6);
    System.out.println("Result: " + result2);
  }
}
