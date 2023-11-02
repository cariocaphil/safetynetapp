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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void testGetChildAlertInfoWithChildren() {
      // Mocking dataLoader
      when(dataLoader.loadAllDataFromJson(eq("persons"), eq(Person.class)))
          .thenReturn(createMockPersonList());
      when(dataLoader.loadAllDataFromJson(eq("medicalrecords"), eq(MedicalRecord.class)))
          .thenReturn(createMockMedicalRecordList());

      // Testing the service method
      ChildInfoResponse result = childAlertService.getChildAlertInfo("123 Main St");

      // Assertions
      assertNotNull(result);
      List<SimpleChildInfo> simpleChildInfoList = result.getChildren();
      assertEquals(2, simpleChildInfoList.size());
      assertEquals("John", simpleChildInfoList.get(0).getFirstName());
      assertEquals("Doe", simpleChildInfoList.get(0).getLastName());
    }

    @Test
    void testGetChildAlertInfo_OnlyOtherPersons() {
      // Arrange
      String address = "123 Main St";

      List<Person> persons = new ArrayList<>();
      persons.add(new Person("John", "Doe", "123 Main St", "Anytown", "12345", "555-555-5555", "john.doe@example.com"));

      List<MedicalRecord> medicalRecords = new ArrayList<>();
      medicalRecords.add(new MedicalRecord("John", "Doe", "01/01/1990", new ArrayList<>(), new ArrayList<>()));

      List<PersonWithAge> children = new ArrayList<>();
      List<Person> otherPersons = new ArrayList<>(persons);

      when(dataLoader.loadAllDataFromJson(eq("persons"), eq(Person.class))).thenReturn(persons);
      when(dataLoader.loadAllDataFromJson(eq("medicalrecords"), eq(MedicalRecord.class))).thenReturn(medicalRecords);

      // Act
      ChildInfoResponse childInfoResponse = childAlertService.getChildAlertInfo(address);

      // Assert
      verify(dataLoader, times(1)).loadAllDataFromJson(eq("persons"), eq(Person.class));
      verify(dataLoader, times(1)).loadAllDataFromJson(eq("medicalrecords"), eq(MedicalRecord.class));

      assertEquals(otherPersons, childInfoResponse.getOtherPersons());
    }

    private List<Person> createMockPersonList() {
      List<Person> persons = new ArrayList<>();
      persons.add(new Person("John", "Doe", "123 Main St", "City", "12345", "email@example.com", "1234567890"));
      persons.add(new Person("Jane", "Doe", "123 Main St", "City", "12345", "email@example.com", "1234567890"));
      return persons;
    }

    private List<MedicalRecord> createMockMedicalRecordList() {
      List<MedicalRecord> medicalRecords = new ArrayList<>();
      medicalRecords.add(new MedicalRecord("John", "Doe", "01/01/2010",
          List.of(new String[]{"medication1", "medication2"}),
          List.of(new String[]{"allergy1", "allergy2"})));
      medicalRecords.add(new MedicalRecord("Jane", "Doe", "01/01/2012",
          List.of(new String[]{"medication3", "medication4"}),
          List.of(new String[]{"allergy3", "allergy4"})));
      return medicalRecords;
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

    @Test
    void testGetChildAlertInfo_BothChildrenAndAdults() {
      // Arrange
      String address = "123 Main St";

      List<Person> persons = new ArrayList<>();
      persons.add(new Person("John", "Doe", "123 Main St", "Anytown", "12345", "555-555-5555", "john.doe@example.com"));
      persons.add(new Person("Jane", "Doe", "123 Main St", "Anytown", "12345", "555-555-5555", "jane.doe@example.com"));

      List<MedicalRecord> medicalRecords = new ArrayList<>();
      medicalRecords.add(new MedicalRecord("John", "Doe", "01/01/2010", new ArrayList<>(), new ArrayList<>()));
      medicalRecords.add(new MedicalRecord("Jane", "Doe", "01/01/1990", new ArrayList<>(), new ArrayList<>()));

      List<PersonWithAge> children = new ArrayList<>();
      List<Person> otherPersons = new ArrayList<>();

      when(dataLoader.loadAllDataFromJson(eq("persons"), eq(Person.class))).thenReturn(persons);
      when(dataLoader.loadAllDataFromJson(eq("medicalrecords"), eq(MedicalRecord.class))).thenReturn(medicalRecords);

      // Act
      ChildInfoResponse childInfoResponse = childAlertService.getChildAlertInfo(address);

      // Assert
      verify(dataLoader, times(1)).loadAllDataFromJson(eq("persons"), eq(Person.class));
      verify(dataLoader, times(1)).loadAllDataFromJson(eq("medicalrecords"), eq(MedicalRecord.class));

      // Verify that children were correctly added to the 'children' list
      assertEquals(1, childInfoResponse.getChildren().size());
      assertEquals("John", childInfoResponse.getChildren().get(0).getFirstName());
      assertEquals("Doe", childInfoResponse.getChildren().get(0).getLastName());

      // Verify that other persons were correctly added to the 'otherPersons' list
      assertEquals(1, childInfoResponse.getOtherPersons().size());
      assertEquals("Jane", childInfoResponse.getOtherPersons().get(0).getFirstName());
      assertEquals("Doe", childInfoResponse.getOtherPersons().get(0).getLastName());
    }

}
