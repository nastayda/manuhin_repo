package ru.stqa.pft.rzd.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class RzdTests2 {
    FirefoxDriver wd;
    
    @BeforeMethod
    public void setUp() throws Exception {
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }
    
    @Test
    public void RzdTests2() {
        wd.get("http://rzd.ru/main/public/ru");
        wd.findElement(By.id("name0")).click();
        wd.findElement(By.id("name0")).clear();
        wd.findElement(By.id("name0")).sendKeys("САНКТ");
        wd.findElement(By.cssSelector("div.station")).click();
        wd.findElement(By.id("name1")).click();
        wd.findElement(By.id("name1")).clear();
        wd.findElement(By.id("name1")).sendKeys("МОСКВА");
        wd.findElement(By.xpath("//div[7]/div[1]")).click();
        wd.findElement(By.id("buttonDate")).click();
        wd.findElement(By.xpath("//div[@class='bg']/div/div[3]/div/ul/li[22]/span")).click();
        wd.findElement(By.id("Submit")).click();
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
