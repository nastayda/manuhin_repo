package ru.mdi.rest;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.domain.*;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.input.FieldInput;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;
import jersey.repackaged.com.google.common.collect.ImmutableList;
import org.codehaus.jettison.json.JSONException;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by avdeev on 26.04.2016.
 */
public class JiraIssues {
    final String URL = "https://jira.ursip.ru/";
    final String USER = "autotest_user";
    final String PASSWORD = "qwerty1";
    final String REQUEST = "project = TEST";
    @Test
    public void main() throws IOException, URISyntaxException, JSONException {
        JerseyJiraRestClientFactory connector = new JerseyJiraRestClientFactory();
        NullProgressMonitor pm = new NullProgressMonitor();
        JiraRestClient restClient = connector.createWithBasicHttpAuthentication(new URI(URL), USER, PASSWORD);
        SearchResult searchResult = restClient.getSearchClient().searchJql(REQUEST, pm);
        for (BasicIssue basicIssue : searchResult.getIssues()) {
            Issue issue = restClient.getIssueClient().getIssue(basicIssue.getKey(), pm);

            //Получаем данные
            String idIssue = issue.getKey();
            String item1 = getTextFromField(issue,"item1");
            String item2 = getTextFromField(issue,"item2");
            String item3 = getTextFromField(issue,"item3");
            System.out.println(idIssue);
            System.out.println(item1);
            System.out.println(item2);
            System.out.println(item3);

            //Меняем значение меток
            getUpdate(restClient, issue, "item1", "Тестовое поле 2",pm);
            getUpdate(restClient, issue, "item2", "Тестовое поле 3",pm);
            getUpdate(restClient, issue, "item3", "Тестовое поле 4",pm);
        }

    }

    private String getTextFromField(Issue issue, String field) {
        return issue.getFieldByName(field) == null || issue.getFieldByName(field).getValue() == null? "Нету такого поля!" : issue.getFieldByName(field).getValue().toString();
    }

    private void getUpdate(JiraRestClient restClient, Issue issue, String field, String value, NullProgressMonitor pm) {
            restClient.getIssueClient().update(issue, ImmutableList.of(
                    new FieldInput(issue.getFieldByName(field).getId(), value)
            ), pm);
    }
}

