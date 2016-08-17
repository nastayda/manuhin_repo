package ru.stqa.pft.gge.model;

import com.google.gson.annotations.Expose;

/**
 * Created by manuhin on 02.08.2016.
 */
public class TaskProcessData {
  @Expose
  private String processTestCase;

  @Expose
  private String urlCardProcess;

  @Expose
  private String login;

  @Expose
  private String actionWithTask;

  @Expose
  private String fio;

  @Expose
  private String numberProcess;

  @Expose
  private String dateProcess;

  @Expose
  private String urlCardTask;

  @Expose
  private String numberTask;

  @Expose
  private String nameTask;

  public String getProcessTestCase() {
    return processTestCase;
  }

  public String getUrlCardProcess() {
    return urlCardProcess;
  }

  public String getLogin() {
    return login;
  }

  public String getActionWithTask() {
    return actionWithTask;
  }

  public String getFio() {
    return fio;
  }

  public String getNumberProcess() {
    return numberProcess;
  }

  public String getDateProcess() {
    return dateProcess;
  }

  public String getUrlCardTask() {
    return urlCardTask;
  }

  public String getNumberTask() {
    return numberTask;
  }

  public String getNameTask() {
    return nameTask;
  }

  public TaskProcessData withProcessTestCase(String processTestCase) {
    this.processTestCase = processTestCase;
    return this;
  }

  public TaskProcessData withUrlCardProcess(String urlCardProcess) {
    this.urlCardProcess = urlCardProcess;
    return this;
  }

  public TaskProcessData withLogin(String login) {
    this.login = login;
    return this;
  }

  public TaskProcessData withActionWithTask(String actionWithTask) {
    this.actionWithTask = actionWithTask;
    return this;
  }

  public TaskProcessData withFio(String fio) {
    this.fio = fio;
    return this;
  }

  public TaskProcessData withNumberProcess(String numberProcess) {
    this.numberProcess = numberProcess;
    return this;
  }

  public TaskProcessData withDateProcess(String dateProcess) {
    this.dateProcess = dateProcess;
    return this;
  }

  public TaskProcessData withUrlCardTask(String urlCardTask) {
    this.urlCardTask = urlCardTask;
    return this;
  }

  public TaskProcessData withNumberTask(String numberTask) {
    this.numberTask = numberTask;
    return this;
  }

  public TaskProcessData withNameTask(String nameTask) {
    this.nameTask = nameTask;
    return this;
  }

  @Override
  public String toString() {
    return processTestCase + " / " +
           numberTask + " / " +
           nameTask + " / " +
           login + " / " +
           numberProcess + " / " +
           fio + " / " +
           urlCardTask + " / " +
           dateProcess + " / " +
           urlCardProcess;
  }
}
