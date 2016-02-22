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

    Point p1 = new Point(0, 0);
    Point p2 = new Point(3, 4);

    System.out.println("Расстояние между точками p1(" + p1.x + "," + p1.y + ") и p2(" + p2.x + ", " + p2.y + ") равно " + distance(p1, p2));


  }

  public static void hello(String someb) {

    System.out.println("Hello, " + someb + "!!!");
  }


  public static double distance(Point p1, Point p2) {
    double dx = p1.x - p2.x;
    double dy = p1.y - p2.y;
    double sq = dx*dx + dy*dy;

    return Math.sqrt(sq);
  }
}