package com.safetynetapp.controllers;

import com.safetynetapp.models.FireInfoResponse;
import com.safetynetapp.models.PersonWithAgeAndMedicalDetails;
import com.safetynetapp.services.FireService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FireController.class)
class FireControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FireService fireService;

  @Test
  void getFireInfo() throws Exception {
    String address = "123 Main St"; // Example address

    FireInfoResponse mockResponse = new FireInfoResponse();
    mockResponse.setStationNumber(1);

    List<PersonWithAgeAndMedicalDetails> peopleLivingAtAddress = Arrays.asList(
        new PersonWithAgeAndMedicalDetails("John", "Boyd", "841-874-6512", 37,
            Arrays.asList("aznol:350mg", "hydrapermazol:100mg"),
            Arrays.asList("nillacilan")),
        new PersonWithAgeAndMedicalDetails("Jane", "Boyd", "841-874-6513", 32,
            Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
            Arrays.asList()),
        new PersonWithAgeAndMedicalDetails("Jim", "Boyd", "841-874-6514", 10,
            Arrays.asList(),
            Arrays.asList())
    );

    mockResponse.setPeopleLivingAtAddress(peopleLivingAtAddress);

    when(fireService.getFireInfo(anyString())).thenReturn(mockResponse);

    mockMvc.perform(get("/fire")
            .param("address", address)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.stationNumber").value(1))
        .andExpect(jsonPath("$.peopleLivingAtAddress[0].firstName").value("John"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[0].lastName").value("Boyd"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[0].phone").value("841-874-6512"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[0].age").value(37))
        .andExpect(jsonPath("$.peopleLivingAtAddress[0].medications[0]").value("aznol:350mg"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[0].medications[1]").value("hydrapermazol:100mg"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[0].allergies[0]").value("nillacilan"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[1].firstName").value("Jane"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[1].lastName").value("Boyd"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[1].phone").value("841-874-6513"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[1].age").value(32))
        .andExpect(jsonPath("$.peopleLivingAtAddress[1].medications[0]").value("pharmacol:5000mg"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[1].medications[1]").value("terazine:10mg"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[1].medications[2]").value("noznazol:250mg"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[2].firstName").value("Jim"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[2].lastName").value("Boyd"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[2].phone").value("841-874-6514"))
        .andExpect(jsonPath("$.peopleLivingAtAddress[2].age").value(10));
  }


}
