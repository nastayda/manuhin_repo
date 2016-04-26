package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandbox.Point;

/**
 * Created by Юрий on 26.02.2016.
 */
public class PointTests {

  @Test
  public void TestDistance() {

    Point p1 = new Point(0, 0);
    Point p2 = new Point(30, 40);

    assert p1.distance(p1, p2) == 50.0;
    Assert.assertEquals(p1.distance(p1, p2), 50.0);
    Assert.assertEquals(p2.distance(p2, p1), 50);
  }
}
