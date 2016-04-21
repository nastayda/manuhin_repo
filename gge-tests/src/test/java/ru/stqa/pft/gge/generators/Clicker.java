package ru.stqa.pft.gge.generators;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;
import ru.stqa.pft.gge.model.GeneratorData;

import static org.testng.Assert.fail;

public class Clicker {
    FirefoxDriver wd;
    public String RAZD = ".//*[@id='firstMenu']/li/a[@href!='']";
    public String MENU = ".//*[@id='secondMenu']/ul/li/a";
    public String SUBMENU = "(.//*[@id='secondMenu']/ul/li//ul/li)";
    public String URL = "https://vm-082-as-gge.mdi.ru/auth/login.action?service=https%3A%2F%2Fvm-082-as-gge.mdi.ru%2Fportal%2FtabInfo.action%3Ftab%3DPRIVATEOFFICE";
    public String usserName = "galactica_admin1";
    public String usserPass = "21";

    @BeforeMethod
    public void setUp() throws Exception {
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        wd.get(URL);
        login(usserName, usserPass);
    }

    private void login(String nick, String password) {
        wd.findElement(By.id("username")).click();
        wd.findElement(By.id("username")).clear();
        wd.findElement(By.id("username")).sendKeys(nick);
        wd.findElement(By.id("password")).click();
        wd.findElement(By.id("password")).sendKeys(password);
        wd.findElement(By.id("submitBtn")).click();
    }

    public List<GeneratorData> GenParam(int i) throws InterruptedException {
        List<WebElement> mainMenu;
        List<WebElement> dropDown;
        List <GeneratorData> listRazd = new ArrayList<>();

        //Раздел
        for (int r = 1; r <= i; r++){
            waitElement(By.xpath("//div[@id=\"overlay\" and @style=\"display: none;\"]"));
            String xpath1 = "//*[@id='firstMenu']/li"+ "["+ r +"]" +"/a[@href!='']";
            wd.findElementByXPath(xpath1).click();
            String nameRazd = wd.findElementByXPath(xpath1).getText();
            mainMenu =  wd.findElementsByXPath(MENU);
            int amountMenu = mainMenu.size();
            //Меню
            for(int m = 1; m <= amountMenu; m++) {
                waitElement(By.xpath("//div[@id=\"overlay\" and @style=\"display: none;\"]"));
                String xpath2 = "//*[@id='secondMenu']/ul/li" + "["+ m +"]" +"/a";
                String nameMenu = wd.findElementByXPath(xpath2).getText();
                String classMenu = wd.findElementByXPath(".//*[@id='secondMenu']/ul/li" + "["+ m +"]").getAttribute("class");
                //Витрина
                if (classMenu.equals("link dropDown") || classMenu.equals("link dropDown active")){
                    dropDown = wd.findElementsByXPath(".//*[@id='secondMenu']/ul/li"+ "[" + m +"]" + "/ul/li");//.//*[@id='secondMenu']/ul/li[2]/ul/li
                    int amountSubMenu = dropDown.size();
                    waitElement(By.xpath("//div[@id=\"overlay\" and @style=\"display: none;\"]"));
                    wd.findElementByXPath(xpath2).click();
                    for(int s = 1; s <= amountSubMenu; s++){
                        String xpath3 = "//*[@id='secondMenu']/ul/li" + "[" + m + "]" + "//ul/li" + "[" + s + "]" + "/a";
                        String nameSub = wd.findElementByXPath(xpath3).getText();
                        GeneratorData generatorData = new GeneratorData();
                        generatorData.setRazdName(nameRazd);
                        generatorData.setRazdXpath(xpath1);
                        generatorData.setMenu(nameMenu);
                        generatorData.setMenuXpath(xpath2);
                        generatorData.setVitrina(nameSub);
                        generatorData.setVitrinaXpath(xpath3);
                        listRazd.add(generatorData);
                    }
                } else {
                    GeneratorData generatorData = new GeneratorData();
                    generatorData.setRazdName(nameRazd);
                    generatorData.setRazdXpath(xpath1);
                    generatorData.setMenu(nameMenu);
                    generatorData.setMenuXpath(xpath2);
                    generatorData.setVitrina(nameMenu);
                    generatorData.setVitrinaXpath(xpath2);
                    listRazd.add(generatorData);
                }
            }
        }
        return listRazd;
    }

    private void waitElement(By locator) throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= 80) {
                fail("timeout");
            }
            try {
                if (isElementPresent(locator)) {
                    break;
                }
            }
            catch (Exception e) {
            }
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

