package com.safetynetapp.services;

import com.safetynetapp.model.*;

import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import com.safetynetapp.utilities.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireService {

  @Autowired
  private DataLoader dataLoader;

  @Autowired
  private DataUtils dataUtils;

  public FireInfoResponse getFireInfo(String address) {
    Integer stationServicingAddress = dataUtils.getStationForAddress(address);

    if (stationServicingAddress == null) {
      return null;
    }

    List<Person> peopleLivingAtAddress = getPeopleLivingAtAddress(address);

    List<PersonWithAgeAndMedicalDetails> customizedPeopleLivingAtAddress =
        customizeListPeopleServicedWithMedicalDetails(peopleLivingAtAddress);

    FireInfoResponse response = new FireInfoResponse();
    response.setStationNumber(stationServicingAddress);
    response.setPeopleLivingAtAddress(customizedPeopleLivingAtAddress);

    return response;
  }

  public List<Person> getPeopleLivingAtAddress(String address) {
    List<Person> allPeople = dataLoader.loadAllDataFromJson("persons", Person.class);
    List<Person> peopleLivingAtAddress = new ArrayList<>();

    for (Person person : allPeople) {
      if (person.getAddress().equals(address)) {
        peopleLivingAtAddress.add(person);
      }
    }

    return peopleLivingAtAddress;
  }

  public List<PersonWithAgeAndMedicalDetails> customizeListPeopleServicedWithMedicalDetails(List<Person> listPeopleServiced) {
    List<MedicalRecord> medicalRecords = dataLoader.loadAllDataFromJson("medicalrecords", MedicalRecord.class);

    List<PersonWithAgeAndMedicalDetails> personWithMedicalDetailsList = new ArrayList<>();
    for (Person person : listPeopleServiced) {
      for (MedicalRecord record : medicalRecords) {
        if (person.getFirstName().equals(record.getFirstName()) && person.getLastName().equals(record.getLastName())) {
          int age = DateUtils.calculateAge(record.getBirthdate());

          PersonWithAgeAndMedicalDetails personWithMedicalDetails = new PersonWithAgeAndMedicalDetails(
              person.getFirstName(), person.getLastName(), person.getPhone(), age,
              record.getMedications(), record.getAllergies()
          );

          personWithMedicalDetailsList.add(personWithMedicalDetails);
          break;
        }
      }
    }
    return personWithMedicalDetailsList;
  }

}