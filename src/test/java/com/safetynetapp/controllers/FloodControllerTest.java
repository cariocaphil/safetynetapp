package com.safetynetapp.controllers;

import com.safetynetapp.controllers.FloodController;
import com.safetynetapp.models.FloodInfoResponse;
import com.safetynetapp.services.FloodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FloodControllerTest {

  @Mock
  private FloodService floodService;

  private FloodController floodController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    floodController = new FloodController(floodService);
  }

  @Test
  void getFloodInfo() {
    // Arrange
    String stationNumbers = "1,2,3";
    FloodInfoResponse response = new FloodInfoResponse();
    when(floodService.getFloodInfo(stationNumbers)).thenReturn(response);

    // Act
    ResponseEntity<Object> result = floodController.getFloodInfo(stationNumbers);

    // Assert
    assertEquals(ResponseEntity.ok().body(response), result);
  }
}
