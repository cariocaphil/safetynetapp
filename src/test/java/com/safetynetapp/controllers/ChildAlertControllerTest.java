package com.safetynetapp.controllers;

import com.safetynetapp.models.ChildInfoResponse;
import com.safetynetapp.models.SimpleChildInfo;
import com.safetynetapp.services.ChildAlertService;
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

@WebMvcTest(ChildAlertController.class)
class ChildAlertControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ChildAlertService childAlertService;

  @Test
  void getChildAlertInfo() throws Exception {
    String address = "644 Gershwin Cir"; // Example address

    ChildInfoResponse mockResponse = new ChildInfoResponse();
    List<SimpleChildInfo> children = Arrays.asList(
        new SimpleChildInfo("Tenley", "Boyd", 11),
        new SimpleChildInfo("Roger", "Boyd", 6),
        new SimpleChildInfo("Tessa", "Carman", 11),
        new SimpleChildInfo("Zach", "Zemicks", 6),
        new SimpleChildInfo("Kendrik", "Stelzer", 9)
    );

    mockResponse.setChildren(children);

    when(childAlertService.getChildAlertInfo(anyString())).thenReturn(mockResponse);

    mockMvc.perform(get("/childAlert")
            .param("address", address)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.children[0].firstName").value("Tenley"))
        .andExpect(jsonPath("$.children[0].lastName").value("Boyd"))
        .andExpect(jsonPath("$.children[0].age").value(11))
        .andExpect(jsonPath("$.children[1].firstName").value("Roger"))
        .andExpect(jsonPath("$.children[1].lastName").value("Boyd"))
        .andExpect(jsonPath("$.children[1].age").value(6))
        .andExpect(jsonPath("$.children[2].firstName").value("Tessa"))
        .andExpect(jsonPath("$.children[2].lastName").value("Carman"))
        .andExpect(jsonPath("$.children[2].age").value(11))
        .andExpect(jsonPath("$.children[3].firstName").value("Zach"))
        .andExpect(jsonPath("$.children[3].lastName").value("Zemicks"))
        .andExpect(jsonPath("$.children[3].age").value(6))
        .andExpect(jsonPath("$.children[4].firstName").value("Kendrik"))
        .andExpect(jsonPath("$.children[4].lastName").value("Stelzer"))
        .andExpect(jsonPath("$.children[4].age").value(9));
  }

}
