package com.safetynetapp.services;

import com.safetynetapp.models.MedicalRecord;
import com.safetynetapp.utilities.DataLoader;
import com.safetynetapp.utilities.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.List;

@Service
public class MedicalRecordService {

  @Autowired
  private DataLoader dataLoader;

  @Autowired
  private DataUtils dataUtils;

  public boolean addMedicalRecord(MedicalRecord medicalRecord) {
    List<MedicalRecord> medicalRecords = dataLoader.loadAllDataFromJson("medicalrecords",
        MedicalRecord.class);

    for (MedicalRecord existingRecord : medicalRecords) {
      if (existingRecord.getFirstName().equals(medicalRecord.getFirstName())
          && existingRecord.getLastName().equals(medicalRecord.getLastName())) {
        Logger.warn("Medical record already exists for: {} {}", medicalRecord.getFirstName(),
            medicalRecord.getLastName());
        return false; // Medical record already exists
      }
    }

    medicalRecords.add(medicalRecord);
    Logger.info("Added new medical record for: {} {}", medicalRecord.getFirstName(),
        medicalRecord.getLastName());

    return true; // Medical record added successfully
  }

  public boolean updateMedicalRecord(MedicalRecord updatedMedicalRecord) {
    List<MedicalRecord> medicalRecords = dataLoader.loadAllDataFromJson("medicalrecords",
        MedicalRecord.class);

    for (MedicalRecord existingRecord : medicalRecords) {
      if (existingRecord.getFirstName().equals(updatedMedicalRecord.getFirstName())
          && existingRecord.getLastName().equals(updatedMedicalRecord.getLastName())) {
        existingRecord.setMedications(updatedMedicalRecord.getMedications());
        existingRecord.setAllergies(updatedMedicalRecord.getAllergies());
        Logger.info("Updated medical record for: {} {}", updatedMedicalRecord.getFirstName(),
            updatedMedicalRecord.getLastName());
        return true; // Medical record updated successfully
      }
    }

    Logger.warn("Medical record not found for: {} {}", updatedMedicalRecord.getFirstName(),
        updatedMedicalRecord.getLastName());
    return false; // Medical record not found
  }

  public boolean deleteMedicalRecord(String firstName, String lastName) {
    List<MedicalRecord> medicalRecords = dataLoader.loadAllDataFromJson("medicalrecords",
        MedicalRecord.class);

    for (int i = 0; i < medicalRecords.size(); i++) {
      if (medicalRecords.get(i).getFirstName().equals(firstName) && medicalRecords.get(i)
          .getLastName().equals(lastName)) {
        medicalRecords.remove(i);
        Logger.info("Deleted medical record for: {} {}", firstName, lastName);
        return true; // Medical record deleted successfully
      }
    }

    Logger.warn("Medical record not found for: {} {}", firstName, lastName);
    return false; // Medical record not found
  }
}
