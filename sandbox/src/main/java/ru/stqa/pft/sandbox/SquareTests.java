package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandbox.Point;
import ru.stqa.pft.sandbox.Square;


/**
 * Created by Юрий on 26.02.2016.
 */
public class SquareTests {

  @Test
  public void testArea() {

    Square s = new Square(5);
    Assert.assertEquals(s.area(), 25.0);

  }
}
