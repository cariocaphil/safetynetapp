package com.safetynetapp.services;

import com.safetynetapp.models.FireStation;
import com.safetynetapp.models.FireStationInfoResponse;
import com.safetynetapp.models.FireStationMappingDeleteRequest;
import com.safetynetapp.models.FireStationMappingUpdateRequest;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonInfo;
import com.safetynetapp.models.PersonWithAge;
import com.safetynetapp.models.SummaryChildrenAndAdultsServiced;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import com.safetynetapp.utilities.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {

  @Autowired
  private DataLoader dataLoader;

  @Autowired
  private DataUtils dataUtils;

  public FireStationService(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
  }

  public FireStationService(DataLoader dataLoader, DataUtils dataUtils) {
    this.dataLoader = dataLoader;
    this.dataUtils = dataUtils;
  }

  public FireStationService() {
  }

  public FireStationInfoResponse getFireStationInfo(String stationNumber) {
    Logger.debug("Received request for Fire Station Info with station number: {}", stationNumber);

    List<Person> listPeopleServiced = dataUtils.getPeopleServicedByFireStation(stationNumber);

    if (listPeopleServiced.isEmpty()) {
      return null;
    }
    List<PersonInfo> customizedListPeopleServiced = customizeListPeopleServiced(listPeopleServiced);
    FireStationInfoResponse response = new FireStationInfoResponse();

    response.setPeopleServiced(customizedListPeopleServiced);
    response.setSummaryChildrenAndAdultsServiced(
        getNumberChildrenAndAdultsServived(listPeopleServiced));

    Logger.info("Fire Station Info retrieved for station number: {}", stationNumber);

    return response;
  }

  public List<PersonInfo> customizeListPeopleServiced(List<Person> listPeopleServiced) {
    List<PersonInfo> personInfoList = new ArrayList<>();
    for (Person person : listPeopleServiced) {
      PersonInfo personInfo = new PersonInfo(person.getFirstName(), person.getLastName(),
          person.getAddress(), person.getPhone());
      personInfoList.add(personInfo);
    }
    return personInfoList;
  }

  public SummaryChildrenAndAdultsServiced getNumberChildrenAndAdultsServived(
      List<Person> listPeopleServiced) {
    int numAdults = 0;
    int numChildren = 0;

    List<MedicalRecord> medicalRecords = dataLoader.loadAllDataFromJson("medicalrecords",
        MedicalRecord.class);
    List<PersonWithAge> listPeopleServicedWithAge = addAgeToPersons(listPeopleServiced,
        medicalRecords);

    for (PersonWithAge person : listPeopleServicedWithAge) {
      if (person.getAge() <= 18) {
        numChildren++;
      } else {
        numAdults++;
      }
    }

    SummaryChildrenAndAdultsServiced summary = new SummaryChildrenAndAdultsServiced();
    summary.setChildren(numChildren);
    summary.setAdults(numAdults);

    Logger.debug("Calculated summary of children and adults serviced");
    return summary;
  }

  public static List<PersonWithAge> addAgeToPersons(List<Person> persons,
      List<MedicalRecord> medicalRecords) {
    List<PersonWithAge> personsWithAge = new ArrayList<>();
    for (Person person : persons) {
      for (MedicalRecord record : medicalRecords) {
        if (person.getFirstName().equals(record.getFirstName()) && person.getLastName()
            .equals(record.getLastName())) {
          int age = DateUtils.calculateAge(record.getBirthdate());
          personsWithAge.add(new PersonWithAge(person, age));
          break;
        }
      }
    }

    Logger.debug("Added age to persons from medical records");
    return personsWithAge;
  }

  public boolean addFireStation(FireStation fireStation) {
    List<FireStation> fireStations = new ArrayList<>(dataLoader.loadAllDataFromJson("firestations", FireStation.class));

    for (FireStation existingMapping : fireStations) {
      if (existingMapping.getAddress().equals(fireStation.getAddress())) {
        Logger.info("Fire station mapping already exists for address: {}", fireStation.getAddress());
        return false; // Mapping already exists, return false
      }
    }

    fireStations.add(fireStation);

    String stationInfo = String.format("Station Number: %s, Address: %s",
        fireStation.getStation(), fireStation.getAddress());
    Logger.info("Added new fire station: {}", stationInfo);

    return true; // Mapping added successfully
  }

  public boolean updateFireStation(FireStationMappingUpdateRequest request) {
    List<FireStation> fireStations = dataLoader.loadAllDataFromJson("firestations",
        FireStation.class);

    for (FireStation fireStation : fireStations) {
      if (fireStation.getAddress().equals(request.getAddress())) {
        fireStation.setStation(request.getStation());
        String stationInfo = String.format("Station Number: %s, Address: %s",
            fireStation.getStation(), fireStation.getAddress());
        Logger.info("Updated fire station: {}", stationInfo);
        return true;
      }
    }

    Logger.info("Fire station not found for address: {}", request.getAddress());
    return false;
  }

  public boolean deleteFireStationMapping(FireStationMappingDeleteRequest request) {
    List<FireStation> fireStations = dataLoader.loadAllDataFromJson("firestations", FireStation.class);

    if (request.getAddress() == null) {
      Logger.info("Address in delete request is null.");
      return false; // Address is null, return false
    }

    for (int i = 0; i < fireStations.size(); i++) {
      if (fireStations.get(i).getAddress().equals(request.getAddress())) {
        fireStations.remove(i);
        String stationInfo = String.format("Station Number: %s, Address: %s",
            fireStations.get(i).getStation(), fireStations.get(i).getAddress());
        Logger.info("Deleted fire station: {}", stationInfo);
        return true; // Deletion successful
      }
    }

    Logger.info("Fire station not found for address: {}", request.getAddress());
    return false; // Mapping not found
  }
}
