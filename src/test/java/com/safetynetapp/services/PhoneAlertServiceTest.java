package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.models.PhoneAlertInfoResponse;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PhoneAlertServiceTest {

  @Test
  void getPhoneAlertInfo() {
    // Mock DataLoader and DataUtils
    DataLoader dataLoader = Mockito.mock(DataLoader.class);
    DataUtils dataUtils = Mockito.mock(DataUtils.class);

    // Create instance of PhoneAlertService
    PhoneAlertService phoneAlertService = new PhoneAlertService(dataLoader, dataUtils);

    // Create test data
    String stationNumber = "1";
    List<Person> peopleServiced = new ArrayList<>();
    peopleServiced.add(new Person("John","Doe", "123 Main St", "Culver", "123", "123-456-7890", "john@example.com"));

    // Mock behavior of dataUtils
    when(dataUtils.getPeopleServicedByFireStation(stationNumber)).thenReturn(peopleServiced);

    // Test the method
    PhoneAlertInfoResponse response = phoneAlertService.getPhoneAlertInfo(stationNumber);

    assertNotNull(response);
    assertEquals(1, response.getPhoneNumbers().size());
    assertEquals("123-456-7890", response.getPhoneNumbers().get(0));

  }

  @Test
  void getPhoneAlertInfo_NoPeopleServiced() {
    // Mock DataLoader and DataUtils
    DataLoader dataLoader = Mockito.mock(DataLoader.class);
    DataUtils dataUtils = Mockito.mock(DataUtils.class);

    // Create instance of PhoneAlertService
    PhoneAlertService phoneAlertService = new PhoneAlertService(dataLoader, dataUtils);

    // Create test data
    String stationNumber = "1";

    // Mock behavior of dataUtils (no people serviced)
    when(dataUtils.getPeopleServicedByFireStation(stationNumber)).thenReturn(new ArrayList<>());

    // Test the method
    PhoneAlertInfoResponse response = phoneAlertService.getPhoneAlertInfo(stationNumber);

    assertNull(response);
  }
}
