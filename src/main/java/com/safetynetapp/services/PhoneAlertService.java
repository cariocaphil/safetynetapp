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
import com.safetynetapp.utilities.DataUtils;
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
