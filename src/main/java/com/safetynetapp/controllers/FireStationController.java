package com.safetynetapp.controllers;

import com.safetynetapp.models.FireStation;
import com.safetynetapp.models.FireStationInfoResponse;
import com.safetynetapp.models.FireStationMappingDeleteRequest;
import com.safetynetapp.models.FireStationMappingUpdateRequest;
import com.safetynetapp.services.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    FireStationInfoResponse response = fireStationService.getFireStationInfo(stationNumber);

    if (response == null) {
      return ResponseEntity.ok().body("{}");
    }

    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/firestation")
  public ResponseEntity<String> addFireStation(@RequestBody FireStation fireStation) {
    boolean success = fireStationService.addFireStation(fireStation);
    if (success) {
      return ResponseEntity.ok("Fire station added successfully");
    } else {
      return ResponseEntity.badRequest().body("Fire station with the given number already exists");
    }
  }

  @PutMapping("/firestation")
  public ResponseEntity<Object> updateFireStation(
      @RequestBody FireStationMappingUpdateRequest request) {
    boolean success = fireStationService.updateFireStation(request);
    if (success) {
      return ResponseEntity.ok().body("Fire station mapping updated successfully");
    } else {
      return ResponseEntity.badRequest().body("Fire station with the given number already exists");
    }
  }

  @DeleteMapping("/firestation")
  public ResponseEntity<String> deleteFireStationMapping(
      @RequestBody FireStationMappingDeleteRequest request) {
    boolean success = fireStationService.deleteFireStationMapping(request);
    if (success) {
      return ResponseEntity.ok("Fire station mapping deleted successfully");
    } else {
      return new ResponseEntity<>("Fire station or address not found", HttpStatus.NOT_FOUND);
    }
  }
}
