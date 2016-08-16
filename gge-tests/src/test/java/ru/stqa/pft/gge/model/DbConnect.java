package ru.stqa.pft.gge.model;

import com.google.gson.annotations.Expose;

/**
 * Created by manuhin on 16.08.2016.
 */
public class DbConnect {
  @Expose
  private String dbserver;

  @Expose
  private String port;

  @Expose
  private String sid;

  @Expose
  private String user;

  @Expose
  private String password;


  public String getPassword() {
    return password;
  }

  public String getDbserver() {
    return dbserver;
  }

  public String getPort() {
    return port;
  }

  public String getSid() {
    return sid;
  }

  public String getUser() {
    return user;
  }

  public DbConnect withPassword(String password) {
    this.password = password;
    return this;
  }

  public DbConnect withDbserver(String dbserver) {
    this.dbserver = dbserver;
    return this;
  }

  public DbConnect withPort(String port) {
    this.port = port;
    return this;
  }

  public DbConnect withSid(String sid) {
    this.sid = sid;
    return this;
  }

  public DbConnect withUser(String user) {
    this.user = user;
    return this;
  }
}
