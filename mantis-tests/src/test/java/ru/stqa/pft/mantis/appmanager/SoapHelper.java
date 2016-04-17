package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import org.hibernate.service.spi.ServiceException;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by manuhin on 15.04.2016.
 */
public class SoapHelper {
  
  private ApplicationManager app;

  public SoapHelper(ApplicationManager app) {
    this.app = app;
  }

  public Set<Project> getProjects() throws MalformedURLException, RemoteException, javax.xml.rpc.ServiceException {
    MantisConnectPortType mc = getMantisConnect();
    ProjectData[] projects = mc.mc_projects_get_user_accessible(app.getProperty("mantis.adminlogin"),
            app.getProperty("mantis.adminpassword"));

    return Arrays.asList(projects).stream()
            .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName()))
            .collect(Collectors.toSet());
  }

  public MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException, javax.xml.rpc.ServiceException {
    return new MantisConnectLocator()
              .getMantisConnectPort(new URL(app.getProperty("mantis.soapURL")));
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, RemoteException, javax.xml.rpc.ServiceException {
    MantisConnectPortType mc = getMantisConnect();
    String mantisLogin = app.getProperty("mantis.adminlogin");
    String mantisPassword = app.getProperty("mantis.adminpassword");
    String[] categories = mc.mc_project_get_categories(mantisLogin, mantisPassword, BigInteger.valueOf(issue.getProject().getId()));
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    issueData.setCategory(categories[0]);
    BigInteger issueId = mc.mc_issue_add(mantisLogin, mantisPassword, issueData);
    IssueData createdIssueData = mc.mc_issue_get(mantisLogin, mantisPassword, issueId);

    return new Issue().withId(createdIssueData.getId().intValue())
            .withSummary(createdIssueData.getSummary())
            .withDescription(createdIssueData.getDescription())
            .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
                                      .withName(createdIssueData.getProject().getName()));
  }
}
