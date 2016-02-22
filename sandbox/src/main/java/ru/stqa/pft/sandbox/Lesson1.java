package ru.stqa.pft.sandbox;

/**
 * Created by Юрий on 22.02.2016.
 */
public class Lesson1 {

  public static void main(String[] args) {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(30, 40);

    System.out.println("Расстояние между точками p1(" + p1.x + "," + p1.y + ") и p2(" + p2.x + ", " + p2.y + ") равно " + p1.distance(p1, p2));

    System.out.println("Расстояние между точками p1(" + p1.x + "," + p1.y + ") и p2(" + p2.x + ", " + p2.y + ") равно " + p2.distance(p1, p2));

  }

}
