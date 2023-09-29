package com.safetynetapp.controllers;

import com.safetynetapp.models.Person;
import com.safetynetapp.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

@RestController
public class PersonController {

  private final PersonService personService;

  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  @PostMapping("/person")
  public ResponseEntity<String> addPerson(@RequestBody Person person) {
    // Log the request
    Logger.info("Request received to add person: {} {}", person.getFirstName(), person.getLastName());

    boolean success = personService.addPerson(person);

    if (success) {
      return ResponseEntity.ok("Person added successfully.");
    } else {
      return ResponseEntity.badRequest().body("Failed to add person.");
    }
  }

  @PutMapping("/person")
  public ResponseEntity<String> updatePerson(@RequestBody Person person) {
    // Log the request
    Logger.info("Request received to update person: {} {}", person.getFirstName(), person.getLastName());

    boolean success = personService.updatePerson(person);

    if (success) {
      return ResponseEntity.ok("Person updated successfully.");
    } else {
      return ResponseEntity.badRequest().body("Failed to update person.");
    }
  }

  @DeleteMapping("/person")
  public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
    // Log the request
    Logger.info("Request received to delete person: {} {}", firstName, lastName);

    boolean success = personService.deletePerson(firstName, lastName);

    if (success) {
      return ResponseEntity.ok("Person deleted successfully.");
    } else {
      return ResponseEntity.badRequest().body("Failed to delete person.");
    }
  }
}
