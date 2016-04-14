package ru.stqa.pft.rzd.model;

import java.util.Date;

/**
 * Created by manuhin on 13.04.2016.
 */
public class Train {
  // запись по поезду: xpath = //*[@class="trlist__trlist-row trslot "]
  private Integer id;         // номер кнопки поезда
  private String number;      // номер поезда
  private String name;        // наименование поезда
  private String stationFrom; // станция отправления
  private Date datetimeFrom;    // дата, время отправления
  private String stationTo;   // станция назначения
  private Date datetimeTo;      // дата, время прибытия
  private String typePlace;   // тип вагона (сидячий, купе, св, плацкарт...)
  private Integer countPlace;  // Кол-во мест нужного типа
  private Integer price;       // Цена билета

  public String getNumber() {
    return number;
  }

  public String getName() {
    return name;
  }

  public String getStationFrom() {
    return stationFrom;
  }

  public Date getDatetimeFrom() {
    return datetimeFrom;
  }

  public Date getDatetimeTo() {
    return datetimeTo;
  }

  public String getStationTo() {
    return stationTo;
  }

  public String getTypePlace() {
    return typePlace;
  }

  public Integer getCountPlace() {
    return countPlace;
  }

  public Integer getPrice() {
    return price;
  }

  public Integer getId() {
    return id;
  }

  public Train withNumber(String number) {
    this.number = number;
    return this;
  }

  public Train withName(String name) {
    this.name = name;
    return this;
  }

  public Train withStationFrom(String stationFrom) {
    this.stationFrom = stationFrom;
    return this;
  }

  public Train withDateTimeFrom(Date dateTimeFrom) {
    this.datetimeFrom = dateTimeFrom;
    return this;
  }

  public Train withStationTo(String stationTo) {
    this.stationTo = stationTo;
    return this;
  }

  public Train withDateTimeTo(Date dateTimeTo) {
    this.datetimeTo = dateTimeTo;
    return this;
  }

  public Train withTypePlace(String typePlace) {
    this.typePlace = typePlace;
    return this;
  }

  public Train withCountPlace(Integer countPlace) {
    this.countPlace = countPlace;
    return this;
  }

  public Train withPrice(Integer price) {
    this.price = price;
    return this;
  }

  public Train withId(Integer id) {
    this.id = id;
    return this;
  }

}
