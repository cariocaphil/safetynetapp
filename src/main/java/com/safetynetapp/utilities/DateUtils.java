package com.safetynetapp.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.tinylog.Logger;

public class DateUtils {
  public static int calculateAge(String birthdate) {
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    Date today = new Date();
    try {
      Date dob = format.parse(birthdate);
      int age = today.getYear() - dob.getYear();
      if (today.getMonth() < dob.getMonth() || (today.getMonth() == dob.getMonth()
          && today.getDate() < dob.getDate())) {
        age--;
      }
      return age;
    } catch (ParseException e) {
      Logger.error(e, "Error parsing birthdate: {}", e.getMessage());
      return -1;
    }
  }
}