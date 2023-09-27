package com.safetynetapp.controllers;

import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {

  private final MedicalRecordService medicalRecordService;

  @Autowired
  public MedicalRecordController(MedicalRecordService medicalRecordService) {
    this.medicalRecordService = medicalRecordService;
  }

  @PostMapping("/medicalRecord")
  public ResponseEntity<Object> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    boolean success = medicalRecordService.addMedicalRecord(medicalRecord);
    if (success) {
      return ResponseEntity.ok().body("Medical record added successfully.");
    } else {
      return new ResponseEntity<>("Medical record not found", HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/medicalRecord")
  public ResponseEntity<Object> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
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
    boolean success = medicalRecordService.deleteMedicalRecord(firstName, lastName);
    if (success) {
      return ResponseEntity.ok().body("Medical record deleted successfully.");
    } else {
      return new ResponseEntity<>("Medical record not found", HttpStatus.NOT_FOUND);
    }
  }
}
