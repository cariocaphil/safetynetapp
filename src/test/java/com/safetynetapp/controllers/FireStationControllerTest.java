package com.safetynetapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetapp.models.FireStation;
import com.safetynetapp.models.FireStationInfoResponse;
import com.safetynetapp.models.FireStationMappingDeleteRequest;
import com.safetynetapp.models.FireStationMappingUpdateRequest;
import com.safetynetapp.services.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FireStationControllerTest {

  @Mock
  private FireStationService fireStationService;

  private FireStationController fireStationController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    fireStationController = new FireStationController(fireStationService);
  }

  @Test
  void getFireStationInfo() {
    // Arrange
    String stationNumber = "1";
    FireStationInfoResponse response = new FireStationInfoResponse();
    when(fireStationService.getFireStationInfo(stationNumber)).thenReturn(response);

    // Act
    ResponseEntity<Object> result = fireStationController.getFireStationInfo(stationNumber);

    // Assert
    assertEquals(ResponseEntity.ok().body(response), result);
  }

  @Test
  void addFireStation() {
    // Arrange
    FireStation fireStation = new FireStation();
    fireStation.setStation("1");
    when(fireStationService.addFireStation(fireStation)).thenReturn(true);

    // Act
    ResponseEntity<String> result = fireStationController.addFireStation(fireStation);

    // Assert
    assertEquals(ResponseEntity.ok("Fire station added successfully"), result);
  }

  @Test
  void updateFireStation() {
    // Arrange
    FireStationMappingUpdateRequest request = new FireStationMappingUpdateRequest();
    request.setStation("1");
    when(fireStationService.updateFireStation(request)).thenReturn(true);

    // Act
    ResponseEntity<Object> result = fireStationController.updateFireStation(request);

    // Assert
    assertEquals(ResponseEntity.ok().body("Fire station mapping updated successfully"), result);
  }

  @Test
  void deleteFireStationMapping() {
    // Arrange
    FireStationMappingDeleteRequest request = new FireStationMappingDeleteRequest();
    request.setAddress("123 Main St");
    when(fireStationService.deleteFireStationMapping(request)).thenReturn(true);

    // Act
    ResponseEntity<String> result = fireStationController.deleteFireStationMapping(request);

    // Assert
    assertEquals(ResponseEntity.ok("Fire station mapping deleted successfully"), result);
  }
}
