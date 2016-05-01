package ru.stqa.pft.gge.tests;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.stqa.pft.gge.appmanager.ApplicationManager;
import ru.yandex.qatools.allure.annotations.Attachment;


/**
 * Created by manuhin on 27.04.2016.
 */
public class MyTestListener implements ITestListener {
  @Override
  public void onTestStart(ITestResult iTestResult) {

  }

  @Override
  public void onTestSuccess(ITestResult iTestResult) {
    ApplicationManager app = (ApplicationManager) iTestResult.getTestContext().getAttribute("app");
    saveScreenshot(app.takeScreenshot());
  }

  @Override
  public void onTestFailure(ITestResult iTestResult) {
    ApplicationManager app = (ApplicationManager) iTestResult.getTestContext().getAttribute("app");
    saveScreenshot(app.takeScreenshot());
  }

  @Attachment(value = "Page screenshot", type = "image/png")
  public byte[] saveScreenshot(byte[] screenShot) {
    return screenShot;
  }

  @Override
  public void onTestSkipped(ITestResult iTestResult) {

  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    ApplicationManager app = (ApplicationManager) iTestResult.getTestContext().getAttribute("app");
    saveScreenshot(app.takeScreenshot());
  }

  @Override
  public void onStart(ITestContext iTestContext) {

  }

  @Override
  public void onFinish(ITestContext iTestContext) {

  }
}
