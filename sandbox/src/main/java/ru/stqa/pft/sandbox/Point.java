package ru.stqa.pft.sandbox;

/**
 * Created by Юрий on 22.02.2016.
 */
public class Point {

  public double x;
  public double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double distance(Point p1, Point p2) {
    double dx = p1.x - p2.x;
    double dy = p1.y - p2.y;
    double sq = dx*dx + dy*dy;

    return Math.sqrt(sq);
  }

}
