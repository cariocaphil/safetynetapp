package com.safetynetapp.services;

import com.safetynetapp.models.FloodInfoResponse;
import com.safetynetapp.models.Person;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloodService {

  @Autowired
  private DataLoader dataLoader;

  @Autowired
  private DataUtils dataUtils;

  public static List<String> convertStationParamToList(String stationParam) {
    // Remove any trailing commas and split the string
    String[] stationArray = stationParam.replaceAll(",$", "").split(",");

    return Arrays.asList(stationArray);
  }

  public FloodInfoResponse getFloodInfo(String stationNumbers) {

    List<String> stations = convertStationParamToList(stationNumbers);
    List<Person> peopleServicedByStationNumbers = new ArrayList<>();
    for (String station : stations) {
      List<Person> peopleServiceByStation = dataUtils.getPeopleServicedByFireStation(station);
      peopleServicedByStationNumbers.addAll(peopleServiceByStation);
    }

    // group by address
    Map<String, List<Person>> groupedByAddress = new HashMap<>();
    for (Person person : peopleServicedByStationNumbers) {
      String address = person.getAddress();
      groupedByAddress.computeIfAbsent(address, k -> new ArrayList<>()).add(person);
    }

    FloodInfoResponse response = new FloodInfoResponse();
    response.setListHouseHoldsAtStation(groupedByAddress);

    return response;
  }

}