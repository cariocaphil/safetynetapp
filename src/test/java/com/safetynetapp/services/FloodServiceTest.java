package com.safetynetapp.services;

import com.safetynetapp.models.FloodInfoResponse;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonWithAgeAndMedicalDetails;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class FloodServiceTest {

  @Mock
  private DataLoader dataLoader;

  @Mock
  private DataUtils dataUtils;

  private FloodService floodService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    floodService = new FloodService();
    floodService.setDataLoader(dataLoader);
    floodService.setDataUtils(dataUtils);
  }

  @Test
  void testGetFloodInfo() {
    // Arrange
    List<Person> mockPeople = Arrays.asList(
        new Person("John", "Doe", "123 Main St", "Anytown", "12345", "555-555-5555", "john.doe@example.com"),
        new Person("Jane", "Doe", "123 Main St", "Anytown", "12345", "555-555-5555", "jane.doe@example.com"),
        new Person("Jim", "Smith", "456 Oak Ave", "Sometown", "67890", "555-555-5555", "jim.smith@example.com")
    );

    when(dataLoader.loadAllDataFromJson("persons", Person.class)).thenReturn(mockPeople);

    when(dataUtils.getPeopleServicedByFireStation("1")).thenReturn(Arrays.asList(
        mockPeople.get(0),
        mockPeople.get(1)
    ));

    when(dataUtils.getPeopleServicedByFireStation("2")).thenReturn(Arrays.asList(
        mockPeople.get(2)
    ));

    when(dataUtils.getPersonDetails(mockPeople.get(0))).thenReturn(
        new PersonWithAgeAndMedicalDetails("John", "Doe", "555-555-5555", 30, new ArrayList<>(), new ArrayList<>())
    );

    when(dataUtils.getPersonDetails(mockPeople.get(1))).thenReturn(
        new PersonWithAgeAndMedicalDetails("Jane", "Doe", "555-555-5555", 28, new ArrayList<>(), new ArrayList<>())
    );

    when(dataUtils.getPersonDetails(mockPeople.get(2))).thenReturn(
        new PersonWithAgeAndMedicalDetails("Jim", "Smith", "555-555-5555", 25, new ArrayList<>(), new ArrayList<>())
    );

    // Act
    FloodInfoResponse response = floodService.getFloodInfo("1,2");

    // Assert
    Map<String, List<PersonWithAgeAndMedicalDetails>> expectedHouseholds = new HashMap<>();
    expectedHouseholds.put("123 Main St", Arrays.asList(
        new PersonWithAgeAndMedicalDetails("John", "Doe", "555-555-5555", 30, new ArrayList<>(), new ArrayList<>()),
        new PersonWithAgeAndMedicalDetails("Jane", "Doe", "555-555-5555", 28, new ArrayList<>(), new ArrayList<>())
    ));
    expectedHouseholds.put("456 Oak Ave", Arrays.asList(
        new PersonWithAgeAndMedicalDetails("Jim", "Smith", "555-555-5555", 25, new ArrayList<>(), new ArrayList<>())
    ));

    assertEquals(expectedHouseholds, response.getListHouseholdsAtStation());
  }
}
