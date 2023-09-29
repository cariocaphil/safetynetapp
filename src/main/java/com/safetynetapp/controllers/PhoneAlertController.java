package com.safetynetapp.controllers;

import com.safetynetapp.models.PhoneAlertInfoResponse;
import com.safetynetapp.services.PhoneAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

@RestController
public class PhoneAlertController {

  private final PhoneAlertService phoneAlertService;

  @Autowired
  public PhoneAlertController(PhoneAlertService phoneAlertService) {
    this.phoneAlertService = phoneAlertService;
  }

  @GetMapping("/phoneAlert")
  public ResponseEntity<Object> getPhoneAlertInfo(@RequestParam("firestation") String stationNumber) {
    // Log the request
    Logger.info("Request received for phone alert info for firestation: {}", stationNumber);

    PhoneAlertInfoResponse response = phoneAlertService.getPhoneAlertInfo(stationNumber);

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
