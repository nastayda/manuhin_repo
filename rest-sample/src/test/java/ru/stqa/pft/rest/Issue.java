package ru.stqa.pft.rest;

/**
 * Created by Юрий on 17.04.2016.
 */
public class Issue {
  private int id;
  private String subject;
  private String description;

  public int getId() {
    return id;
  }

  public Issue withId(int id) {
    this.id = id;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public Issue withSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;
  }
}
