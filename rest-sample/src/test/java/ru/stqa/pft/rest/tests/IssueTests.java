package ru.stqa.pft.rest.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by Юрий on 17.04.2016.
 */
public class IssueTests extends TestBase {

  @BeforeTest
  public void preConditions() throws IOException {
    skipIfNotFixed(9);
    skipIfNotFixed(2);
  }

  @Test
  public void testCheckIssue() {
    System.out.println("Начинаем тест...");
  }
}
