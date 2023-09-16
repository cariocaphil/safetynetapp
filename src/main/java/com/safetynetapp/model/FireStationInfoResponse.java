package com.safetynetapp.models;
import com.safetynetapp.models.PersonInfo;

import java.util.List;


public class FireStationInfoResponse {
  private List<PersonInfo> peopleServiced;
  private String numberChildrenAndAdultsServiced;

  public List<PersonInfo> getPeopleServiced() {
    return peopleServiced;
  }

  public void setPeopleServiced(List<PersonInfo> peopleServiced) {
    this.peopleServiced = peopleServiced;
  }

  public String getNumberChildrenAndAdultsServiced() {
    return numberChildrenAndAdultsServiced;
  }

  public void setNumberChildrenAndAdultsServiced(String numberChildrenAndAdultsServiced) {
    this.numberChildrenAndAdultsServiced = numberChildrenAndAdultsServiced;
  }
}