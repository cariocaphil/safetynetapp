package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonInfo;
import com.safetynetapp.models.MedicalRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonWithAge;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

@Service
public class FireStationService {

  @Autowired
  private ResourceLoader resourceLoader;

  public String getFireStationInfo(int stationNumber) {
    List<Person> listPeopleServiced = getPeopleServicedByFireStation(stationNumber);
    String stringPeopleServiced = convertPersonListToString(listPeopleServiced);
    String numberChildrenAndAdultsServiced = getNumberChildrenAndAdultsServived(listPeopleServiced);
    return stringPeopleServiced + numberChildrenAndAdultsServiced;
  }


  public String getNumberChildrenAndAdultsServived(List<Person> listPeopleServiced) {
    int numAdults = 0;
    int numChildren = 0;

    List<MedicalRecord> medicalRecords = loadAllMedicalRecordsFromJson();
    List<PersonWithAge> listPeopleServicedWithAge = addAgeToPersons(listPeopleServiced,
        medicalRecords);

    for (PersonWithAge person : listPeopleServicedWithAge) {
      if (person.getAge() <= 18) {
        numChildren++;
      } else {
        numAdults++;
      }
    }

    String summary = "Summary: " + numAdults + " adults, " + numChildren + " children.\n";
    return summary;
  }

  public static List<PersonWithAge> addAgeToPersons(List<Person> persons,
      List<MedicalRecord> medicalRecords) {
    List<PersonWithAge> personsWithAge = new ArrayList<>();
    for (Person person : persons) {
      for (MedicalRecord record : medicalRecords) {
        if (person.getFirstName().equals(record.getFirstName()) && person.getLastName()
            .equals(record.getLastName())) {
          int age = calculateAge(record.getBirthdate());
          personsWithAge.add(new PersonWithAge(person, age));
          break;
        }
      }
    }

    return personsWithAge;
  }

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

  // Helper method
  public String convertPersonListToString(List<Person> personList) {
    try {
      List<PersonInfo> personInfoList = new ArrayList<>();
      for (Person person : personList) {
        PersonInfo personInfo = new PersonInfo(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());
        personInfoList.add(personInfo);
      }

      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(personInfoList);
    } catch (Exception e) {
      e.printStackTrace();
      return null; // Handle exception appropriately
    }
  }

  private List<Person> loadAllPeopleFromJson() {
    try {
      InputStream inputStream = resourceLoader.getResource("classpath:data.json").getInputStream();
      ObjectMapper objectMapper = new ObjectMapper();

      // Read the JSON data and get the list of persons
      JsonNode rootNode = objectMapper.readTree(inputStream);
      JsonNode personsNode = rootNode.get("persons");

      // Convert the personsNode to a List<Person>
      List<Person> personList = objectMapper.convertValue(personsNode,
          new TypeReference<List<Person>>() {
          });

      return personList;
    } catch (IOException e) {
      e.printStackTrace();
      return null; // TODO: Handle exception
    }
  }

  private List<MedicalRecord> loadAllMedicalRecordsFromJson() {
    try {
      InputStream inputStream = resourceLoader.getResource("classpath:data.json").getInputStream();
      ObjectMapper objectMapper = new ObjectMapper();

      // Read the JSON data and get the list of medical records
      JsonNode rootNode = objectMapper.readTree(inputStream);
      JsonNode medicalRecordsNode = rootNode.get("medicalrecords");

      // Convert the personsNode to a List<MedicalRecord>
      List<MedicalRecord> medicalRecordList = objectMapper.convertValue(medicalRecordsNode,
          new TypeReference<List<MedicalRecord>>() {
          });

      return medicalRecordList;
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
      List<FireStation> fireStationList = objectMapper.convertValue(fireStationsNode,
          new TypeReference<List<FireStation>>() {
          });

      return fireStationList;
    } catch (IOException e) {
      e.printStackTrace();
      return null; // TODO: Handle exception
    }
  }

  public static int calculateAge(String birthdate) {
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    Date today = new Date();
    try {
      Date dob = format.parse(birthdate);
      int age = today.getYear() - dob.getYear();
      if (today.getMonth() < dob.getMonth() || (today.getMonth() == dob.getMonth()
          && today.getDate() < dob.getDate())) {
        age--;
      }
      return age;
    } catch (ParseException e) {
      e.printStackTrace();
      return -1; // Handle error
    }
  }
}
