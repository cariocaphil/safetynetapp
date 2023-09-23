package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.utilities.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityEmailService {

  @Autowired
  private DataLoader dataLoader;

  public List<String> getCommunityEmails(String city) {
    List<Person> allPeople = dataLoader.loadAllDataFromJson("persons", Person.class);
    List<String> communityEmails = new ArrayList<>();

    for (Person person : allPeople) {
      if (person.getCity().equalsIgnoreCase(city) && person.getEmail() != null) {
        communityEmails.add(person.getEmail());
      }
    }

    return communityEmails;
  }
}
