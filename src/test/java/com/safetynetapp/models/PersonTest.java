package com.safetynetapp.models;

import com.safetynetapp.models.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

  @Test
  void testGetFirstName() {
    Person person = new Person("John", "Doe", "123 Main St", "Some City", "12345", "555-555-5555", "john@example.com");
    assertEquals("John", person.getFirstName());
  }

  @Test
  void testGetLastName() {
    Person person = new Person("John", "Doe", "123 Main St", "Some City", "12345", "555-555-5555", "john@example.com");
    assertEquals("Doe", person.getLastName());
  }

  @Test
  void testGetAddress() {
    Person person = new Person("John", "Doe", "123 Main St", "Some City", "12345", "555-555-5555", "john@example.com");
    assertEquals("123 Main St", person.getAddress());
  }

  @Test
  void testGetCity() {
    Person person = new Person("John", "Doe", "123 Main St", "Some City", "12345", "555-555-5555", "john@example.com");
    assertEquals("Some City", person.getCity());
  }

  @Test
  void testGetZip() {
    Person person = new Person("John", "Doe", "123 Main St", "Some City", "12345", "555-555-5555", "john@example.com");
    assertEquals("12345", person.getZip());
  }

  @Test
  void testGetPhone() {
    Person person = new Person("John", "Doe", "123 Main St", "Some City", "12345", "555-555-5555", "john@example.com");
    assertEquals("555-555-5555", person.getPhone());
  }

  @Test
  void testGetEmail() {
    Person person = new Person("John", "Doe", "123 Main St", "Some City", "12345", "555-555-5555", "john@example.com");
    assertEquals("john@example.com", person.getEmail());
  }

  @Test
  void testSetFirstName() {
    Person person = new Person();
    person.setFirstName("Jane");
    assertEquals("Jane", person.getFirstName());
  }

  @Test
  void testSetLastName() {
    Person person = new Person();
    person.setLastName("Doe");
    assertEquals("Doe", person.getLastName());
  }

  @Test
  void testSetAddress() {
    Person person = new Person();
    person.setAddress("456 Oak St");
    assertEquals("456 Oak St", person.getAddress());
  }

  @Test
  void testSetCity() {
    Person person = new Person();
    person.setCity("New City");
    assertEquals("New City", person.getCity());
  }

  @Test
  void testSetZip() {
    Person person = new Person();
    person.setZip("54321");
    assertEquals("54321", person.getZip());
  }

  @Test
  void testSetPhone() {
    Person person = new Person();
    person.setPhone("555-123-4567");
    assertEquals("555-123-4567", person.getPhone());
  }

  @Test
  void testSetEmail() {
    Person person = new Person();
    person.setEmail("jane@example.com");
    assertEquals("jane@example.com", person.getEmail());
  }
}
