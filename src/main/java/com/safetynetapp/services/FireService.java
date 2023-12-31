package com.safetynetapp.services;

import com.safetynetapp.models.FireInfoResponse;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonWithAgeAndMedicalDetails;
import com.safetynetapp.services.interfaces.FireInfoService;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import com.safetynetapp.utilities.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireService implements FireInfoService {

  private final DataLoader dataLoader;
  private final DataUtils dataUtils;

  @Autowired
  public FireService(DataLoader dataLoader, DataUtils dataUtils) {
    this.dataLoader = dataLoader;
    this.dataUtils = dataUtils;
  }

  @Override
  public FireInfoResponse getFireInfo(String address) {
    Logger.debug("Received request for FireInfo with address: {}", address);

    Integer stationServicingAddress = dataUtils.getStationForAddress(address);

    if (stationServicingAddress == null) {
      return null;
    }

    List<Person> peopleLivingAtAddress = dataUtils.getPeopleLivingAtAddress(address);

    List<PersonWithAgeAndMedicalDetails> customizedPeopleLivingAtAddress =
        customizeListPeopleServicedWithMedicalDetails(peopleLivingAtAddress);

    FireInfoResponse response = new FireInfoResponse();
    response.setStationNumber(stationServicingAddress);
    response.setPeopleLivingAtAddress(customizedPeopleLivingAtAddress);

    Logger.info("FireInfo retrieved for address: {}", address);

    return response;
  }

  public List<PersonWithAgeAndMedicalDetails> customizeListPeopleServicedWithMedicalDetails(List<Person> listPeopleServiced) {
    List<PersonWithAgeAndMedicalDetails> personWithMedicalDetailsList = new ArrayList<>();
    List<MedicalRecord> medicalRecords = dataLoader.loadAllDataFromJson("medicalrecords", MedicalRecord.class);

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

    Logger.debug("Customized People List with Medical Details generated");
    return personWithMedicalDetailsList;
  }
}
