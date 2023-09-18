package com.safetynetapp.controllers;

import com.safetynetapp.models.PhoneAlertInfoResponse;

import com.safetynetapp.services.PhoneAlertService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


@RestController
public class PhoneAlertController {

  private final PhoneAlertService phoneAlertService;

  @Autowired
  public PhoneAlertController(PhoneAlertService phoneAlertService) {
    this.phoneAlertService = phoneAlertService;
  }

  @GetMapping("/phoneAlert")
  public ResponseEntity<Object> getPhoneAlertInfo(@RequestParam("firestation") int stationNumber) {
    PhoneAlertInfoResponse response = phoneAlertService.getPhoneAlertInfo(stationNumber);

    if (response == null) {
      return ResponseEntity.ok().body("{}");
    }

    return ResponseEntity.ok().body(response);
  }
}
