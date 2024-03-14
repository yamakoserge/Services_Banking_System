package com.gsoftcode.servicebankingsystem.services.authentication;

import com.gsoftcode.servicebankingsystem.dto.SignupRequestDTO;
import com.gsoftcode.servicebankingsystem.dto.UserDto;

public interface AuthService {
    UserDto signupClient(SignupRequestDTO signupRequestDTO);

    UserDto company(SignupRequestDTO signupRequestDTO);

    boolean presentByEmail(String email);

}
