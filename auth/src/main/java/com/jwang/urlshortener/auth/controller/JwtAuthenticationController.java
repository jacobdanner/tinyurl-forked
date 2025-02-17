/* (C)2024 */
package com.jwang.urlshortener.auth.controller;

import com.jwang.urlshortener.auth.config.JwtTokenUtil;
import com.jwang.urlshortener.auth.model.JwtRequest;
import com.jwang.urlshortener.auth.model.JwtResponse;
import com.jwang.urlshortener.auth.model.UserDTO;
import com.jwang.urlshortener.auth.service.JwtUserDetailsService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService userDetailsService;

    public JwtAuthenticationController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @Valid @RequestBody JwtRequest authenticationRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO user) {
        if (userDetailsService.usernameExists(user)) {
            Map<String, List<String>> res = new HashMap<>();
            List<String> errors = new ArrayList<>();
            errors.add("username is in use");
            res.put("message", errors);
            return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userDetailsService.save(user));
    }
}
