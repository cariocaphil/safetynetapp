package com.safetynetapp.models;

public class FireStationMappingUpdateRequest {

  private String address;
  private String station;

  public FireStationMappingUpdateRequest(String address, String station) {
    this.address = address;
    this.station = station;
  }

  public FireStationMappingUpdateRequest() {
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getStation() {
    return station;
  }

  public void setStation(String station) {
    this.station = station;
  }
}
