package ru.stqa.pft.sandbox;

public class Program1 {

  public static void main(String[] args) {
    hello("world");
    hello("user");
    hello("Yuriy");

    Square s = new Square(9);

    System.out.println("Площадь квадрата со стороной " + s.l + " равна " + s.area());

    Rectangle r = new Rectangle(7, 9);

    System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " равна " + r.area());

    System.out.println("Периметр прямоугольника со сторонами " + r.a + " и " + r.b + " равен " + r.perimeter());

    System.out.println("Периметр квадрата со стороной " + s.l + " равен " + s.perimeter());

    Point p1 = new Point(0, 0);
    Point p2 = new Point(3, 4);

    System.out.println("Расстояние между точками p1(" + p1.x + "," + p1.y + ") и p2(" + p2.x + ", " + p2.y + ") равно " + p1.distance(p1, p2));

    System.out.println("Расстояние между точками p1(" + p1.x + "," + p1.y + ") и p2(" + p2.x + ", " + p2.y + ") равно " + p2.distance(p1, p2));

  }

  public static void hello(String someb) {

    System.out.println("Hello, " + someb + "!!!");
  }
}