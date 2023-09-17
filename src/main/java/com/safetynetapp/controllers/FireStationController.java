package com.safetynetapp.controllers;

import com.safetynetapp.models.FireStationInfoResponse;

import com.safetynetapp.services.FireStationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


@RestController
public class FireStationController {

  private final FireStationService fireStationService;

  @Autowired // injects the service instance into the controller
  public FireStationController(FireStationService fireStationService) {
    this.fireStationService = fireStationService;
  }

  @GetMapping("/firestation")
  public ResponseEntity<Object> getFireStationInfo(
      @RequestParam("stationNumber") int stationNumber) {
    FireStationInfoResponse response = fireStationService.getFireStationInfo(stationNumber);

    if (response == null) {
      return ResponseEntity.ok().body("{}");
    }

    return ResponseEntity.ok().body(response);
  }
}
