package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.entity.HttpResponse;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.form.LoginForm;

import com.codemasters.accommodateme.service.authService.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody User registerRequest){
        return ResponseEntity.ok(authService.registerAdmin(registerRequest));
    }

    @PostMapping("/register/user")
    public ResponseEntity<HttpResponse> registerUser(@RequestBody User registerRequest) {

        return ResponseEntity.ok(authService.register(registerRequest));
    }



    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginForm loginRequest){
        log.info("Signing In");
        return ResponseEntity.ok(authService.login(loginRequest));


    }

}
