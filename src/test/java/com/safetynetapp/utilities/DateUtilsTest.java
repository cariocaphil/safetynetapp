package com.safetynetapp.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DateUtilsTest {

  @Test
  void testCalculateAge() {
    // Test with a valid birthdate
    String validBirthdate = "10/30/1990";
    int age = DateUtils.calculateAge(validBirthdate);
    assertEquals(33, age, "Expected age is 33");

    // Test with an invalid birthdate format
    String invalidBirthdate = "1990-10-30";
    assertThrows(IllegalArgumentException.class, () -> {
      DateUtils.calculateAge(invalidBirthdate);
    }, "Invalid birthdate format, expected IllegalArgumentException");
  }
}
