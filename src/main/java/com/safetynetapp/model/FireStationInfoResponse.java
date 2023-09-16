package com.safetynetapp.models;
import com.safetynetapp.models.Person;

import java.util.List;


public class FireStationInfoResponse {
  private List<Person> peopleServiced;
  private String numberChildrenAndAdultsServiced;

  public List<Person> getPeopleServiced() {
    return peopleServiced;
  }

  public void setPeopleServiced(List<Person> peopleServiced) {
    this.peopleServiced = peopleServiced;
  }

  public String getNumberChildrenAndAdultsServiced() {
    return numberChildrenAndAdultsServiced;
  }

  public void setNumberChildrenAndAdultsServiced(String numberChildrenAndAdultsServiced) {
    this.numberChildrenAndAdultsServiced = numberChildrenAndAdultsServiced;
  }
}