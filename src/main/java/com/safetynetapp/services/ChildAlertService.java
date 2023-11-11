package com.safetynetapp.services;

import com.safetynetapp.models.ChildInfoResponse;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonWithAge;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.models.SimpleChildInfo;

import com.safetynetapp.utilities.Constants;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChildAlertService {

  private final DataLoader dataLoader;

  @Autowired
  public ChildAlertService(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
  }

  public ChildInfoResponse getChildAlertInfo(String address) {
    Logger.debug("Received request for ChildAlertInfo with address: {}", address);

    List<Person> persons = dataLoader.loadAllDataFromJson(Constants.PERSONS, Person.class);
    List<PersonWithAge> children = new ArrayList<>();
    List<Person> otherPersons = new ArrayList<>();
    List<MedicalRecord> medicalRecords = dataLoader.loadAllDataFromJson(Constants.MEDICAL_RECORDS, MedicalRecord.class);

    for (Person person : persons) {
      for (MedicalRecord record : medicalRecords) {
        if (person.getFirstName().equals(record.getFirstName()) && person.getLastName()
            .equals(record.getLastName())) {
          int age = DateUtils.calculateAge(record.getBirthdate());

          if (age < 18) {
            children.add(new PersonWithAge(person, age));
          } else {
            otherPersons.add(person);
          }
        }
      }
    }

    if (!children.isEmpty() || !otherPersons.isEmpty()) {
      List<SimpleChildInfo> simpleChildInfoList = getSimpleChildInfoList(children);

      Logger.info("ChildAlertInfo found for address: {}", address);
      return new ChildInfoResponse(simpleChildInfoList, otherPersons);
    }

    Logger.debug("No ChildAlertInfo found for address: {}", address);
    return null;
  }

  public List<SimpleChildInfo> getSimpleChildInfoList(List<PersonWithAge> children) {
    List<SimpleChildInfo> simpleChildInfoList = new ArrayList<>();

    for (PersonWithAge child : children) {
      SimpleChildInfo simpleChildInfo = new SimpleChildInfo();
      simpleChildInfo.setFirstName(child.getFirstName());
      simpleChildInfo.setLastName(child.getLastName());
      simpleChildInfo.setAge(child.getAge());

      simpleChildInfoList.add(simpleChildInfo);
    }

    return simpleChildInfoList;
  }
}
