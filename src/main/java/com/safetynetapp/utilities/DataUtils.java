package com.safetynetapp.utilities;

import com.safetynetapp.models.FireStation;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonDetails;
import com.safetynetapp.models.PersonWithAgeAndMedicalDetails;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DataUtils {

  private final DataLoader dataLoader;

  public DataUtils(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
  }

  public List<Person> getPeopleServicedByFireStation(String stationNumber) {
    List<Person> allPeople = dataLoader.loadAllDataFromJson("persons", Person.class);
    List<Person> peopleServicedByStation = new ArrayList<>();

    for (Person person : allPeople) {
      if (getAddressesForStation(stationNumber).contains(person.getAddress())) {
        peopleServicedByStation.add(person);
      }
    }

    return peopleServicedByStation;
  }

  private List<String> getAddressesForStation(String stationNumber) {
    List<FireStation> fireStations = dataLoader.loadAllDataFromJson("firestations",
        FireStation.class);
    List<String> addresses = new ArrayList<>();

    for (FireStation fireStation : fireStations) {
      if (fireStation.getStation().equals(String.valueOf(stationNumber))) {
        addresses.add(fireStation.getAddress());
      }
    }

    return addresses;
  }

  public Integer getStationForAddress(String address) {
    List<FireStation> fireStations = dataLoader.loadAllDataFromJson("firestations",
        FireStation.class);
    Integer stationNumber = null;

    for (FireStation fireStation : fireStations) {
      if (fireStation.getAddress().equals(address)) {
        stationNumber = Integer.valueOf(fireStation.getStation());
        break; // Once we find the station, we can stop the loop.
      }
    }

    return stationNumber;
  }

  public PersonWithAgeAndMedicalDetails getPersonDetails(Person person) {
    List<MedicalRecord> medicalRecords = dataLoader.loadAllDataFromJson("medicalrecords",
        MedicalRecord.class);

    for (MedicalRecord record : medicalRecords) {
      if (person.getFirstName().equals(record.getFirstName()) && person.getLastName()
          .equals(record.getLastName())) {
        int age = DateUtils.calculateAge(record.getBirthdate());

        return new PersonWithAgeAndMedicalDetails(
            person.getFirstName(), person.getLastName(), person.getPhone(), age,
            record.getMedications(), record.getAllergies()
        );
      }
    }

    // If no matching medical record found, return null or handle appropriately.
    return null;
  }

  public PersonDetails getPersonDetailsFor(Person person) {
    List<MedicalRecord> medicalRecords = dataLoader.loadAllDataFromJson("medicalrecords",
        MedicalRecord.class);

    for (MedicalRecord record : medicalRecords) {
      if (person.getFirstName().equals(record.getFirstName()) && person.getLastName()
          .equals(record.getLastName())) {
        int age = DateUtils.calculateAge(record.getBirthdate());

        return new PersonDetails(
            person.getFirstName(), person.getLastName(), person.getAddress(), age,
            person.getEmail(),
            record.getMedications(), record.getAllergies()
        );
      }
    }
    // If no matching medical record found, return null or handle appropriately.
    return null;
  }
}