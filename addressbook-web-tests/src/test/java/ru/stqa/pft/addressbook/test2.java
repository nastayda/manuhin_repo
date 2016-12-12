package ru.stqa.pft.addressbook;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;


public class test2 {
  FirefoxDriver driver;

  @BeforeMethod
  public void setUp() throws Exception {
    //Какая то хня
    System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\GekoDriver\\geckodriver.exe");
    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    capabilities.setCapability("marionette", true);
    WebDriver driver = new FirefoxDriver(capabilities);
    //Какая то хня

    this.driver = new FirefoxDriver();
    this.driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void GroupCreationTests() {
    driver.get("http://localhost/addressbook/");
    driver.findElement(By.name("user")).click();
    driver.findElement(By.name("user")).sendKeys("\\undefined");
    driver.findElement(By.name("pass")).click();
    driver.findElement(By.name("pass")).sendKeys("\\undefined");
    driver.findElement(By.id("LoginForm")).click();
    driver.findElement(By.name("user")).click();
    driver.findElement(By.name("user")).click();
    driver.findElement(By.name("user")).clear();
    driver.findElement(By.name("user")).sendKeys("admin");
    driver.findElement(By.id("LoginForm")).click();
    driver.findElement(By.name("pass")).click();
    driver.findElement(By.name("pass")).clear();
    driver.findElement(By.name("pass")).sendKeys("secret");
    driver.findElement(By.name("pass")).click();
    driver.findElement(By.name("pass")).clear();
    driver.findElement(By.name("pass")).sendKeys("secret");
    driver.findElement(By.xpath("//form[@id='LoginForm']/input[3]")).click();
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
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
