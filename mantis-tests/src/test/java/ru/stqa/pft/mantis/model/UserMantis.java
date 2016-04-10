package ru.stqa.pft.mantis.model;

/**
 * Created by Юрий on 10.04.2016.
 */
public class UserMantis {
  private String name;
  private String password;
  private String email;

  public String getName() {
    return name;
  }

  public UserMantis withName(String name) {
    this.name = name;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserMantis withPassword(String password) {
    this.password = password;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserMantis withEmail(String email) {
    this.email = email;
    return this;
  }
}
