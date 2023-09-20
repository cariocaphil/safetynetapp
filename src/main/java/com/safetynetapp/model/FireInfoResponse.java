package com.safetynetapp.model;

import java.util.List;

public class FireInfoResponse {

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
