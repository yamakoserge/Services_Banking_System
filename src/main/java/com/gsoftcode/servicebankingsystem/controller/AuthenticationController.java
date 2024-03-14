package com.gsoftcode.servicebankingsystem.controller;

import com.gsoftcode.servicebankingsystem.dto.SignupRequestDTO;
import com.gsoftcode.servicebankingsystem.dto.UserDto;
import com.gsoftcode.servicebankingsystem.services.authentication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/client/sign-up")
    public ResponseEntity<?> singupClient(@RequestBody SignupRequestDTO signupRequestDTO){
       if (authService.presentByEmail(signupRequestDTO.getEmail())){
           return  new ResponseEntity<>("Ce Client existe déjà!", HttpStatus.NOT_ACCEPTABLE);
       }
        UserDto createUser = authService.signupClient(signupRequestDTO);

        return new ResponseEntity<>(createUser,HttpStatus.OK);


    }

    @PostMapping("/company/sign-up")
    public ResponseEntity<?> singupCompany(@RequestBody SignupRequestDTO signupRequestDTO){
        if (authService.presentByEmail(signupRequestDTO.getEmail())){
            return  new ResponseEntity<>("Cette Company existe déjà!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createUser = authService.signupClient(signupRequestDTO);

        return new ResponseEntity<>(createUser,HttpStatus.OK);


    }
}
