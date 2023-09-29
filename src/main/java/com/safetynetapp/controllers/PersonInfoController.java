package com.safetynetapp.controllers;

import com.safetynetapp.models.PersonDetails;
import com.safetynetapp.models.PersonWithAgeAndMedicalDetails;
import com.safetynetapp.services.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.util.List;

@RestController
public class PersonInfoController {

  private final PersonInfoService personInfoService;

  @Autowired
  public PersonInfoController(PersonInfoService personInfoService) {
    this.personInfoService = personInfoService;
  }

  @GetMapping("/personInfo")
  public ResponseEntity<List<PersonDetails>> getPersonInfo(
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName) {
    // Log the request
    Logger.info("Request received for person info for: {} {}", firstName, lastName);

    List<PersonDetails> personInfoList = personInfoService.getPersonInfo(firstName, lastName);

    if (personInfoList.isEmpty()) {
      // Log the response
      Logger.info("No person info found for: {} {}", firstName, lastName);
      return ResponseEntity.noContent().build();
    }

    // Log the response
    Logger.info("Person info found for: {} {}", firstName, lastName);
    return ResponseEntity.ok(personInfoList);
  }
}
