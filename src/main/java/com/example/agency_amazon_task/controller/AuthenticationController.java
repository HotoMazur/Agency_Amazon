package com.example.agency_amazon_task.controller;

import com.example.agency_amazon_task.dto.LoginUserDto;
import com.example.agency_amazon_task.dto.RegisterUserDto;
import com.example.agency_amazon_task.model.LoginResponse;
import com.example.agency_amazon_task.model.User;
import com.example.agency_amazon_task.service.AuthenticationService;
import com.example.agency_amazon_task.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto){
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        System.out.println("start");
        User authenticateUser = authenticationService.authentication(loginUserDto);
    System.out.println(authenticateUser);

        String jwtToken = jwtService.generateToken(authenticateUser);
    System.out.println(jwtToken);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
