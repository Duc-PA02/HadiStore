package com.example.hadistore.service;

import com.example.hadistore.dtos.request.LoginRequest;
import com.example.hadistore.dtos.request.SignUpRequest;
import com.example.hadistore.dtos.response.LoginResponse;
import com.example.hadistore.entity.User;

public interface UserService {
    User createUser(SignUpRequest signUpRequest);
    LoginResponse login(LoginRequest loginRequest);
}
