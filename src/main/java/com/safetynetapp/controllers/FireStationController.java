package com.safetynetapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

  @GetMapping("/firestation")
  public String getFireStationInfo(@RequestParam("stationNumber") int stationNumber) {
    return "Fire station " + stationNumber + " info";
  }
}
