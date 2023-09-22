package com.safetynetapp.services;

import com.safetynetapp.models.Person;
import com.safetynetapp.models.PersonDetails;
import com.safetynetapp.models.PersonWithAgeAndMedicalDetails;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonInfoService {

  @Autowired
  private DataLoader dataLoader;

  @Autowired
  private DataUtils dataUtils;

  // Assuming you have appropriate data access methods (e.g., using repositories)

  public List<PersonDetails> getPersonInfo(String firstName, String lastName) {
    // Implement the logic to fetch person information based on first name and last name
    List<Person> allPeople = dataLoader.loadAllDataFromJson("persons", Person.class);
    List<PersonDetails> peopleWithGivenName = new ArrayList<>();

    for (Person person : allPeople) {
      if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
        PersonDetails personWithDetails = dataUtils.getPersonDetailsFor(
            person);

        peopleWithGivenName.add(personWithDetails);
      }
    }

    return peopleWithGivenName;

  }

}
