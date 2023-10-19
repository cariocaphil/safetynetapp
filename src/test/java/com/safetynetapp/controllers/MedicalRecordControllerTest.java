package com.safetynetapp.controllers;

import com.safetynetapp.controllers.MedicalRecordController;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.services.MedicalRecordService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalRecordControllerTest {

  @Mock
  private MedicalRecordService medicalRecordService;

  private MedicalRecordController medicalRecordController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    medicalRecordController = new MedicalRecordController(medicalRecordService);
  }

  @Test
  void addMedicalRecord() {
    // Arrange
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Doe");
    medicalRecord.setBirthdate("1990-05-15");
    medicalRecord.setMedications(Arrays.asList("Medication A", "Medication B"));
    medicalRecord.setAllergies(Arrays.asList("Peanuts", "Dust"));

    when(medicalRecordService.addMedicalRecord(medicalRecord)).thenReturn(true);

    // Act
    ResponseEntity<Object> result = medicalRecordController.addMedicalRecord(medicalRecord);

    // Assert
    assertEquals(ResponseEntity.ok().body("Medical record added successfully."), result);
  }


  @Test
  void updateMedicalRecord() {
    // Arrange
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Doe");
    medicalRecord.setBirthdate("1990-05-15");
    medicalRecord.setMedications(Arrays.asList("Medication A", "Medication B"));
    medicalRecord.setAllergies(Arrays.asList("Peanuts", "Dust"));

    when(medicalRecordService.updateMedicalRecord(medicalRecord)).thenReturn(true);

    // Act
    ResponseEntity<Object> result = medicalRecordController.updateMedicalRecord(medicalRecord);

    // Assert
    assertEquals(ResponseEntity.ok().body("Medical record updated successfully."), result);
  }

  @Test
  void deleteMedicalRecord() {
    // Arrange
    String firstName = "John";
    String lastName = "Doe";
    when(medicalRecordService.deleteMedicalRecord(firstName, lastName)).thenReturn(true);

    // Act
    ResponseEntity<Object> result = medicalRecordController.deleteMedicalRecord(firstName, lastName);

    // Assert
    assertEquals(ResponseEntity.ok().body("Medical record deleted successfully."), result);
  }
}
