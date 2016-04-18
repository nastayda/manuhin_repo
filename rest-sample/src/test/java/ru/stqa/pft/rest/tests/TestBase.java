package ru.stqa.pft.rest.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.rest.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Created by Юрий on 09.04.2016.
 */
public class TestBase {
  protected static final ApplicationManager app =
          new ApplicationManager() ;

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws IOException {
  }

  public void skipIfNotFixed(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    if (isIssueOpen(issueId)) {
      System.out.println("Ignored because of issue " + issueId);
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  private boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
  //  MantisConnectPortType mc = app.soap().getMantisConnect();
  //  IssueData issueData = mc.mc_issue_get(app.getProperty("mantis.adminlogin"),
  //          app.getProperty("mantis.adminpassword"), BigInteger.valueOf(issueId));
  //  String statusName = issueData.getStatus().getName();
  //  if (statusName.equals("resolved") || statusName.equals("closed")) {
  //    return false;
  //  }
    return true;
  }
}
