package com.safetynetapp.models;

import java.util.List;
import java.util.Map;

public class FloodInfoResponse {

  private Map<String, List<PersonWithAgeAndMedicalDetails>> listHouseholdsAtStation;

  public Map<String, List<PersonWithAgeAndMedicalDetails>> getListHouseholdsAtStation() {
    return listHouseholdsAtStation;
  }

  public void setListHouseholdsAtStation(
      Map<String, List<PersonWithAgeAndMedicalDetails>> listHouseholdsAtStation) {
    this.listHouseholdsAtStation = listHouseholdsAtStation;
  }

}
