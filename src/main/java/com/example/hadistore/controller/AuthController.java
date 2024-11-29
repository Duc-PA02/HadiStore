package com.example.hadistore.controller;

import com.example.hadistore.dtos.request.LoginRequest;
import com.example.hadistore.dtos.request.SignUpRequest;
import com.example.hadistore.dtos.response.LoginResponse;
import com.example.hadistore.dtos.response.ResponseData;
import com.example.hadistore.entity.User;
import com.example.hadistore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("signup")
    public ResponseData<User> signUp(@Valid @RequestBody SignUpRequest signupRequest){
        return new ResponseData<>(HttpStatus.OK, "Create user successfully", userService.createUser(signupRequest));
    }
    @PostMapping("login")
    public ResponseData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseData<>(HttpStatus.OK, "Login successfully", userService.login(loginRequest));
    }
}
