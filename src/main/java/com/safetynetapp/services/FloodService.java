package com.safetynetapp.services;

import com.safetynetapp.models.FloodInfoResponse;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonWithAgeAndMedicalDetails;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import com.safetynetapp.utilities.DateUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloodService {

  @Autowired
  private DataLoader dataLoader;

  @Autowired
  private DataUtils dataUtils;

  public static List<String> convertStationParamToList(String stationParam) {
    // Remove any trailing commas and split the string
    String[] stationArray = stationParam.replaceAll(",$", "").split(",");

    return Arrays.asList(stationArray);
  }

  public FloodInfoResponse getFloodInfo(String stationNumbers) {

    List<String> stations = convertStationParamToList(stationNumbers);
    List<Person> peopleServicedByStationNumbers = new ArrayList<>();
    for (String station : stations) {
      List<Person> peopleServiceByStation = dataUtils.getPeopleServicedByFireStation(station);
      peopleServicedByStationNumbers.addAll(peopleServiceByStation);
    }

    // group by address
    Map<String, List<PersonWithAgeAndMedicalDetails>> groupedByAddress = new HashMap<>();
    for (Person person : peopleServicedByStationNumbers) {
      String address = person.getAddress();

      PersonWithAgeAndMedicalDetails personDetails = getPersonDetails(person);

      groupedByAddress.computeIfAbsent(address, k -> new ArrayList<>()).add(personDetails);
    }

    FloodInfoResponse response = new FloodInfoResponse();
    response.setListHouseHoldsAtStation(groupedByAddress);

    return response;
  }

  private PersonWithAgeAndMedicalDetails getPersonDetails(Person person) {
    List<MedicalRecord> medicalRecords = dataLoader.loadAllDataFromJson("medicalrecords", MedicalRecord.class);

    for (MedicalRecord record : medicalRecords) {
      if (person.getFirstName().equals(record.getFirstName()) && person.getLastName().equals(record.getLastName())) {
        int age = DateUtils.calculateAge(record.getBirthdate());

        return new PersonWithAgeAndMedicalDetails(
            person.getFirstName(), person.getLastName(), person.getPhone(), age,
            record.getMedications(), record.getAllergies()
        );
      }
    }

    // If no matching medical record found, return null or handle appropriately.
    return null;
  }

}