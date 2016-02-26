package ru.stqa.pft.sandbox;

/**
 * Created by Юрий on 22.02.2016.
 */
public class Square {

  public double l;

  public Square (double l) {

    this.l = l;
  }

  public double area() {
    return this.l * this.l;
  }

  public double perimeter() {
    return this.l  * 4;
  }

}
