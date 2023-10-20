package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonDetails;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PersonInfoServiceTest {

  @Mock
  private DataLoader dataLoader;

  @Mock
  private DataUtils dataUtils;

  private PersonInfoService personInfoService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    personInfoService = new PersonInfoService();
    personInfoService.setDataLoader(dataLoader);
    personInfoService.setDataUtils(dataUtils);
  }

  @Test
  void testGetPersonInfo() {
    // Arrange
    String firstName = "John";
    String lastName = "Doe";

    List<Person> mockPeople = new ArrayList<>();
    Person mockPerson = new Person(firstName, lastName, "123 Main St", "City", "12345", "555-555-5555", "john.doe@example.com");
    mockPeople.add(mockPerson);

    List<String> medications = Arrays.asList("Medication A", "Medication B");
    List<String> allergies = Arrays.asList("Allergy X", "Allergy Y");

    PersonDetails mockPersonDetails = new PersonDetails(firstName, lastName, "123 Main St", 30, "john.doe@example.com", medications, allergies);

    when(dataLoader.loadAllDataFromJson("persons", Person.class)).thenReturn(mockPeople);
    when(dataUtils.getPersonDetailsFor(mockPerson)).thenReturn(mockPersonDetails);

    // Act
    List<PersonDetails> result = personInfoService.getPersonInfo(firstName, lastName);

    // Assert
    assertEquals(1, result.size());
    assertEquals(mockPersonDetails, result.get(0));
  }
}
