package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.utilities.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityEmailService {

  @Autowired
  private DataLoader dataLoader;

  public List<String> getCommunityEmails(String city) {
    Logger.debug("Received request for CommunityEmails with city: {}", city);

    List<Person> allPeople = dataLoader.loadAllDataFromJson("persons", Person.class);
    List<String> communityEmails = new ArrayList<>();

    for (Person person : allPeople) {
      if (person.getCity().equalsIgnoreCase(city) && person.getEmail() != null) {
        communityEmails.add(person.getEmail());
      }
    }

    if (!communityEmails.isEmpty()) {
      Logger.info("CommunityEmails found for city: {}", city);
    } else {
      Logger.info("No CommunityEmails found for city: {}", city);
    }

    return communityEmails;
  }
}
