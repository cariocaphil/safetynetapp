package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonInfo;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.models.PhoneAlertInfoResponse;
import com.safetynetapp.models.SummaryChildrenAndAdultsServiced;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonWithAge;
import com.safetynetapp.models.FireStation;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

@Service
public class PhoneAlertService {

  @Autowired
  private DataLoader dataLoader;

  public PhoneAlertService(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
  }

  public PhoneAlertInfoResponse getPhoneAlertInfo(int stationNumber) {
    List<Person> listPeopleServiced = getPeopleServicedByFireStation(stationNumber);

    if (listPeopleServiced.isEmpty()) {
      return null;
    }
    List<String> listPhoneNumbersPeopleServiced = new ArrayList<>();

    for (Person person : listPeopleServiced) {
      listPhoneNumbersPeopleServiced.add(person.getPhone());
    }
    PhoneAlertInfoResponse response = new PhoneAlertInfoResponse();

    response.setPhoneNumbers(listPhoneNumbersPeopleServiced);

    return response;
  }

  public List<Person> getPeopleServicedByFireStation(int stationNumber) {
    List<Person> allPeople = dataLoader.loadAllDataFromJson("persons", Person.class);
    List<Person> peopleServicedByStation = new ArrayList<>();

    for (Person person : allPeople) {
      if (getAddressesForStation(stationNumber).contains(person.getAddress())) {
        peopleServicedByStation.add(person);
      }
    }

    return peopleServicedByStation;
  }

  // Helper method
  private List<String> getAddressesForStation(int stationNumber) {
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

}
