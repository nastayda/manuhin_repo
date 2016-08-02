package ru.stqa.pft.gge.model;

import com.google.gson.annotations.Expose;

/**
 * Created by manuhin on 02.08.2016.
 */
public class ProcessData {
  @Expose
  private String urlCardProcess;

  @Expose
  private String login;

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

  public String getUrlCardProcess() {
    return urlCardProcess;
  }

  public String getLogin() {
    return login;
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

  public ProcessData withUrlCardProcess(String urlCardProcess) {
    this.urlCardProcess = urlCardProcess;
    return this;
  }

  public ProcessData withLogin(String login) {
    this.login = login;
    return this;
  }

  public ProcessData setFio(String fio) {
    this.fio = fio;
    return this;
  }

  public ProcessData withNumberProcess(String numberProcess) {
    this.numberProcess = numberProcess;
    return this;
  }

  public ProcessData withDateProcess(String dateProcess) {
    this.dateProcess = dateProcess;
    return this;
  }

  public ProcessData withUrlCardTask(String urlCardTask) {
    this.urlCardTask = urlCardTask;
    return this;
  }

  public ProcessData withNumberTask(String numberTask) {
    this.numberTask = numberTask;
    return this;
  }

  public ProcessData withNameTask(String nameTask) {
    this.nameTask = nameTask;
    return this;
  }

  @Override
  public String toString() {
    return numberTask + " / " +
           nameTask + " / " +
           login + " / " +
           urlCardTask + " / " +
           fio + " / " +
           numberProcess + " / " +
           dateProcess + " / " +
           urlCardProcess;
  }
}
