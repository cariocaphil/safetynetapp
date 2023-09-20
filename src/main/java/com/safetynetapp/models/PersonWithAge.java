package com.safetynetapp.models;

public class PersonWithAge extends Person {

  private int age;

  public PersonWithAge(Person person, int age) {
    super();
    this.setFirstName(person.getFirstName());
    this.setLastName(person.getLastName());
    this.age = age;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}