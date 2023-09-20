package com.safetynetapp.model;

import java.util.List;

public class PersonWithAgeAndMedicalDetails {
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private int age;
  private List<String> medications;
  private List<String> allergies;

  public PersonWithAgeAndMedicalDetails(String firstName, String lastName, String phone, int age, List<String> medications, List<String> allergies) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phone;
    this.age = age;
    this.medications = medications;
    this.allergies = allergies;
  }


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public List<String> getMedications() {
    return medications;
  }

  public void setMedications(List<String> medications) {
    this.medications = medications;
  }

  public List<String> getAllergies() {
    return allergies;
  }

  public void setAllergies(List<String> allergies) {
    this.allergies = allergies;
  }
}
