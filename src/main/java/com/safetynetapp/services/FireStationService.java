package com.safetynetapp.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class FireStationService {

  @Autowired
  private ResourceLoader resourceLoader;

  public List<Person> getPeopleServicedByFireStation(int stationNumber) {
    List<Person> allPeople = loadAllPeopleFromJson();
    List<Person> peopleServicedByStation = new ArrayList<>();

    for (Person person : allPeople) {
      if (getAddressesForStation(stationNumber).contains(person.getAddress())) {
        Person filteredPerson = new Person();
        filteredPerson.setFirstName(person.getFirstName());
        filteredPerson.setLastName(person.getLastName());
        filteredPerson.setAddress(person.getAddress());
        filteredPerson.setPhone(person.getPhone());

        peopleServicedByStation.add(filteredPerson);
      }
    }

    return peopleServicedByStation;
  }

  // Helper method
  private List<String> getAddressesForStation(int stationNumber) {
    List<FireStation> fireStations = loadAllFireStationsFromJson();
    List<String> addresses = new ArrayList<>();

    for (FireStation fireStation : fireStations) {
      if (fireStation.getStation().equals(String.valueOf(stationNumber))) {
        addresses.add(fireStation.getAddress());
      }
    }

    return addresses;
  }

  private List<Person> loadAllPeopleFromJson() {
    try {
      InputStream inputStream = resourceLoader.getResource("classpath:data.json").getInputStream();
      ObjectMapper objectMapper = new ObjectMapper();

      // Read the JSON data and get the list of persons
      JsonNode rootNode = objectMapper.readTree(inputStream);
      JsonNode personsNode = rootNode.get("persons");

      // Convert the personsNode to a List<Person>
      List<Person> personList = objectMapper.convertValue(personsNode, new TypeReference<List<Person>>() {});

      return personList;
    } catch (IOException e) {
      e.printStackTrace();
      return null; // TODO: Handle exception
    }
  }

  private List<FireStation> loadAllFireStationsFromJson() {
    try {
      InputStream inputStream = resourceLoader.getResource("classpath:data.json").getInputStream();
      ObjectMapper objectMapper = new ObjectMapper();

      // Read the JSON data and get the list of fire stations
      JsonNode rootNode = objectMapper.readTree(inputStream);
      JsonNode fireStationsNode = rootNode.get("firestations");

      // Convert the fireStationsNode to a List<FireStation>
      List<FireStation> fireStationList = objectMapper.convertValue(fireStationsNode, new TypeReference<List<FireStation>>() {});

      return fireStationList;
    } catch (IOException e) {
      e.printStackTrace();
      return null; // TODO: Handle exception
    }
  }
}
