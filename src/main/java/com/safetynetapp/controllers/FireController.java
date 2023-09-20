package com.safetynetapp.controllers;

import com.safetynetapp.models.FireStationInfoResponse.FireInfoResponse;
import com.safetynetapp.services.FireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireController {

  private final FireService fireService;

  @Autowired
  public FireController(FireService fireService) {
    this.fireService = fireService;
  }

  @GetMapping("/fire")
  public ResponseEntity<Object> getFireInfo(
      @RequestParam("address") String address) {
    FireInfoResponse response = fireService.getFireInfo(address);

    if (response == null) {
      return ResponseEntity.ok().body("{}");
    }

    return ResponseEntity.ok().body(response);
  }
}