package com.safetynetapp.controllers;

import com.safetynetapp.models.ChildInfoResponse;
import com.safetynetapp.services.ChildAlertService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.tinylog.Logger;

@RestController
public class ChildAlertController {

  private final ChildAlertService childAlertService;

  @Autowired
  public ChildAlertController(ChildAlertService childAlertService) {
    this.childAlertService = childAlertService;
  }

  @GetMapping("/childAlert")
  public ResponseEntity<Object> getChildAlertInfo(
      @RequestParam("address") String address) {

    // Log the request
    Logger.info("Request received for address: {}", address);

    ChildInfoResponse response = childAlertService.getChildAlertInfo(address);

    if (response == null) {
      return ResponseEntity.ok().body("{}");
    }

    // Log the response
    Logger.info("Response: {}", response);

    return ResponseEntity.ok().body(response);
  }
}
