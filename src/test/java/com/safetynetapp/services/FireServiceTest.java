package com.safetynetapp.services;

import com.safetynetapp.models.*;
import com.safetynetapp.models.FireStationInfoResponse.FireInfoResponse;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import com.safetynetapp.utilities.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class FireServiceTest {

  @Mock
  private DataLoader dataLoader;

  @Mock
  private DataUtils dataUtils;

  private FireService fireService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    fireService = new FireService();
    fireService.setDataLoader(dataLoader);
    fireService.setDataUtils(dataUtils);
  }

  @Test
  void testGetFireInfo() {
    // Arrange
    String address = "123 Main St";

    // Mock dataUtils behavior
    when(dataUtils.getStationForAddress(address)).thenReturn(1);

    List<Person> peopleLivingAtAddress = new ArrayList<>();
    peopleLivingAtAddress.add(new Person("John", "Doe", address, "Anytown", "12345", "555-555-5555", "john.doe@example.com"));
    when(dataUtils.getPeopleLivingAtAddress(address)).thenReturn(peopleLivingAtAddress);

    // Mock dataLoader behavior
    List<MedicalRecord> medicalRecords = new ArrayList<>();
    medicalRecords.add(new MedicalRecord("John", "Doe", "01/01/2005", new ArrayList<>(), new ArrayList<>()));



    when(dataLoader.loadAllDataFromJson("medicalrecords", MedicalRecord.class)).thenReturn(medicalRecords);

    // Act
    FireInfoResponse fireInfoResponse = fireService.getFireInfo(address);

    // Assert
    assertNotNull(fireInfoResponse);
    assertEquals(1, fireInfoResponse.getStationNumber());

    List<PersonWithAgeAndMedicalDetails> expectedPeopleList = new ArrayList<>();
    expectedPeopleList.add(new PersonWithAgeAndMedicalDetails("John", "Doe", "555-555-5555", 18, new ArrayList<>(), new ArrayList<>()));
    assertEquals(expectedPeopleList, fireInfoResponse.getPeopleLivingAtAddress());
  }

  @Test
  void testGetFireInfoNullStation() {
    // Arrange
    String address = "123 Main St";

    // Mock dataUtils behavior to return null for getStationForAddress
    when(dataUtils.getStationForAddress(address)).thenReturn(null);

    // Act
    FireInfoResponse fireInfoResponse = fireService.getFireInfo(address);

    // Assert
    assertNull(fireInfoResponse);
  }
}
