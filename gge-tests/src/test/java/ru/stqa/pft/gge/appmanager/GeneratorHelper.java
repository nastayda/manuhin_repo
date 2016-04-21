package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.gge.model.GeneratorData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuhin on 21.04.2016.
 */
public class GeneratorHelper extends HelperBase {
  public GeneratorHelper(WebDriver wd) {
    super(wd);
  }

  public String RAZD = ".//*[@id='firstMenu']/li/a[@href!='']";
  public String MENU = ".//*[@id='secondMenu']/ul/li/a";
  public String SUBMENU = "(.//*[@id='secondMenu']/ul/li//ul/li)";

  public List<GeneratorData> GenParam(int i) throws InterruptedException {
    List<WebElement> mainMenu;
    List<WebElement> dropDown;
    List <GeneratorData> listRazd = new ArrayList<>();

    //Раздел
    for (int r = 1; r <= i; r++){
      //waitElement(By.xpath("//div[@id=\"overlay\" and @style=\"display: none;\"]"));
      waitLoadPage();
      String xpath1 = "//*[@id='firstMenu']/li"+ "["+ r +"]" +"/a[@href!='']";
      //WebElement razdel = wd.findElement(By.xpath(xpath1));
      click(By.xpath(xpath1));
      //wd.findElementByXPath(xpath1).click();
      String nameRazd = wd.findElement(By.xpath(xpath1)).getText();
      mainMenu =  wd.findElements(By.xpath(MENU));
      int amountMenu = mainMenu.size();
      //Меню
      for(int m = 1; m <= amountMenu; m++) {
        //waitElement(By.xpath("//div[@id=\"overlay\" and @style=\"display: none;\"]"));
        waitLoadPage();
        String xpath2 = "//*[@id='secondMenu']/ul/li" + "["+ m +"]" +"/a";
        String nameMenu = wd.findElement(By.xpath(xpath2)).getText();
        String classMenu = wd.findElement(By.xpath(".//*[@id='secondMenu']/ul/li" + "["+ m +"]"))
                .getAttribute("class");
        //Витрина
        if (classMenu.equals("link dropDown") || classMenu.equals("link dropDown active")){
          dropDown = wd.findElements(By.xpath(".//*[@id='secondMenu']/ul/li"+ "[" + m +"]" + "/ul/li"));//.//*[@id='secondMenu']/ul/li[2]/ul/li
          int amountSubMenu = dropDown.size();
          //waitElement(By.xpath("//div[@id=\"overlay\" and @style=\"display: none;\"]"));
          waitLoadPage();
          click(By.xpath(xpath2));
          for(int s = 1; s <= amountSubMenu; s++){
            String xpath3 = "//*[@id='secondMenu']/ul/li" + "[" + m + "]" + "//ul/li" + "[" + s + "]" + "/a";
            String nameSub = wd.findElement(By.xpath(xpath3)).getText();
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
}
