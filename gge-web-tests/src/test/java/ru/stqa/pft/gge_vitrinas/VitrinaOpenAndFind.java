package test.java.ru.stqa.pft.gge_vitrinas;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class VitrinaOpenAndFind {
    FirefoxDriver wd;
    
    @BeforeMethod
    public void setUp() throws Exception {
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    
    @Test
    public void VitrinaOpenAndFind() {
        wd.get("https://vm-082-as-gge.mdi.ru/auth/login.action");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("username")).click();
        wd.findElement(By.id("username")).clear();
        wd.findElement(By.id("username")).sendKeys("galactica_admin1");
        wd.findElement(By.id("password")).click();
        wd.findElement(By.id("password")).sendKeys("21");
        wd.findElement(By.id("submitBtn")).click();
//        wd.findElement(By.linkText("Экспертиза")).click();
       // wd.findElement(By.xpath("(.//*[@id='firstMenu']//a)[2]")).click();
        wd.findElement(By.linkText("Экспертиза")).click();
//        wd.findElement(By.cssSelector("div.closeBtn.btSearchStyle")).click();
        wd.findElement(By.id("0E0CADA75DC448959686DFC63BD2178A")).click();
        wd.findElement(By.xpath("//div[@class='form']/div[2]/span/input")).click();
        wd.findElement(By.xpath("//div[@class='form']/div[2]/span/input")).clear();
        wd.findElement(By.xpath("//div[@class='form']/div[2]/span/input")).sendKeys("ав");
        wd.findElement(By.xpath("//div[@class='form']/input")).click();
        wd.findElement(By.cssSelector("div.closeBtn.btSearchStyle")).click();
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
