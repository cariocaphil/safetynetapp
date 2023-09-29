package com.safetynetapp.controllers;

import com.safetynetapp.models.FloodInfoResponse;
import com.safetynetapp.services.FloodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

@RestController
public class FloodController {

  private final FloodService floodService;

  @Autowired
  public FloodController(FloodService floodService) {
    this.floodService = floodService;
  }

  @GetMapping("/flood/stations")
  public ResponseEntity<Object> getFloodInfo(@RequestParam("stations") String stationNumbers) {
    // Log the request
    Logger.info("Request received for flood info for stations: {}", stationNumbers);

    FloodInfoResponse response = floodService.getFloodInfo(stationNumbers);

    if (response == null) {
      // Log the response
      Logger.info("Response: {}","{}");
      return ResponseEntity.ok().body("{}");
    }

    // Log the response
    Logger.info("Response: {}", response);

    return ResponseEntity.ok().body(response);
  }
}
