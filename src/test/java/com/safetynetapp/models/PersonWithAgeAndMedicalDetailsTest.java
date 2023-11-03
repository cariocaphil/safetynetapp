package com.safetynetapp.models;

import com.safetynetapp.models.PersonWithAgeAndMedicalDetails;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonWithAgeAndMedicalDetailsTest {

  @Test
  void testEquals() {
    // Arrange
    List<String> medications = Arrays.asList("Medication1", "Medication2");
    List<String> allergies = Arrays.asList("Allergy1", "Allergy2");

    PersonWithAgeAndMedicalDetails person1 = new PersonWithAgeAndMedicalDetails("John", "Doe", "555-555-5555", 30, medications, allergies);
    PersonWithAgeAndMedicalDetails person2 = new PersonWithAgeAndMedicalDetails("John", "Doe", "555-555-5555", 30, medications, allergies);
    PersonWithAgeAndMedicalDetails person3 = new PersonWithAgeAndMedicalDetails("Jane", "Doe", "555-555-5555", 25, medications, allergies);

    // Act & Assert
    assertEquals(person1, person2); // Expecting them to be equal
    assertNotEquals(person1, person3); // Expecting them not to be equal
  }

  @Test
  void testHashCode() {
    // Arrange
    List<String> medications = Arrays.asList("Medication1", "Medication2");
    List<String> allergies = Arrays.asList("Allergy1", "Allergy2");

    PersonWithAgeAndMedicalDetails person1 = new PersonWithAgeAndMedicalDetails("John", "Doe", "555-555-5555", 30, medications, allergies);
    PersonWithAgeAndMedicalDetails person2 = new PersonWithAgeAndMedicalDetails("John", "Doe", "555-555-5555", 30, medications, allergies);

    // Act & Assert
    assertEquals(person1.hashCode(), person2.hashCode()); // Expecting hash codes to be equal
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    List<String> medications = Arrays.asList("Medication1", "Medication2");
    List<String> allergies = Arrays.asList("Allergy1", "Allergy2");

    PersonWithAgeAndMedicalDetails person = new PersonWithAgeAndMedicalDetails("John", "Doe", "555-555-5555", 30, medications, allergies);

    // Act & Assert
    assertEquals("John", person.getFirstName());
    assertEquals("Doe", person.getLastName());
    assertEquals("555-555-5555", person.getPhone());
    assertEquals(30, person.getAge());
    assertEquals(medications, person.getMedications());
    assertEquals(allergies, person.getAllergies());

    // Test setters
    person.setFirstName("Jane");
    person.setLastName("Smith");
    person.setPhone("123-456-7890");
    person.setAge(40);
    person.setMedications(Arrays.asList("NewMedication"));
    person.setAllergies(Arrays.asList("NewAllergy"));

    assertEquals("Jane", person.getFirstName());
    assertEquals("Smith", person.getLastName());
    assertEquals("123-456-7890", person.getPhone());
    assertEquals(40, person.getAge());
    assertEquals(Arrays.asList("NewMedication"), person.getMedications());
    assertEquals(Arrays.asList("NewAllergy"), person.getAllergies());
  }
}
