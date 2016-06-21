package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.gge.model.GeneratorData;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by manuhin on 21.04.2016.
 */
public class GeneratorHelperUGD extends HelperBase {
  public GeneratorHelperUGD(WebDriver wd, Properties properties) {
    super(wd);
    this.properties = properties;
    //wd.manage().window().maximize();
  }

  private Properties properties;

  public List<GeneratorData> GenParamUGD(int i, boolean isProdServer,
                                         String loginUser, String baseUrl,
                                         String listVitrinasUrl) throws InterruptedException {
    List<WebElement> mainMenu;
    List<WebElement> vitrinas;
    List <GeneratorData> listRazd = new ArrayList<>();

    String xpathRazdelStart = "(//*[@id='PortalTree']/li)";
    String xpathRazdelEnd = "";

    String xpathMenuStart = "(//*[@id='PortalTree']/li";
    String xpathMenuIn = "/ul/li/a)";
    String xpathMenuEnd = "";

    String xpathSubmenuStart = "(//*[@id='PortalTree']/li";
    String xpathSubmenuIn1 = "/ul/li";
    String xpathSubmenuIn2 = "/a/../ul/li/a)";
    String xpathSubmenuEnd = "";

    //Раздел
    for (int r = 1; r <= i; r++){
      waitLoadPage(isProdServer);
      String xpath1 = xpathRazdelStart + "["+ r +"]" + xpathRazdelEnd;
      String nameRazd = wd.findElement(By.xpath(xpath1)).getText();
      int nameRazdNum = nameRazd.indexOf("\n");
      if (nameRazdNum == -1) {
        continue;
      }
      nameRazd = nameRazd.substring(0, nameRazdNum);
      String xpathMenu = xpathMenuStart + "["+ r +"]" + xpathMenuIn + xpathMenuEnd;
      mainMenu =  wd.findElements(By.xpath(xpathMenu));
      int amountMenu = mainMenu.size();
      //Меню
      for(int m = 1; m <= amountMenu; m++) {
        waitLoadPage(isProdServer);
        String xpath2 = xpathMenuStart + "[" + r + "]" + xpathMenuIn + "["+ m +"]" + xpathMenuEnd;
        String nameMenu = wd.findElement(By.xpath(xpath2)).getText();

        String menuID = wd.findElement(By.xpath(xpath2)).getAttribute("id");

        String xpathVitrinas = xpathSubmenuStart + "[" + r + "]" +
                xpathSubmenuIn1 + "["+ m +"]" +
                xpathSubmenuIn2 + xpathSubmenuEnd;
        vitrinas = wd.findElements(By.xpath(xpathVitrinas));
        //Витрина
        int amountSubMenu = vitrinas.size();
        if (amountSubMenu == 0) {
          GeneratorData generatorData = new GeneratorData()
                  .withRazdelName(nameRazd).withRazdelXpath(xpath1)
                  .withMenuName(nameMenu).withMenuXpath(xpath2)
                  .withVitrinaName(nameMenu).withVitrinaXpath(xpath2)
                  .withVitrinaID(menuID)
                  .withLoginUser(loginUser).withBaseUrl(baseUrl)
                  .withListVitrinasUrl(listVitrinasUrl);
          listRazd.add(generatorData);
        } else {
          for(int s = 1; s <= amountSubMenu; s++){
            String xpath3 = xpathSubmenuStart + "[" + r + "]" +
                    xpathSubmenuIn1 + "["+ m +"]" +
                    xpathSubmenuIn2 + "[" + s + "]" + xpathSubmenuEnd;
            String nameSub = wd.findElement(By.xpath(xpath3)).getText();

            String vitrinaID = wd.findElement(By.xpath(xpath3)).getAttribute("id");

            GeneratorData generatorData = new GeneratorData()
                    .withRazdelName(nameRazd).withRazdelXpath(xpath1)
                    .withMenuName(nameMenu).withMenuXpath(xpath2)
                    .withVitrinaName(nameSub).withVitrinaXpath(xpath3)
                    .withVitrinaID(vitrinaID)
                    .withLoginUser(loginUser).withBaseUrl(baseUrl)
                    .withListVitrinasUrl(listVitrinasUrl);
            listRazd.add(generatorData);
          }
        }
      }
    }
    return listRazd;
  }
}
