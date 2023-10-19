package com.safetynetapp.controllers;

import com.safetynetapp.services.CommunityEmailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommunityEmailController.class)
class CommunityEmailControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CommunityEmailService communityEmailService;

  @Test
  void getCommunityEmails() throws Exception {
    String city = "Culver"; // Example city

    List<String> mockEmails = Arrays.asList(
        "john@example.com",
        "jane@example.com"
    );

    when(communityEmailService.getCommunityEmails(anyString())).thenReturn(mockEmails);

    mockMvc.perform(get("/communityEmail")
            .param("city", city)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0]").value("john@example.com"))
        .andExpect(jsonPath("$[1]").value("jane@example.com"));
  }
}
