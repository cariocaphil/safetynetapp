package com.safetynetapp.services;

import com.safetynetapp.models.ChildInfoResponse;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonWithAge;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.models.SimpleChildInfo;

import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safetynetapp.models.SimpleChildInfo;

import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;


@Service
public class ChildAlertService {

  private final DataLoader dataLoader;

  @Autowired
  public ChildAlertService(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
  }

  public ChildInfoResponse getChildAlertInfo(String address) {
    List<Person> persons = dataLoader.loadAllDataFromJson("persons", Person.class);
    List<PersonWithAge> children = new ArrayList<>();
    List<Person> otherPersons = new ArrayList<>();
    List<MedicalRecord> medicalRecords = dataLoader.loadAllDataFromJson("medicalrecords", MedicalRecord.class);
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

      return new ChildInfoResponse(simpleChildInfoList, otherPersons);
    }

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
