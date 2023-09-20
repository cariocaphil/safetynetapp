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

  public static class FireInfoResponse {

    private Integer stationNumber;

    private List<PersonWithAgeAndMedicalDetails> peopleLivingAtAddress;

    public Integer getStationNumber() {
      return stationNumber;
    }

    public void setStationNumber(Integer stationNumber) {
      this.stationNumber = stationNumber;
    }

    public List<PersonWithAgeAndMedicalDetails> getPeopleLivingAtAddress() {
      return peopleLivingAtAddress;
    }

    public void setPeopleLivingAtAddress(List<PersonWithAgeAndMedicalDetails> peopleLivingAtAddress) {
      this.peopleLivingAtAddress = peopleLivingAtAddress;
    }
  }

  public static class PersonWithAgeAndMedicalDetails {
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
}
