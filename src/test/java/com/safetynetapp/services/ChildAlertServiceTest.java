package com.safetynetapp.services;

import com.safetynetapp.models.ChildInfoResponse;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonWithAge;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.models.SimpleChildInfo;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChildAlertServiceTest {

  @Mock
  private DataLoader dataLoader;

  private ChildAlertService childAlertService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    childAlertService = new ChildAlertService(dataLoader);
  }

  @Test
  void testGetSimpleChildInfoList() {
    // Arrange
    List<PersonWithAge> children = new ArrayList<>();
    children.add(new PersonWithAge(new Person("John", "Doe", "123 Main St", "Anytown", "12345", "555-555-5555", "john.doe@example.com"), 10));
    children.add(new PersonWithAge(new Person("Jane", "Doe", "123 Main St", "Anytown", "12345", "555-555-5555", "john.doe@example.com"), 12));

    // Act
    List<SimpleChildInfo> simpleChildInfoList = childAlertService.getSimpleChildInfoList(children);

    // Assert
    List<SimpleChildInfo> expectedSimpleChildInfoList = new ArrayList<>();
    expectedSimpleChildInfoList.add(new SimpleChildInfo("John", "Doe", 10));
    expectedSimpleChildInfoList.add(new SimpleChildInfo("Jane", "Doe", 12));

    assertEquals(expectedSimpleChildInfoList, simpleChildInfoList);
  }

}
