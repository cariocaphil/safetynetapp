package com.safetynetapp.services;

import com.safetynetapp.models.ChildInfoResponse;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonWithAge;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.utilities.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
          int age = calculateAge(record.getBirthdate());

          if (age < 18) {
            children.add(new PersonWithAge(person, age));
          } else {
            otherPersons.add(person);
          }
        }
      }
    }
    if (!children.isEmpty() || !otherPersons.isEmpty()) {
      return new ChildInfoResponse(children, otherPersons);
    }

    return null;
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
