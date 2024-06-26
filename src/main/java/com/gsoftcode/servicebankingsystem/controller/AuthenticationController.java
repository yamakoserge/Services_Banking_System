package com.gsoftcode.servicebankingsystem.controller;

import com.gsoftcode.servicebankingsystem.dto.AuthenticationRequest;
import com.gsoftcode.servicebankingsystem.dto.SignupRequestDTO;
import com.gsoftcode.servicebankingsystem.dto.UserDto;
import com.gsoftcode.servicebankingsystem.entity.User;
import com.gsoftcode.servicebankingsystem.repository.UserRepository;
import com.gsoftcode.servicebankingsystem.services.authentication.AuthService;
import com.gsoftcode.servicebankingsystem.services.jwt.UserDetailsServiceImpl;
import com.gsoftcode.servicebankingsystem.utils.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static String HEADER_STRING = "Authorization";

    @PostMapping("/client/sign-up")
    public ResponseEntity<?> singupClient(@RequestBody SignupRequestDTO signupRequestDTO){
       if (authService.presentByEmail(signupRequestDTO.getEmail())){
           return  new ResponseEntity<>("Ce Client existe déjà!", HttpStatus.NOT_ACCEPTABLE);
       }
        UserDto createUser = authService.signupClient(signupRequestDTO);

        return new ResponseEntity<>(createUser,HttpStatus.OK);

    }

    @PostMapping("/company/sign-up")
    public ResponseEntity<?> signupCompany(@RequestBody SignupRequestDTO signupRequestDTO){
        if (authService.presentByEmail(signupRequestDTO.getEmail())){
            return  new ResponseEntity<>("Cette Company existe déjà!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createUser = authService.signupCompany(signupRequestDTO);

        return new ResponseEntity<>(createUser,HttpStatus.OK);
    }

    @PostMapping({"/authenticate"})
    public void  createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails= userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final  String jwt = jwtUtil.generateToken(userDetails.getUsername());
        User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());

        response.getWriter().write(new JSONObject()
                .put("userId", user.getId())
                .put("role", user.getRole())
                .toString()
        );
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization" +
                "X-PINGOTHER, Origin, X-Request-with, Accept, Content-Type, X-Custom-header");

        response.addHeader(HEADER_STRING, TOKEN_PREFIX+ jwt);

    }
}
