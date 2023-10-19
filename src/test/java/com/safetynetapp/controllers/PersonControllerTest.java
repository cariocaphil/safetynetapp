package com.safetynetapp.controllers;

import com.safetynetapp.controllers.PersonController;
import com.safetynetapp.models.Person;
import com.safetynetapp.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonControllerTest {

  @Mock
  private PersonService personService;

  private PersonController personController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    personController = new PersonController(personService);
  }

  @Test
  void addPerson() {
    // Arrange
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
    person.setAddress("123 Main St");
    person.setCity("Springfield");
    person.setZip("12345");
    person.setPhone("555-123-4567");
    person.setEmail("john.doe@email.com");

    when(personService.addPerson(person)).thenReturn(true);

    // Act
    ResponseEntity<String> result = personController.addPerson(person);

    // Assert
    assertEquals(ResponseEntity.ok("Person added successfully."), result);
  }

  @Test
  void updatePerson() {
    // Arrange
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
    person.setAddress("123 Main St");
    person.setCity("Springfield");
    person.setZip("12345");
    person.setPhone("555-123-4567");
    person.setEmail("john.doe@email.com");

    when(personService.updatePerson(person)).thenReturn(true);

    // Act
    ResponseEntity<String> result = personController.updatePerson(person);

    // Assert
    assertEquals(ResponseEntity.ok("Person updated successfully."), result);
  }

  @Test
  void deletePerson() {
    // Arrange
    String firstName = "John";
    String lastName = "Doe";
    when(personService.deletePerson(firstName, lastName)).thenReturn(true);

    // Act
    ResponseEntity<String> result = personController.deletePerson(firstName, lastName);

    // Assert
    assertEquals(ResponseEntity.ok("Person deleted successfully."), result);
  }
}
