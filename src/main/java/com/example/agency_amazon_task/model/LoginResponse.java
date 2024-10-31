package com.example.agency_amazon_task.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
  private String token;
  private long expiresIn;

}
