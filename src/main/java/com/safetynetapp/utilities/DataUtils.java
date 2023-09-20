package com.safetynetapp.utilities;

import com.safetynetapp.model.Person;
import com.safetynetapp.model.FireStation;
import com.safetynetapp.utilities.DataLoader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class DataUtils {

  private final DataLoader dataLoader;

  public DataUtils(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
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

  private List<String> getAddressesForStation(int stationNumber) {
    List<FireStation> fireStations = dataLoader.loadAllDataFromJson("firestations", FireStation.class);
    List<String> addresses = new ArrayList<>();

    for (FireStation fireStation : fireStations) {
      if (fireStation.getStation().equals(String.valueOf(stationNumber))) {
        addresses.add(fireStation.getAddress());
      }
    }

    return addresses;
  }

  public Integer getStationForAddress(String address) {
    List<FireStation> fireStations = dataLoader.loadAllDataFromJson("firestations", FireStation.class);
    Integer stationNumber = null;

    for (FireStation fireStation : fireStations) {
      if (fireStation.getAddress().equals(address)) {
        stationNumber = Integer.valueOf(fireStation.getStation());
        break; // Once we find the station, we can stop the loop.
      }
    }

    return stationNumber;
  }
}