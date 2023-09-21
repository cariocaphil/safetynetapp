package com.safetynetapp.models;

import java.util.List;
import java.util.Map;

public class FloodInfoResponse {

  private Map<String, List<Person>> listHouseHoldsAtStation;

  public Map<String, List<Person>> getListHouseHoldsAtStation() {
    return listHouseHoldsAtStation;
  }

  public void setListHouseHoldsAtStation(Map<String, List<Person>> listHouseHoldsAtStation) {
    this.listHouseHoldsAtStation = listHouseHoldsAtStation;
  }

}
