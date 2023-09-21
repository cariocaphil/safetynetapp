package com.safetynetapp.models;

import java.util.List;
import java.util.Map;

public class FloodInfoResponse {

  private Map<String, List<PersonWithAgeAndMedicalDetails>> listHouseHoldsAtStation;

  public Map<String, List<PersonWithAgeAndMedicalDetails>> getListHouseHoldsAtStation() {
    return listHouseHoldsAtStation;
  }

  public void setListHouseHoldsAtStation(
      Map<String, List<PersonWithAgeAndMedicalDetails>> listHouseHoldsAtStation) {
    this.listHouseHoldsAtStation = listHouseHoldsAtStation;
  }

}
