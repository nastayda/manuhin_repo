package ru.stqa.pft.addressbook.tests;

public class ContactData {
  private final String firstName;
  private final String lastName;
  private final String address;
  private final String home;
  private final String mobile;
  private final String work;
  private final String email;
  private final String email2;
  private final String address2;
  private final String phone2;

  public ContactData(String firstName, String lastName, String address, String home, String mobile, String work, String email, String email2, String address2, String phone2) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.home = home;
    this.mobile = mobile;
    this.work = work;
    this.email = email;
    this.email2 = email2;
    this.address2 = address2;
    this.phone2 = phone2;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAddress() {
    return address;
  }

  public String getHome() {
    return home;
  }

  public String getMobile() {
    return mobile;
  }

  public String getWork() {
    return work;
  }

  public String getEmail() {
    return email;
  }

  public String getEmail2() {
    return email2;
  }

  public String getAddress2() {
    return address2;
  }

  public String getPhone2() {
    return phone2;
  }
}
