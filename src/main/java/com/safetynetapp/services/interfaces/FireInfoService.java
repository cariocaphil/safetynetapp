package com.safetynetapp.services.interfaces;

import com.safetynetapp.models.FireInfoResponse;

public interface FireInfoService {
  FireInfoResponse getFireInfo(String address);
}