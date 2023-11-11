package com.safetynetapp.services;

import com.safetynetapp.models.FloodInfoResponse;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonWithAgeAndMedicalDetails;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    List<Person> peopleServicedByStationNumbers = getPeopleServicedByStations(stations);

    // group by address
    Map<String, List<PersonWithAgeAndMedicalDetails>> groupedByAddress = new HashMap<>();
    for (Person person : peopleServicedByStationNumbers) {
      String address = person.getAddress();

      PersonWithAgeAndMedicalDetails personDetails = dataUtils.getPersonDetails(person);

      groupedByAddress.computeIfAbsent(address, k -> new ArrayList<>()).add(personDetails);
    }

    FloodInfoResponse response = new FloodInfoResponse();
    response.setListHouseholdsAtStation(groupedByAddress);

    Logger.info("Flood info retrieved for stations: {}", stationNumbers);

    return response;
  }

  private List<Person> getPeopleServicedByStations(List<String> stationNumbers) {
    List<Person> peopleServicedByStations = new ArrayList<>();

    for (String station : stationNumbers) {
      List<Person> peopleServicedByStation = dataUtils.getPeopleServicedByFireStation(station);
      peopleServicedByStations.addAll(peopleServicedByStation);
    }

    return peopleServicedByStations;
  }

  public void setDataLoader(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
  }

  public void setDataUtils(DataUtils dataUtils) {
    this.dataUtils = dataUtils;
  }
}
