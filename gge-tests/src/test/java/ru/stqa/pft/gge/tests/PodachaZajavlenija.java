// Тут нужно все причесать
package ru.stqa.pft.gge.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class PodachaZajavlenija {
    //FirefoxDriver wd;
    WebDriver wd;
    public static final String USERNAME = "yuriymanuhin1";
    public static final String AUTOMATE_KEY = "V2DteiKuxvATeZxmu7iG";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";


    @BeforeMethod
    public void setUp() throws Exception {
        //For HTTP
//        System.getProperties().put("http.proxyHost", "proxy.mdi.ru");
//        System.getProperties().put("http.proxyPort", "3128");
//        System.getProperties().put("http.proxyUser", "manuhin");
//        System.getProperties().put("http.proxyPassword", "manyurij");
//
//        //For HTTPS
//        System.getProperties().put("https.proxyHost", "proxy.mdi.ru");
//        System.getProperties().put("https.proxyPort", "3128");
//        System.getProperties().put("https.proxyUser", "manuhin");
//        System.getProperties().put("https.proxyPassword", "manyurij");
//
//        wd = new RemoteWebDriver(new URL(URL), DesiredCapabilities.firefox());

        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @Test
    public void PodachaZajavlenija() throws InterruptedException {
        int korpus = 113; // номер корпуса - изменяющийся параметр для создания большого кол-ва заявок
        int maxKorpus = 114;
        boolean easyVariant = true;

        for (int i = korpus; i < maxKorpus; i++) {
            autorization("https://test-my.gge.ru/auth/login.action", "galactica_admin1", "21");
            //autorization("https://vm-081-as-gge.mdi.ru/auth/login.action", "galactica_admin1", "21");

            wd.manage().window().maximize();

            // Первый шаг заявления
            step1(i, easyVariant);
            // Второй шаг заявления
            step2();
            // Третий шаг заявления
            step3();
            // Четвертый шаг - подача заявления
            step4();
        }
    }

    private void step4() {
        click(By.id("apply"));
    }

    private void step3() throws InterruptedException {
        click(By.xpath("//*[@for=\"9C17D64348EA4A97A1711FA9711A7AFE_0_2\"]/span"));
        checkCheckboxWithId("B9D198ADC20E4E9FBE9D2B79C14887C2_0_0");
        checkCheckboxWithId("DB68524BAAE74FD89C29A62EC52FFFC9_0_0");
        checkCheckboxWithId("1EB0BAB72F3B46ADAF74DDE33D0BBF7B_0_0");
        waitLoadPage();
        click(By.id("next"));
    }

    private void checkCheckboxWithId(String id) {
        String jstriptString = "$('input#" + id + "').click()";
        ((JavascriptExecutor) wd).executeScript(jstriptString);
    }

    private void step2() throws InterruptedException {
        // Первая организация
        waitElement(By.id("_labelAcceptor3"));
        waitLoadPage();
        type(By.id("_labelAcceptor3"), "7708543649");

        waitElement(By.id("_labelAcceptor5"));
        type(By.id("_labelAcceptor5"), "770801001");
        Thread.sleep(500);
        type(By.id("_labelAcceptor5"), "\n");
        type(By.id("_labelAcceptor5"), "770801001");

        // Вторая организация
        waitElement(By.id("_labelAcceptor45"));
        waitLoadPage();
        type(By.id("_labelAcceptor45"), "4716016979");

        waitElement(By.id("_labelAcceptor47"));
        type(By.id("_labelAcceptor47"), "772801001");
        Thread.sleep(500);
        type(By.id("_labelAcceptor47"), "\n");
        type(By.id("_labelAcceptor47"), "772801001");

        // Третья организация
        waitElement(By.id("_labelAcceptor87"));
        waitLoadPage();
        type(By.id("_labelAcceptor87"), "0000990");

        waitElement(By.id("_labelAcceptor89"));
        type(By.id("_labelAcceptor89"), "0007540");
        Thread.sleep(500);
        type(By.id("_labelAcceptor89"), "\n");
        type(By.id("_labelAcceptor89"), "0007540");

        String xpathButtonAddOrg = "(//div[@class=\"addSection buttonGray addRem add\"])[4]";
        waitElement(By.xpath(xpathButtonAddOrg));
        click(By.xpath(xpathButtonAddOrg));

        // Четвертая организация
        waitElement(By.id("_labelAcceptor167"));
        waitLoadPage();
        type(By.id("_labelAcceptor167"), "1110990");

        waitElement(By.id("_labelAcceptor169"));
        type(By.id("_labelAcceptor169"), "1117540");
        Thread.sleep(500);
        type(By.id("_labelAcceptor169"), "\n");
        type(By.id("_labelAcceptor169"), "1117540");

        //Роль 4й организации
        String jstriptString = "$('select#_labelAcceptor166').find('option:eq(9)').attr('selected', 'selected').end().change();";
        ((JavascriptExecutor) wd).executeScript(jstriptString);
        //Thread.sleep(5000);

        waitLoadPage();
        //Thread.sleep(3000);
        click(By.id("next"));
    }

    private void autorization(String url, String login, String password) {
        wd.get(url);
        type(By.id("username"), login);
        type(By.id("password"), password);
        click(By.id("submitBtn"));
    }

    private void step1(int korpus, boolean easyVariant) throws InterruptedException {
        waitElementAndClick(By.linkText("Подать новое заявление"));

        if (easyVariant) {
            waitLoadPage();
            click(By.xpath("//*[@for=\"A572AF0F9BBE41CB9A399FCA9BDE25E2_0_2\"]/span"));
            click(By.xpath("//*[@for=\"30CF4DD3EB714DF4BA4CBC81FE249760_0_1\"]/span"));
        }

        // Наименование объекта
        String nameObject = "Спортивный комплекс «Звездный». Корпус " + korpus;
        type(By.id("_labelAcceptor10"), nameObject);

        // комбобокс "Источник финансирования"
        multiSelect(4, 2);

        // Почтовый адрес
        String addressObject = "394065, Воронежская обл., г.Воронеж, ул. Южно-Моравская, д.3, к. " + korpus;
        type(By.id("_labelAcceptor18"), addressObject);

        // комбобокс "Субъект РФ"
        multiSelect(6, 9);

        waitLoadPage();
        Thread.sleep(5000);
        // справочник "Функциональное назначение"
        fillReference(By.xpath(".//*[@for=\"_labelAcceptor21\"]/..//*[@class=\"folderButtonAdd\"]"));

        // чекбоксом "Добавить типовые технико-экономические показатели"
        checkCheckboxWithNick("CHECK_TYPE");
        Thread.sleep(3000);

        waitElement(By.id("_labelAcceptor27"));
        type(By.id("_labelAcceptor27"), "1,35"); // Площадь участка
        waitLoadPage();
        type(By.id("_labelAcceptor37"), "10500"); // Площадь застройки
        waitLoadPage();
        type(By.id("_labelAcceptor42"), "8500"); // Площадь благоустройства
        waitLoadPage();
        type(By.id("_labelAcceptor47"), "1200"); // Площадь озеленения

        type(By.id("_labelAcceptor52"), "8,7"); // Общая площадь
        type(By.id("_labelAcceptor57"), "51200"); // Общий строительный объем
        type(By.id("_labelAcceptor62"), "5100"); // Строительный объём надземной/надводной части
        type(By.id("_labelAcceptor67"), "14200"); // Строительный объем подземной/подводной части
        type(By.id("_labelAcceptor72"), "15"); // Верхняя отметка объекта
        type(By.id("_labelAcceptor77"), "5"); // Этажность
        type(By.id("_labelAcceptor82"), "24"); // Продолжительность строительства, реконструкции
        type(By.id("_labelAcceptor87"), "120,5"); // Общая сметная стоимость строительства в ценах по состоянию на 01.01.2000 (без НДС)

        type(By.id("_labelAcceptor92"), "156,2"); // Общая сметная стоимость строительства с учетом НДС (в текущих ценах)
        type(By.id("_labelAcceptor97"), "43,3"); // Стоимость проектно-изыскательских работ (ПИР) в ценах по состоянию на 01.01.2000 (без НДС)
        type(By.id("_labelAcceptor102"), "51,1"); // Стоимость проектно-изыскательских работ (ПИР) в текущих ценах (с НДС)

        click(By.id("next"));
    }

    private void multiSelect(final int numSelect, final int numValue) throws InterruptedException {
        String jstriptString = "$('.autoFormMULTISELECT:eq(" + numSelect + ") .token-input-list').click();";
        ((JavascriptExecutor) wd).executeScript(jstriptString);

        Thread.sleep(2000);

        jstriptString = "$('.token-input-dropdown li:eq(" + numValue + ")').mousedown();";
        ((JavascriptExecutor) wd).executeScript(jstriptString);
    }

    private void fillReference(By locator) throws InterruptedException {
        Set<String> winOld = wd.getWindowHandles();

        waitLoadPage();
        waitElement(locator);
        click(locator);

        Set<String> wNewSet = wd.getWindowHandles();
        int attempt = 0;
        while (wNewSet.size() < 2 && attempt < 15) {
            Thread.sleep(1000);
            click(locator);
            wNewSet = wd.getWindowHandles();
            attempt++;
        }

        wNewSet.removeAll(winOld);
        String wNew = wNewSet.iterator().next();
        wd.switchTo().window(wNew);

        String xpathPlus1 = "(//*[@class=\"Expand\"])[1]";
        waitElement(By.xpath(xpathPlus1));
        click(By.xpath(xpathPlus1));

        String xpathRadio2 = "(//input[@class=\"RadioCheckElem\"])[2]";
        waitElement(By.xpath(xpathRadio2));
        click(By.xpath(xpathRadio2));

        String xpathSubmit = ".//input[@id='submitButton']";
        waitElement(By.xpath(xpathSubmit));
        click(By.xpath(xpathSubmit));

        wd.switchTo().window(winOld.iterator().next());
        waitLoadPage();
    }

    private void checkCheckboxWithNick(final String nick) throws InterruptedException {
        String jstriptString = "$('[nick=\"" + nick + "\"] input[type=\"checkbox\"]').click();";
        ((JavascriptExecutor) wd).executeScript(jstriptString);
    }

    private void waitElementAndClick(By locator) throws InterruptedException {
        waitElement(locator);
        waitLoadPage();
        click(locator);
    }

    @AfterMethod
    public void tearDown() {
        wd.quit();
    }

    public void click(By locator) {
        wd.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        if (text != null) {
            String existingText = wd.findElement(locator).getAttribute("value");
            if (! text.equals(existingText)) {
                click(locator);
                wd.findElement(locator).clear();
                wd.findElement(locator).sendKeys(text);
            }
        }
    }

    protected void attach(By locator, File file) {
        if (file != null) {
            wd.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    protected boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public void waitLoadPage() throws InterruptedException {
        waitElement(By.xpath("//div[@id=\"overlay\" and @style=\"display: none;\"]"));
    }

    protected void waitElement(By locator) throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= 60) {
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
}
