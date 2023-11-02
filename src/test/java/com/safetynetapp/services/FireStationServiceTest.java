package com.safetynetapp.services;

import com.safetynetapp.models.FireStation;
import com.safetynetapp.models.FireStationInfoResponse;
import com.safetynetapp.models.FireStationMappingDeleteRequest;
import com.safetynetapp.models.FireStationMappingUpdateRequest;
import com.safetynetapp.models.Person;
import com.safetynetapp.services.FireStationService;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FireStationServiceTest {

  @Mock
  private DataLoader dataLoader;

  @Mock
  private DataUtils dataUtils;

  private FireStationService fireStationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    fireStationService = new FireStationService(dataLoader, dataUtils);
  }

  @Test
  void testGetFireStationInfo() {
    String stationNumber = "1";

    List<Person> mockListPeopleServiced = Collections.singletonList(
        new Person("John", "Doe", "123 Main St", "New York", "10001", "555-555-5555", "john.doe@example.com")
    );

    when(dataUtils.getPeopleServicedByFireStation(stationNumber)).thenReturn(mockListPeopleServiced);

    FireStationInfoResponse response = fireStationService.getFireStationInfo(stationNumber);

    assertNotNull(response);
    assertEquals(1, response.getPeopleServiced().size());
    assertEquals("123 Main St", response.getPeopleServiced().get(0).getAddress());
  }

  @Test
  void testAddFireStation() {
    FireStation fireStation = new FireStation("123 Main St", "Station 1");

    List<FireStation> mockFireStations = Collections.emptyList();
    when(dataLoader.loadAllDataFromJson("firestations", FireStation.class)).thenReturn(mockFireStations);

    boolean result = fireStationService.addFireStation(fireStation);

    assertTrue(result);
  }

  @Test
  void testUpdateFireStation() {
    FireStationMappingUpdateRequest updateRequest = new FireStationMappingUpdateRequest("123 Main St", "Station 2");

    List<FireStation> mockFireStations = Collections.singletonList(
        new FireStation("123 Main St", "Station 1")
    );

    when(dataLoader.loadAllDataFromJson("firestations", FireStation.class)).thenReturn(mockFireStations);

    boolean result = fireStationService.updateFireStation(updateRequest);

    assertTrue(result);
  }

  @Test
  void testDeleteFireStationMappingNonExistentAddress() {
    String nonExistentAddress = "Non Existent Address";

    // Initialize the FireStationMappingDeleteRequest with a non-existent address
    FireStationMappingDeleteRequest request = new FireStationMappingDeleteRequest();
    request.setAddress(nonExistentAddress);

    // Call the deleteFireStationMapping method with the initialized request
    boolean result = fireStationService.deleteFireStationMapping(request);

    assertFalse(result, "Expected deleteFireStationMapping to return false for non-existent address");
  }
}
