package ru.stqa.pft.gge.model;

import com.google.gson.annotations.Expose;

/**
 * Created by manuhin on 16.08.2016.
 */
public class ProcessTestCases {

  @Expose
  private String processTestCase;

  public String getProcessTestCase() {
    return processTestCase;
  }

  public ProcessTestCases withProcessTestCase(String processTestCase) {
    this.processTestCase = processTestCase;
    return this;
  }

  @Override
  public String toString() {
    return "processTestCase = " + processTestCase;
  }
}
