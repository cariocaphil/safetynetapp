package com.safetynetapp.controllers;

import com.safetynetapp.models.ChildInfoResponse;
import com.safetynetapp.models.SimpleChildInfo;
import com.safetynetapp.services.ChildAlertService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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
    ChildInfoResponse response = childAlertService.getChildAlertInfo(address);

    if (response == null) {
      return ResponseEntity.ok().body("{}");
    }

    return ResponseEntity.ok().body(response);
  }
}
