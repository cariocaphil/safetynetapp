package com.safetynetapp.utilities;

import com.safetynetapp.models.FireStation;
import com.safetynetapp.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DataUtilsTest {

  @Mock
  private DataLoader dataLoader;

  private DataUtils dataUtils;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    dataUtils = new DataUtils(dataLoader);
  }

  @Test
  public void testGetPeopleServicedByFireStation() {
    // Mock data
    List<Person> allPeople = Arrays.asList(
        new Person("John", "Doe", "123 Main St", "Test City", "12345", "123-456-7890", "john@example.com"),
        new Person("Jane", "Doe", "123 Main St", "Test City", "12345", "123-456-7890", "jane@example.com")
        // Add more mock persons as needed
    );

    List<FireStation> fireStations = Arrays.asList(
        new FireStation("123 Main St", "1"),
        new FireStation("456 Oak St", "2")
        // Add more mock fire stations as needed
    );

    // Mock data loader behavior
    when(dataLoader.loadAllDataFromJson(Constants.PERSONS, Person.class)).thenReturn(allPeople);
    when(dataLoader.loadAllDataFromJson(Constants.FIRESTATIONS, FireStation.class)).thenReturn(fireStations);

    // Test with station number "1"
    List<Person> result = dataUtils.getPeopleServicedByFireStation("1");

    assertEquals(2, result.size());
  }

  // Add more tests for other methods as needed

}
