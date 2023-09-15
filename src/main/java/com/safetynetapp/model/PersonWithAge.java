package com.safetynetapp.models;
import com.safetynetapp.models.Person;

public class PersonWithAge extends Person {

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  int age;

  public PersonWithAge(Person person, int age) {
    super();
    this.age = age;
  }
}