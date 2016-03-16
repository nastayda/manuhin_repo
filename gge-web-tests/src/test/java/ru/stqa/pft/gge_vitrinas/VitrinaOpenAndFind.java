package test.java.ru.stqa.pft.gge_vitrinas;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class VitrinaOpenAndFind {
    FirefoxDriver wd;
    
    @BeforeMethod
    public void setUp() throws Exception {
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    
    @Test
    public void testVitrinaOpenAndFind() throws Exception {
        wd.get("https://vm-082-as-gge.mdi.ru/auth/login.action");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("username")).click();
        wd.findElement(By.id("username")).clear();
        wd.findElement(By.id("username")).sendKeys("galactica_admin1");
        wd.findElement(By.id("password")).click();
        wd.findElement(By.id("password")).sendKeys("21");
        wd.findElement(By.id("submitBtn")).click();

        waitElement(By.linkText("Экспертиза"));
//        Thread.sleep(10000);
        wd.findElement(By.linkText("Экспертиза")).click();

        waitElement(By.id("0E0CADA75DC448959686DFC63BD2178A"));

//        Thread.sleep(10000);

        wd.findElement(By.id("0E0CADA75DC448959686DFC63BD2178A")).click();

        waitElement(By.xpath("//div[@class='form']/div[2]/span/input"));

        wd.findElement(By.xpath("//div[@class='form']/div[2]/span/input")).click();
        wd.findElement(By.xpath("//div[@class='form']/div[2]/span/input")).clear();
        wd.findElement(By.xpath("//div[@class='form']/div[2]/span/input")).sendKeys("ав");

        waitElement(By.xpath("//div[@class='form']/input"));

        wd.findElement(By.xpath("//div[@class='form']/input")).click();

        Thread.sleep(10000);

    }

  private void waitElement(By locator) throws InterruptedException {
    for (int second = 0;; second++) {
      if (second >= 60) fail("timeout");
      try { if (isElementPresent(locator)) break; } catch (Exception e) {}
      Thread.sleep(1000);
    }
  }

  protected boolean isElementPresent(By locator) {
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  @AfterMethod
    public void tearDown() {
        wd.quit();
    }
    
    public static boolean isAlertPresent(FirefoxDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
