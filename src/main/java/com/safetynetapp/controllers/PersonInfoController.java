package com.safetynetapp.controllers;

import com.safetynetapp.models.PersonInfo;
import com.safetynetapp.models.PersonWithAgeAndMedicalDetails;
import com.safetynetapp.services.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonInfoController {

  private final PersonInfoService personInfoService;

  @Autowired
  public PersonInfoController(PersonInfoService personInfoService) {
    this.personInfoService = personInfoService;
  }

  @GetMapping("/personInfo")
  public ResponseEntity<List<PersonWithAgeAndMedicalDetails>> getPersonInfo(
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName) {

    List<PersonWithAgeAndMedicalDetails> personInfoList = personInfoService.getPersonInfo(firstName, lastName);

    if (personInfoList.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(personInfoList);
  }
}
