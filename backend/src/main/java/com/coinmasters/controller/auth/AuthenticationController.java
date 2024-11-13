package com.coinmasters.controller.auth;

import com.coinmasters.exceptions.NoSuchUserException;
import com.coinmasters.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    private ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws UserAlreadyExistsException {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    private ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) throws NoSuchUserException {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
