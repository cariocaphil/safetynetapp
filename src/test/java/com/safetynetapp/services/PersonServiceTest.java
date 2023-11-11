package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.utilities.Constants;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

  @Mock
  private DataLoader dataLoader;

  @Mock
  private DataUtils dataUtils;

  private PersonService personService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    personService = new PersonService(dataLoader, dataUtils);
  }

  @Test
  void addPerson_shouldAddPersonToList() {
    Person person = new Person("John", "Doe", "123 Main St", "City", "12345", "555-555-5555", "john.doe@example.com");

    boolean result = personService.addPerson(person);

    assertTrue(result);
    assertEquals(1, personService.getPersonList().size());
    assertEquals(person, personService.getPersonList().get(0));
    verify(dataUtils, times(1)).loadJsonData(eq(Constants.PERSONS), eq(Person.class));
  }

  @Test
  void addPerson_shouldNotAddDuplicatePerson() {
    Person person = new Person("John", "Doe", "123 Main St", "City", "12345", "555-555-5555", "john.doe@example.com");
    personService.addPerson(person);

    boolean result = personService.addPerson(person);

    assertFalse(result);
    assertEquals(1, personService.getPersonList().size());
    verify(dataUtils, times(1)).loadJsonData(eq(Constants.PERSONS), eq(Person.class));
  }

  @Test
  void updatePerson_shouldUpdateExistingPerson() {
    Person originalPerson = new Person("John", "Doe", "123 Main St", "City", "12345", "555-555-5555", "john.doe@example.com");
    Person updatedPerson = new Person("John", "Doe", "456 Oak St", "New City", "67890", "555-555-5555", "john.doe@example.com");
    personService.addPerson(originalPerson);

    boolean result = personService.updatePerson(updatedPerson);

    assertTrue(result);
    assertEquals("456 Oak St", originalPerson.getAddress());
    assertEquals("New City", originalPerson.getCity());
    assertEquals("67890", originalPerson.getZip());
    verify(dataUtils, times(1)).loadJsonData(eq(Constants.PERSONS), eq(Person.class));
  }

  @Test
  void updatePerson_shouldNotUpdateNonexistentPerson() {
    Person updatedPerson = new Person("John", "Doe", "456 Oak St", "New City", "67890", "555-555-5555", "john.doe@example.com");

    boolean result = personService.updatePerson(updatedPerson);

    assertFalse(result);
    verify(dataUtils, times(1)).loadJsonData(eq(Constants.PERSONS), eq(Person.class));
  }

  @Test
  void deletePerson_shouldDeleteExistingPerson() {
    Person person = new Person("John", "Doe", "123 Main St", "City", "12345", "555-555-5555", "john.doe@example.com");
    personService.addPerson(person);

    boolean result = personService.deletePerson("John", "Doe");

    assertTrue(result);
    assertEquals(0, personService.getPersonList().size());
    verify(dataUtils, times(1)).loadJsonData(eq(Constants.PERSONS), eq(Person.class));
  }

  @Test
  void deletePerson_shouldNotDeleteNonexistentPerson() {
    boolean result = personService.deletePerson("John", "Doe");

    assertFalse(result);
    verify(dataUtils, times(1)).loadJsonData(eq(Constants.PERSONS), eq(Person.class));
  }
}
