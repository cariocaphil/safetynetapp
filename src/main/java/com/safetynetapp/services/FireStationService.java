package com.safetynetapp.services;

import com.safetynetapp.models.FireStationInfoResponse;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonInfo;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.models.SummaryChildrenAndAdultsServiced;
import com.safetynetapp.models.PersonWithAge;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DateUtils;
import com.safetynetapp.utilities.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

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

  public FireStationInfoResponse getFireStationInfo(int stationNumber) {
    List<Person> listPeopleServiced = dataUtils.getPeopleServicedByFireStation(stationNumber);

    if (listPeopleServiced.isEmpty()) {
      return null;
    }
    List<PersonInfo> customizedListPeopleServiced = customizeListPeopleServiced(listPeopleServiced);
    FireStationInfoResponse response = new FireStationInfoResponse();

    response.setPeopleServiced(customizedListPeopleServiced);
    response.setSummaryChildrenAndAdultsServiced(
        getNumberChildrenAndAdultsServived(listPeopleServiced));

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

    return personsWithAge;
  }

}
