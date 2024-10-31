package com.example.agency_amazon_task.service;

import com.example.agency_amazon_task.dto.LoginUserDto;
import com.example.agency_amazon_task.dto.RegisterUserDto;
import com.example.agency_amazon_task.model.User;
import com.example.agency_amazon_task.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  public User signup(RegisterUserDto input) {
    User user = new User();
    user.setEmail(input.getEmail());
    user.setUsername(input.getUsername());
    user.setPassword(passwordEncoder.encode(input.getPassword()));
    return userRepository.save(user);

  }

  public User authentication(LoginUserDto input) {
    System.out.println(userRepository.findByUsername(input.getUsername()));
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    input.getUsername(),
                    input.getPassword()
            )
    );

    return userRepository.findByUsername(input.getUsername()).orElseThrow();
  }
}
