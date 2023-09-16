package com.safetynetapp.utilities;

import com.safetynetapp.models.Person;
import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.models.FireStation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.core.io.ResourceLoader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DataLoader {

  private final ResourceLoader resourceLoader;

  public DataLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  public <T> List<T> loadAllDataFromJson(String fieldName, Class<T> valueType) {
    try {
      InputStream inputStream = resourceLoader.getResource("classpath:data.json").getInputStream();
      ObjectMapper objectMapper = new ObjectMapper();

      // Read the JSON data and get the list of items
      JsonNode rootNode = objectMapper.readTree(inputStream);
      JsonNode dataNode = rootNode.get(fieldName);

      // Convert the dataNode to a List<T>
      List<T> dataList = new ArrayList<>();

      for (JsonNode item : dataNode) {
        T dataItem = objectMapper.treeToValue(item, valueType);
        dataList.add(dataItem);
      }
      return dataList;
    } catch (IOException e) {
      e.printStackTrace();
      return null; // TODO: Handle exception
    }
  }
}