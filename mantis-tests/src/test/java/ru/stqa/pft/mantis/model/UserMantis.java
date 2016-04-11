package ru.stqa.pft.mantis.model;

/**
 * Created by Юрий on 10.04.2016.
 */
public class UserMantis {
  private String username;
  private String password;
  private String email;

  public String getUsername() {
    return username;
  }

  public UserMantis withName(String name) {
    this.username = name;
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
