package ru.stqa.pft.sandbox;

public class Program1 {

  public static void main(String[] args) {
    hello("world");
    hello("user");
    hello("Yuriy");

    double len = 7;

    System.out.println("Площадь квадрата со стороной " + len + " равна " + area(len));

    double a = 7;
    double b = 9;

    System.out.println("Площадь прямоугольника со сторонами " + a + " и " + b + " равна " + area(a, b));

  }

  public static void hello(String someb) {

    System.out.println("Hello, " + someb + "!!!");
  }

  public static double area(double len) {
    return len*len;
  }

  public static double area(double a, double b) {
    return a*b;
  }
}