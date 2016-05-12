// Тут нужно все причесать
package ru.stqa.pft.gge.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class PodachaZajavlenija {
    FirefoxDriver wd;
    
    @BeforeMethod
    public void setUp() throws Exception {
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }
    
    @Test
    public void PodachaZajavlenija() throws InterruptedException {
        int korpus = 1; // номер корпуса - изменяющийся параметр для создания большого кол-ва заявок

        //wd.manage().window().maximize();
        autorization("https://test-my.gge.ru/auth/login.action", "galactica_admin1", "21");
        //autorization("https://vm-081-as-gge.mdi.ru/auth/login.action", "galactica_admin1", "21");

        wd.manage().window().maximize();
        // Первый шаг заявления
        step1(korpus);

        wd.findElement(By.xpath("//form[@class='form']/div/div/div/div/div[7]/div/div[2]/div[2]/span[1]/span/span/span")).click();
        wd.findElement(By.xpath("//form[@class='form']/div/div/div/div/div[7]/div/div[2]/div[2]/span[1]/span/span/span/span[1]/span[1]")).click();
        type(By.id("_labelAcceptor27"), "10000");
        click(By.id("_labelAcceptor29"));
        click(By.xpath("//form[@class='form']/div/div/div/div/div[7]/div/div[2]/div[2]/div[2]/div/span/span"));
        click(By.xpath("//form[@class='form']/div/div/div/div/div[7]/div/div[2]/div[3]/span[1]/span/span/span/span[1]/span[1]"));
        type(By.id("_labelAcceptor37"), "12000");
        click(By.id("next"));

        click(By.id("_labelAcceptor1"));
        click(By.id("_labelAcceptor7"));
        type(By.id("_labelAcceptor12"), "101000, Москва, Фуркасовский пер., д.6");
        type(By.id("_labelAcceptor13"), "info@gge.ru");
        type(By.id("_labelAcceptor13"), "info@yandex.ru");
        type(By.id("_labelAcceptor14"), "7 499 652-90-19");
        type(By.id("_labelAcceptor18"), "044583001");
        type(By.id("_labelAcceptor19"), "отделение 1 Московского ГТУ Банка");
        type(By.id("_labelAcceptor20"), "г. Москва 705");
        type(By.id("_labelAcceptor23"), "40601810000003000002");
        type(By.id("_labelAcceptor24"), "27778410004517733333");
        type(By.id("_labelAcceptor26"), "Хохолина");
        type(By.id("_labelAcceptor28"), "Галина");
        type(By.id("_labelAcceptor29"), "Анатольевна");
        type(By.id("_labelAcceptor31"), "Хохолиной Галины Анатольевной");
        type(By.id("_labelAcceptor32"), "Устава");
        type(By.id("_labelAcceptor33"), "Руководитель");
        type(By.id("_labelAcceptor34"), "Руководителя");
        type(By.id("_labelAcceptor37"), "8 948 853-04-99");
        type(By.id("_labelAcceptor38"), "433439@mdi.ru");
        type(By.id("_labelAcceptor38"), "433449@mdi.ru");

        if (!wd.findElement(By.xpath("//form[@class='form']/div/div/div/div/div[3]/div[4]/div[3]/div[4]/div/div/div[2]/div/span[10]/span/label/input")).isSelected()) {
            wd.findElement(By.xpath("//form[@class='form']/div/div/div/div/div[3]/div[4]/div[3]/div[4]/div/div/div[2]/div/span[10]/span/label/input")).click();
        }
        if (!wd.findElement(By.xpath("//form[@class='form']/div/div/div/div/div[4]/div[4]/div[1]/div/div[2]/div/span/span/label/input")).isSelected()) {
            wd.findElement(By.xpath("//form[@class='form']/div/div/div/div/div[4]/div[4]/div[1]/div/div[2]/div/span/span/label/input")).click();
        }
        click(By.id("_labelAcceptor99"));
        click(By.id("next"));
        if (!wd.findElement(By.id("9C17D64348EA4A97A1711FA9711A7AFE_0_0")).isSelected()) {
            wd.findElement(By.id("9C17D64348EA4A97A1711FA9711A7AFE_0_0")).click();
        }
        if (!wd.findElement(By.id("B9D198ADC20E4E9FBE9D2B79C14887C2_0_0")).isSelected()) {
            wd.findElement(By.id("B9D198ADC20E4E9FBE9D2B79C14887C2_0_0")).click();
        }
        if (!wd.findElement(By.id("DB68524BAAE74FD89C29A62EC52FFFC9_0_0")).isSelected()) {
            wd.findElement(By.id("DB68524BAAE74FD89C29A62EC52FFFC9_0_0")).click();
        }
        if (!wd.findElement(By.id("1EB0BAB72F3B46ADAF74DDE33D0BBF7B_0_0")).isSelected()) {
            wd.findElement(By.id("1EB0BAB72F3B46ADAF74DDE33D0BBF7B_0_0")).click();
        }
        wd.findElement(By.id("next")).click();
    }

    private void autorization(String url, String login, String password) {
        wd.get(url);
        type(By.id("username"), login);
        type(By.id("password"), password);
        click(By.id("submitBtn"));
    }

    private void step1(int korpus) throws InterruptedException {
        waitElementAndClick(By.linkText("Подать новое заявление"));

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
        checkCheckbox("CHECK_TYPE");
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

    private void checkCheckbox(final String nick) throws InterruptedException {
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
