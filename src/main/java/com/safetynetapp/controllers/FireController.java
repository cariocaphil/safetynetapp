package com.safetynetapp.controllers;

import com.safetynetapp.models.FireInfoResponse;
import com.safetynetapp.services.interfaces.FireInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

@RestController
public class FireController {

  private final FireInfoService fireService;

  @Autowired
  public FireController(FireInfoService fireService) {
    this.fireService = fireService;
  }

  @GetMapping("/fire")
  public ResponseEntity<Object> getFireInfo(
      @RequestParam("address") String address) {
    // Log the request
    Logger.info("Request received for address: {}", address);

    FireInfoResponse response = fireService.getFireInfo(address);

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