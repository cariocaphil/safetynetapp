package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

  // Assuming you have appropriate data access methods (e.g., using repositories)

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
    for (Person existingPerson : personList) {
      if (existingPerson.getFirstName().equals(person.getFirstName()) &&
          existingPerson.getLastName().equals(person.getLastName())) {
        return false;
      }
    }

    personList.add(person);
    return true;
  }

  public boolean updatePerson(Person updatedPerson) {
    for (Person existingPerson : personList) {
      if (existingPerson.getFirstName().equals(updatedPerson.getFirstName()) &&
          existingPerson.getLastName().equals(updatedPerson.getLastName())) {
        existingPerson.setAddress(updatedPerson.getAddress());
        existingPerson.setCity(updatedPerson.getCity());
        existingPerson.setZip(updatedPerson.getZip());
        existingPerson.setPhone(updatedPerson.getPhone());
        existingPerson.setEmail(updatedPerson.getEmail());

        return true;
      }
    }

    return false;
  }

  public boolean deletePerson(String firstName, String lastName) {
    for (Person person : personList) {
      if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
        personList.remove(person);
        return true;
      }
    }

    return false;
  }
}
