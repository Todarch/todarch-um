package com.todarch.um.rest;

import com.todarch.um.Endpoints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckController {

  @Value("${testkey:defaultValue}")
  private String testKey;

  @GetMapping(Endpoints.UP)
  public String up() {
    log.info("Test key: {}", testKey);
    return "I am Todarch User Management Service, up and running";
  }
}
