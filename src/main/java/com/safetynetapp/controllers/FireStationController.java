package com.safetynetapp.controllers;

import com.safetynetapp.services.FireStationService;
import com.safetynetapp.models.Person;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

@RestController
public class FireStationController {

  private final FireStationService fireStationService;

  @Autowired // injects the service instance into the controller
  public FireStationController(FireStationService fireStationService) {
    this.fireStationService = fireStationService;
  }

  @GetMapping("/firestation")
  public String getFireStationInfo(@RequestParam("stationNumber") int stationNumber) {
    List<Person> fireStationInfo = fireStationService.getPeopleServicedByFireStation(stationNumber);

    return convertPersonListToString(fireStationInfo);
  }

  public String convertPersonListToString(List<Person> personList) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(personList);
    } catch (Exception e) {
      e.printStackTrace();
      return null; // Handle exception appropriately
    }
  }
}
