package com.safetynetapp.models;

import java.util.List;

public class ChildInfoResponse {
  private List<PersonWithAge> children;
  private List<Person> otherPersons;

  public ChildInfoResponse(List<PersonWithAge> children, List<Person> otherPersons) {
    this.children = children;
    this.otherPersons = otherPersons;
  }

  public List<PersonWithAge> getChildren() {
    return children;
  }

  public void setChildren(List<PersonWithAge> children) {
    this.children = children;
  }

  public List<Person> getOtherPersons() {
    return otherPersons;
  }

  public void setOtherPersons(List<Person> otherPersons) {
    this.otherPersons = otherPersons;
  }
}
