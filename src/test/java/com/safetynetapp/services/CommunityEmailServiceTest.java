package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.services.CommunityEmailService;
import com.safetynetapp.utilities.Constants;
import com.safetynetapp.utilities.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tinylog.Logger;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CommunityEmailServiceTest {

  @Mock
  private DataLoader dataLoader;

  private CommunityEmailService communityEmailService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    communityEmailService = new CommunityEmailService();
    communityEmailService.setDataLoader(dataLoader);
  }

  @Test
  void testGetCommunityEmails() {
    // Arrange
    String city = "TestCity";

    List<Person> mockPeople = Arrays.asList(
        new Person("John", "Doe", "Address1", city, "12345", "555-555-5555", "john.doe@example.com"),
        new Person("Jane", "Doe", "Address2", city, "54321", "555-555-5555", "jane.doe@example.com"),
        new Person("James", "Smith", "Address3", "OtherCity", "67890", "555-555-5555", null)
    );

    when(dataLoader.loadAllDataFromJson(Constants.PERSONS, Person.class)).thenReturn(mockPeople);

    // Act
    List<String> result = communityEmailService.getCommunityEmails(city);

    // Assert
    assertEquals(2, result.size());
    assertEquals("john.doe@example.com", result.get(0));
    assertEquals("jane.doe@example.com", result.get(1));

    // Verify log message
    Logger.info("CommunityEmails found for city: {}", city);
  }

  @Test
  void testGetCommunityEmails_NoEmails() {
    // Arrange
    String city = "OtherCity";

    List<Person> mockPeople = Arrays.asList(
        new Person("John", "Doe", "Address1", "TestCity", "12345", "555-555-5555", "john.doe@example.com"),
        new Person("Jane", "Doe", "Address2", "TestCity", "54321", "555-555-5555", "jane.doe@example.com"),
        new Person("James", "Smith", "Address3", city, "67890", "555-555-5555", null)
    );

    when(dataLoader.loadAllDataFromJson(Constants.PERSONS, Person.class)).thenReturn(mockPeople);

    // Act
    List<String> result = communityEmailService.getCommunityEmails(city);

    // Assert
    assertEquals(0, result.size());

    // Verify log message
    Logger.info("No CommunityEmails found for city: {}", city);
  }
}
