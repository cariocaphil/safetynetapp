package com.safetynetapp.services;

import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.utilities.Constants;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MedicalRecordServiceTest {

  @Mock
  private DataLoader dataLoader;

  @Mock
  private DataUtils dataUtils;

  private MedicalRecordService medicalRecordService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    medicalRecordService = new MedicalRecordService();
    medicalRecordService.setDataLoader(dataLoader);
    medicalRecordService.setDataUtils(dataUtils);
  }

  @Test
  void testAddMedicalRecord() {
    // Arrange
    MedicalRecord mockRecord = new MedicalRecord();
    mockRecord.setFirstName("John");
    mockRecord.setLastName("Doe");
    mockRecord.setBirthdate("01/01/1980");

    List<MedicalRecord> mockMedicalRecords = new ArrayList<>();
    when(dataLoader.loadAllDataFromJson(Constants.MEDICAL_RECORDS, MedicalRecord.class)).thenReturn(mockMedicalRecords);

    // Act
    boolean added = medicalRecordService.addMedicalRecord(mockRecord);

    // Assert
    assertTrue(added);
    assertEquals(1, mockMedicalRecords.size());
    assertEquals(mockRecord, mockMedicalRecords.get(0));
  }

  @Test
  void testAddMedicalRecordAlreadyExists() {
    // Arrange
    MedicalRecord mockRecord = new MedicalRecord();
    mockRecord.setFirstName("John");
    mockRecord.setLastName("Doe");
    mockRecord.setBirthdate("01/01/1980");

    List<MedicalRecord> mockMedicalRecords = new ArrayList<>();
    mockMedicalRecords.add(mockRecord);
    when(dataLoader.loadAllDataFromJson(Constants.MEDICAL_RECORDS, MedicalRecord.class)).thenReturn(mockMedicalRecords);

    // Act
    boolean added = medicalRecordService.addMedicalRecord(mockRecord);

    // Assert
    assertFalse(added);
    assertEquals(1, mockMedicalRecords.size());
  }

  @Test
  void testUpdateMedicalRecord() {
    // Arrange
    MedicalRecord existingRecord = new MedicalRecord();
    existingRecord.setFirstName("John");
    existingRecord.setLastName("Doe");
    existingRecord.setBirthdate("01/01/1980");

    MedicalRecord updatedRecord = new MedicalRecord();
    updatedRecord.setFirstName("John");
    updatedRecord.setLastName("Doe");
    updatedRecord.setBirthdate("01/01/1980");
    updatedRecord.setMedications(Arrays.asList("Medication A", "Medication B"));
    updatedRecord.setAllergies(Arrays.asList("Allergy X", "Allergy Y"));

    List<MedicalRecord> mockMedicalRecords = new ArrayList<>();
    mockMedicalRecords.add(existingRecord);
    when(dataLoader.loadAllDataFromJson(Constants.MEDICAL_RECORDS, MedicalRecord.class)).thenReturn(mockMedicalRecords);

    // Act
    boolean updated = medicalRecordService.updateMedicalRecord(updatedRecord);

    // Assert
    assertTrue(updated);
    assertEquals(updatedRecord.getMedications(), existingRecord.getMedications());
    assertEquals(updatedRecord.getAllergies(), existingRecord.getAllergies());
  }

  @Test
  void testUpdateMedicalRecordNotFound() {
    // Arrange
    MedicalRecord updatedRecord = new MedicalRecord();
    updatedRecord.setFirstName("John");
    updatedRecord.setLastName("Doe");
    updatedRecord.setBirthdate("01/01/1980");

    List<MedicalRecord> mockMedicalRecords = new ArrayList<>();
    when(dataLoader.loadAllDataFromJson(Constants.MEDICAL_RECORDS, MedicalRecord.class)).thenReturn(mockMedicalRecords);

    // Act
    boolean updated = medicalRecordService.updateMedicalRecord(updatedRecord);

    // Assert
    assertFalse(updated);
  }

  @Test
  void testDeleteMedicalRecord() {
    // Arrange
    MedicalRecord mockRecord = new MedicalRecord();
    mockRecord.setFirstName("John");
    mockRecord.setLastName("Doe");
    mockRecord.setBirthdate("01/01/1980");

    List<MedicalRecord> mockMedicalRecords = new ArrayList<>();
    mockMedicalRecords.add(mockRecord);
    when(dataLoader.loadAllDataFromJson(Constants.MEDICAL_RECORDS, MedicalRecord.class)).thenReturn(mockMedicalRecords);

    // Act
    boolean deleted = medicalRecordService.deleteMedicalRecord("John", "Doe");

    // Assert
    assertTrue(deleted);
    assertEquals(0, mockMedicalRecords.size());
  }

  @Test
  void testDeleteMedicalRecordNotFound() {
    // Arrange
    List<MedicalRecord> mockMedicalRecords = new ArrayList<>();
    when(dataLoader.loadAllDataFromJson(Constants.MEDICAL_RECORDS, MedicalRecord.class)).thenReturn(mockMedicalRecords);

    // Act
    boolean deleted = medicalRecordService.deleteMedicalRecord("John", "Doe");

    // Assert
    assertFalse(deleted);
  }
}
