package com.gsoftcode.servicebankingsystem.services.authentication;


import com.gsoftcode.servicebankingsystem.dto.SignupRequestDTO;
import com.gsoftcode.servicebankingsystem.dto.UserDto;
import com.gsoftcode.servicebankingsystem.enums.UserRole;
import com.gsoftcode.servicebankingsystem.repository.UserRepository;
import com.gsoftcode.servicebankingsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    public UserDto signupClient(SignupRequestDTO signupRequestDTO) {
        User user = new User();

        user.setName(signupRequestDTO.getName());
        user.setLastname(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));

        user.setRole(UserRole.CLIENT);

        return userRepository.save(user).getDto();
    }

    public boolean presentByEmail(String email) {

        return userRepository.findFirstByEmail(email) != null;
    }


    public UserDto signupCompany(SignupRequestDTO signupRequestDTO) {
        User user = new User();

        user.setName(signupRequestDTO.getName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));

        user.setRole(UserRole.COMPANY);

        return userRepository.save(user).getDto();
    }

}
