package ru.stqa.pft.gge.tests;

import org.testng.annotations.DataProvider;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

/**
 * Created by manuhin on 14.01.2017.
 */
public class ProverkaMinMGE extends TestBase{

  //@DataProvider
  public class Testdata1 {
    //ссылки и эксклюзивные данные тестируемого сервера
    //имя домена для всех ссылок
    String domen = "https://expertiza-tech.mos.ru";
    //логин пользователя освобождающего логин adminMGE, нужен при терминированном доступе.
    String userlogout = "VASILENKORN";
    //универсальный пароль
    String password = "Yjdsq№7Ujl";



  }
  @Test
  public void testProverkaMinMGE () throws Exception {
    //app.session().;
           // wd.get("https://ya.ru");
  }

  // Testdata testdata = new Testdata1();

}
