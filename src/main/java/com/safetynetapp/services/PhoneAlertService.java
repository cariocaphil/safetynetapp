package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.models.PhoneAlertInfoResponse;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class PhoneAlertService {

  @Autowired
  private DataLoader dataLoader;

  @Autowired
  private DataUtils dataUtils;

  public PhoneAlertService(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
  }

  public PhoneAlertService(DataLoader dataLoader, DataUtils dataUtils) {
    this.dataLoader = dataLoader;
    this.dataUtils = dataUtils;

  }

  public PhoneAlertService() {
  }

  public PhoneAlertInfoResponse getPhoneAlertInfo(int stationNumber) {
    List<Person> listPeopleServiced = dataUtils.getPeopleServicedByFireStation(stationNumber);

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

}
