package com.safetynetapp.models;

import com.safetynetapp.models.FireStation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FireStationTest {

  @Test
  void testGetAddress() {
    FireStation fireStation = new FireStation("123 Main St", "Station 1");
    assertEquals("123 Main St", fireStation.getAddress());
  }

  @Test
  void testGetStation() {
    FireStation fireStation = new FireStation("123 Main St", "Station 1");
    assertEquals("Station 1", fireStation.getStation());
  }

  @Test
  void testSetAddress() {
    FireStation fireStation = new FireStation();
    fireStation.setAddress("456 Oak St");
    assertEquals("456 Oak St", fireStation.getAddress());
  }

  @Test
  void testSetStation() {
    FireStation fireStation = new FireStation();
    fireStation.setStation("Station 2");
    assertEquals("Station 2", fireStation.getStation());
  }
}
