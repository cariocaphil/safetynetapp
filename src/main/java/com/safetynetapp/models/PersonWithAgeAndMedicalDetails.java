package com.safetynetapp.models;

import java.util.List;
import java.util.Objects;

public class PersonWithAgeAndMedicalDetails {
  private String firstName;
  private String lastName;
  private String phone;
  private int age;
  private List<String> medications;
  private List<String> allergies;

  // Constructors, getters, and setters

  public PersonWithAgeAndMedicalDetails(String firstName, String lastName, String phone, int age, List<String> medications, List<String> allergies) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    PersonWithAgeAndMedicalDetails other = (PersonWithAgeAndMedicalDetails) obj;
    return firstName.equals(other.firstName) &&
        lastName.equals(other.lastName) &&
        phone.equals(other.phone) &&
        age == other.age &&
        medications.equals(other.medications) &&
        allergies.equals(other.allergies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, phone, age, medications, allergies);
  }
}
