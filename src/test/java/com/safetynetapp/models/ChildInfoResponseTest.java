package com.safetynetapp.models;

import com.safetynetapp.models.ChildInfoResponse;
import com.safetynetapp.models.SimpleChildInfo;
import com.safetynetapp.models.Person;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ChildInfoResponseTest {

  @Test
  void testGetChildren() {
    List<SimpleChildInfo> children = new ArrayList<>();
    List<Person> otherPersons = new ArrayList<>();
    children.add(new SimpleChildInfo("John", "Doe", 10));
    ChildInfoResponse response = new ChildInfoResponse(children, otherPersons);

    assertEquals(children, response.getChildren());
  }

  @Test
  void testGetOtherPersons() {
    List<SimpleChildInfo> children = new ArrayList<>();
    List<Person> otherPersons = new ArrayList<>();
    otherPersons.add(new Person("Jane", "Doe", "123 Main St", "Some City", "12345", "555-555-5555", "jane@example.com"));
    ChildInfoResponse response = new ChildInfoResponse(children, otherPersons);

    assertEquals(otherPersons, response.getOtherPersons());
  }

  @Test
  void testSetChildren() {
    List<SimpleChildInfo> children = new ArrayList<>();
    List<Person> otherPersons = new ArrayList<>();
    children.add(new SimpleChildInfo("John", "Doe", 10));
    ChildInfoResponse response = new ChildInfoResponse();
    response.setChildren(children);

    assertEquals(children, response.getChildren());
  }

  @Test
  void testSetOtherPersons() {
    List<SimpleChildInfo> children = new ArrayList<>();
    List<Person> otherPersons = new ArrayList<>();
    otherPersons.add(new Person("Jane", "Doe", "123 Main St", "Some City", "12345", "555-555-5555", "jane@example.com"));
    ChildInfoResponse response = new ChildInfoResponse();
    response.setOtherPersons(otherPersons);

    assertEquals(otherPersons, response.getOtherPersons());
  }
}
