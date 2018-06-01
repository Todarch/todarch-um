package com.todarch.um.rest;

import com.todarch.um.Endpoints;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @GetMapping(Endpoints.UP)
  public String up() {
    return "I am Todarch User Management Service, up and running";
  }
}
