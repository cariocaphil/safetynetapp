package com.safetynetapp.controllers;

import com.safetynetapp.services.CommunityEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommunityEmailController {

  private final CommunityEmailService communityEmailService;

  @Autowired
  public CommunityEmailController(CommunityEmailService communityEmailService) {
    this.communityEmailService = communityEmailService;
  }

  @GetMapping("/communityEmail")
  public ResponseEntity<List<String>> getCommunityEmails(@RequestParam("city") String city) {
    List<String> communityEmails = communityEmailService.getCommunityEmails(city);

    if (communityEmails.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(communityEmails);
  }
}
