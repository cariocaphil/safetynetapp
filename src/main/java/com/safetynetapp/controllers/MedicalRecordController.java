package com.safetynetapp.controllers;

import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.tinylog.Logger;

@RestController
public class MedicalRecordController {

  private final MedicalRecordService medicalRecordService;

  @Autowired
  public MedicalRecordController(MedicalRecordService medicalRecordService) {
    this.medicalRecordService = medicalRecordService;
  }

  @PostMapping("/medicalRecord")
  public ResponseEntity<Object> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    // Log the request
    Logger.info("Request received to add medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

    boolean success = medicalRecordService.addMedicalRecord(medicalRecord);
    if (success) {
      return ResponseEntity.ok().body("Medical record added successfully.");
    } else {
      return new ResponseEntity<>("Medical record not found", HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/medicalRecord")
  public ResponseEntity<Object> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    // Log the request
    Logger.info("Request received to update medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

    boolean success = medicalRecordService.updateMedicalRecord(medicalRecord);
    if (success) {
      return ResponseEntity.ok().body("Medical record updated successfully.");
    } else {
      return new ResponseEntity<>("Medical record not found", HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/medicalRecord")
  public ResponseEntity<Object> deleteMedicalRecord(@RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName) {
    // Log the request
    Logger.info("Request received to delete medical record for: {} {}", firstName, lastName);

    boolean success = medicalRecordService.deleteMedicalRecord(firstName, lastName);
    if (success) {
      return ResponseEntity.ok().body("Medical record deleted successfully.");
    } else {
      return new ResponseEntity<>("Medical record not found", HttpStatus.NOT_FOUND);
    }
  }
}
