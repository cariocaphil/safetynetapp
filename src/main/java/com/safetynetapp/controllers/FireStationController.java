package com.safetynetapp.controllers;

import com.safetynetapp.services.FireStationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class FireStationController {

  private final FireStationService fireStationService;

  @Autowired // injects the service instance into the controller
  public FireStationController(FireStationService fireStationService) {
    this.fireStationService = fireStationService;
  }

  @GetMapping("/firestation")
  public String getFireStationInfo(@RequestParam("stationNumber") int stationNumber) {
    String fireStationInfo = fireStationService.getFireStationInfo(stationNumber);
    return fireStationInfo;
  }

}
