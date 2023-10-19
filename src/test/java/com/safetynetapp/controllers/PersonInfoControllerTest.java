package com.safetynetapp.controllers;

import com.safetynetapp.models.PersonDetails;
import com.safetynetapp.services.PersonInfoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonInfoControllerTest {

  @Mock
  private PersonInfoService personInfoService = Mockito.mock(PersonInfoService.class);

  @Test
  void getPersonInfo() {
    PersonInfoController controller = new PersonInfoController(personInfoService);

    // Mocking the service response
    String firstName = "John";
    String lastName = "Doe";
    List<String> mockMedications = Collections.singletonList("Med1");
    List<String> mockAllergies = Collections.singletonList("Allergy1");
    List<PersonDetails> mockPersonInfoList = Collections.singletonList(
        new PersonDetails(firstName, lastName, "123 Main St", 30, "john.doe@example.com", mockMedications, mockAllergies)
    );
    Mockito.when(personInfoService.getPersonInfo(firstName, lastName)).thenReturn(mockPersonInfoList);

    ResponseEntity<List<PersonDetails>> responseEntity = controller.getPersonInfo(firstName, lastName);

    // Verify the response
    assertEquals(200, responseEntity.getStatusCodeValue());
    assertEquals(mockPersonInfoList, responseEntity.getBody());
  }
}
