package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

  @Autowired
  private DataLoader dataLoader;

  @Autowired
  private DataUtils dataUtils;

  public PersonService(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
  }

  @Autowired
  public PersonService(DataLoader dataLoader, DataUtils dataUtils) {
    this.dataLoader = dataLoader;
    this.dataUtils = dataUtils;
    this.personList = dataUtils.loadJsonData("persons", Person.class);
  }

  private List<Person> personList = new ArrayList<>();

  public boolean addPerson(Person person) {
    Logger.info("Adding person: {} {}", person.getFirstName(), person.getLastName());

    for (Person existingPerson : personList) {
      if (existingPerson.getFirstName().equals(person.getFirstName()) &&
          existingPerson.getLastName().equals(person.getLastName())) {
        Logger.info("Person already exists: {} {}", existingPerson.getFirstName(),
            existingPerson.getLastName());
        return false;
      }
    }

    personList.add(person);
    Logger.info("Added person: {} {}", person.getFirstName(), person.getLastName());
    return true;
  }

  public boolean updatePerson(Person updatedPerson) {
    Logger.info("Updating person: {} {}", updatedPerson.getFirstName(),
        updatedPerson.getLastName());

    for (Person existingPerson : personList) {
      if (existingPerson.getFirstName().equals(updatedPerson.getFirstName()) &&
          existingPerson.getLastName().equals(updatedPerson.getLastName())) {
        existingPerson.setAddress(updatedPerson.getAddress());
        existingPerson.setCity(updatedPerson.getCity());
        existingPerson.setZip(updatedPerson.getZip());
        existingPerson.setPhone(updatedPerson.getPhone());
        existingPerson.setEmail(updatedPerson.getEmail());

        Logger.info("Updated person: {} {}, Address: {}, City: {}, Zip: {}, Phone: {}, Email: {}",
            existingPerson.getFirstName(), existingPerson.getLastName(),
            existingPerson.getAddress(), existingPerson.getCity(),
            existingPerson.getZip(), existingPerson.getPhone(), existingPerson.getEmail());
        return true;
      }
    }

    Logger.info("No person found for updating: {} {}", updatedPerson.getFirstName(),
        updatedPerson.getLastName());
    return false;
  }

  public boolean deletePerson(String firstName, String lastName) {
    Logger.info("Deleting person: {} {}", firstName, lastName);

    for (Person person : personList) {
      if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
        personList.remove(person);
        Logger.info("Deleted person: {} {}", person.getFirstName(), person.getLastName());
        return true;
      }
    }

    Logger.info("No person found for deletion: {} {}", firstName, lastName);
    return false;
  }
}
