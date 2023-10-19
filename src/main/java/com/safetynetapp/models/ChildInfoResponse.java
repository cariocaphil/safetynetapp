package com.safetynetapp.models;

import java.util.List;

public class ChildInfoResponse {
  private List<SimpleChildInfo> children;
  private List<Person> otherPersons;

  public ChildInfoResponse(List<SimpleChildInfo> children, List<Person> otherPersons) {
    this.children = children;
    this.otherPersons = otherPersons;
  }

  public ChildInfoResponse() {

  }

  public List<SimpleChildInfo> getChildren() {
    return children;
  }

  public void setChildren(List<SimpleChildInfo> children) {
    this.children = children;
  }

  public List<Person> getOtherPersons() {
    return otherPersons;
  }

  public void setOtherPersons(List<Person> otherPersons) {
    this.otherPersons = otherPersons;
  }
}
