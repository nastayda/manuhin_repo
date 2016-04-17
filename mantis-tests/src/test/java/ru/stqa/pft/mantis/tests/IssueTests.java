package ru.stqa.pft.mantis.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Created by Юрий on 17.04.2016.
 */
public class IssueTests extends TestBase {

  @BeforeTest
  public void preConditions() throws RemoteException, ServiceException, MalformedURLException {
    skipIfNotFixed(3);
    skipIfNotFixed(2);
  }

  @Test
  public void testCheckIssue() {
    System.out.println("Начинаем тест...");;
  }
}
