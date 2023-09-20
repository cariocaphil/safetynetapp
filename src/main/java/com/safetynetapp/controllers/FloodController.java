package com.safetynetapp.controllers;

import com.safetynetapp.models.FloodInfoResponse;
import com.safetynetapp.services.FloodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FloodController {

  private final FloodService floodService;

  @Autowired
  public FloodController(FloodService floodService) {
    this.floodService = floodService;
  }

  @GetMapping("/flood/stations")
  public ResponseEntity<Object> getFloodInfo(
      @RequestParam("stations") String stationNumbers) {
    FloodInfoResponse response = floodService.getFloodInfo(address);

    if (response == null) {
      return ResponseEntity.ok().body("{}");
    }

    return ResponseEntity.ok().body(response);
  }
}