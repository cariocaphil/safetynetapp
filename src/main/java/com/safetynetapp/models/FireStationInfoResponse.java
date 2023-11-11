package com.safetynetapp.models;

import java.util.List;

public class FireStationInfoResponse {

  private List<com.safetynetapp.models.PersonInfo> peopleServiced;
  private com.safetynetapp.models.SummaryChildrenAndAdultsServiced summaryChildrenAndAdultsServiced;

  public List<PersonInfo> getPeopleServiced() {
    return peopleServiced;
  }

  public void setPeopleServiced(List<PersonInfo> peopleServiced) {
    this.peopleServiced = peopleServiced;
  }

  public SummaryChildrenAndAdultsServiced getSummaryChildrenAndAdultsServiced() {
    return summaryChildrenAndAdultsServiced;
  }

  public void setSummaryChildrenAndAdultsServiced(
      SummaryChildrenAndAdultsServiced summaryChildrenAndAdultsServiced) {
    this.summaryChildrenAndAdultsServiced = summaryChildrenAndAdultsServiced;
  }

}
