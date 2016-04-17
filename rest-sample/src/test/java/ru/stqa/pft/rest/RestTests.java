package ru.stqa.pft.rest;


import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * Created by Юрий on 17.04.2016.
 */
public class RestTests {

  @Test
  public void testCreateIssue() {
    Set<Issue> oldIssues = getIssue();
    Issue newIssue = new Issue();
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssue();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);

  }

  private Set<Issue> getIssue() {
    Request
  }

  private int createIssue(Issue newIssue) {
    return 0;
  }
}
