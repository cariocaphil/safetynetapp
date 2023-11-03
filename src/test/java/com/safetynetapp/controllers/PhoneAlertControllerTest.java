package com.safetynetapp.controllers;

import com.safetynetapp.models.PhoneAlertInfoResponse;
import com.safetynetapp.services.PhoneAlertService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhoneAlertControllerTest {

  @Mock
  private PhoneAlertService phoneAlertService = Mockito.mock(PhoneAlertService.class);

  @Test
  void getPhoneAlertInfo() {
    PhoneAlertController controller = new PhoneAlertController(phoneAlertService);

    // Mocking the service response
    String stationNumber = "1";
    PhoneAlertInfoResponse mockResponse = new PhoneAlertInfoResponse();
    mockResponse.setPhoneNumbers(Mockito.mock(List.class));

    Mockito.when(phoneAlertService.getPhoneAlertInfo(stationNumber)).thenReturn(mockResponse);

    ResponseEntity<Object> responseEntity = controller.getPhoneAlertInfo(stationNumber);

    // Verify the response
    assertEquals(200, responseEntity.getStatusCodeValue());
    assertEquals(mockResponse, responseEntity.getBody());
  }
}
