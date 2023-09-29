package com.safetynetapp.controllers;

import com.safetynetapp.models.FireStation;
import com.safetynetapp.models.FireStationInfoResponse;
import com.safetynetapp.models.FireStationMappingDeleteRequest;
import com.safetynetapp.models.FireStationMappingUpdateRequest;
import com.safetynetapp.services.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.tinylog.Logger;

@RestController
public class FireStationController {

  private final FireStationService fireStationService;

  @Autowired
  public FireStationController(FireStationService fireStationService) {
    this.fireStationService = fireStationService;
  }

  @GetMapping("/firestation")
  public ResponseEntity<Object> getFireStationInfo(
      @RequestParam("stationNumber") String stationNumber) {
    // Log the request
    Logger.info("Request received for fire station info with station number: {}", stationNumber);

    FireStationInfoResponse response = fireStationService.getFireStationInfo(stationNumber);

    if (response == null) {
      // Log the response
      Logger.info("Response: {}","{}");
      return ResponseEntity.ok().body("{}");
    }

    // Log the response
    Logger.info("Response: {}", response);

    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/firestation")
  public ResponseEntity<String> addFireStation(@RequestBody FireStation fireStation) {
    // Log the request
    Logger.info("Request received to add fire station with station number: {}", fireStation.getStation());

    boolean success = fireStationService.addFireStation(fireStation);
    if (success) {
      // Log the response
      Logger.info("Fire station added successfully");
      return ResponseEntity.ok("Fire station added successfully");
    } else {
      // Log the response
      Logger.info("Fire station with the given number already exists");
      return ResponseEntity.badRequest().body("Fire station with the given number already exists");
    }
  }

  @PutMapping("/firestation")
  public ResponseEntity<Object> updateFireStation(
      @RequestBody FireStationMappingUpdateRequest request) {
    // Log the request
    Logger.info("Request received to update fire station mapping for station number: {}", request.getStation());

    boolean success = fireStationService.updateFireStation(request);
    if (success) {
      // Log the response
      Logger.info("Fire station mapping updated successfully");
      return ResponseEntity.ok().body("Fire station mapping updated successfully");
    } else {
      // Log the response
      Logger.info("Fire station with the given number already exists");
      return ResponseEntity.badRequest().body("Fire station with the given number already exists");
    }
  }

  @DeleteMapping("/firestation")
  public ResponseEntity<String> deleteFireStationMapping(
      @RequestBody FireStationMappingDeleteRequest request) {
    // Log the request
    Logger.info("Request received to delete fire station mapping for address: {}", request.getAddress());

    boolean success = fireStationService.deleteFireStationMapping(request);
    if (success) {
      // Log the response
      Logger.info("Fire station mapping deleted successfully");
      return ResponseEntity.ok("Fire station mapping deleted successfully");
    } else {
      // Log the response
      Logger.info("Fire station or address not found");
      return new ResponseEntity<>("Fire station or address not found", HttpStatus.NOT_FOUND);
    }
  }
}
