package ru.stqa.pft.rzd.model;

/**
 * Created by manuhin on 13.04.2016.
 */
public class Train {
  // запись по поезду: xpath = //*[@class="trlist__trlist-row trslot "]
  private String number;      // номер поезда
  private String name;        // наименование поезда
  private String stationFrom; // станция отправления
  private String dateFrom;    // дата отправления
  private String timeFrom;    // время отправления
  private String stationTo;   // станция назначения
  private String dateTo;      // дата прибытия
  private String timeTo;      // время прибытия
  private String typePlace;   // тип вагона (сидячий, купе, св, плацкарт...)
  private String countPlace;  // Кол-во мест нужного типа
  private String price;       // Цена билета

  public String getNumber() {
    return number;
  }

  public String getName() {
    return name;
  }

  public String getStationFrom() {
    return stationFrom;
  }

  public String getDateFrom() {
    return dateFrom;
  }

  public String getTimeFrom() {
    return timeFrom;
  }

  public String getDateTo() {
    return dateTo;
  }

  public String getStationTo() {
    return stationTo;
  }

  public String getTimeTo() {
    return timeTo;
  }

  public String getTypePlace() {
    return typePlace;
  }

  public String getCountPlace() {
    return countPlace;
  }

  public String getPrice() {
    return price;
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

  public Train withDateFrom(String dateFrom) {
    this.dateFrom = dateFrom;
    return this;
  }

  public Train withTimeFrom(String timeFrom) {
    this.timeFrom = timeFrom;
    return this;
  }

  public Train withStationTo(String stationTo) {
    this.stationTo = stationTo;
    return this;
  }

  public Train withDateTo(String dateTo) {
    this.dateTo = dateTo;
    return this;
  }

  public Train withTimeTo(String timeTo) {
    this.timeTo = timeTo;
    return this;
  }

  public Train withTypePlace(String typePlace) {
    this.typePlace = typePlace;
    return this;
  }

  public Train withCountPlace(String countPlace) {
    this.countPlace = countPlace;
    return this;
  }

  public Train withPrice(String price) {
    this.price = price;
    return this;
  }
}
