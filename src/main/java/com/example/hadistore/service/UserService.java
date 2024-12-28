package com.example.hadistore.service;

import com.example.hadistore.dtos.request.LoginRequest;
import com.example.hadistore.dtos.request.SignUpRequest;
import com.example.hadistore.dtos.response.LoginResponse;
import com.example.hadistore.entity.User;

import java.util.List;

public interface UserService {
    List<User> findUserByStatusTrue();
    User findById(Long id);
    User createUser(SignUpRequest signUpRequest);
    LoginResponse login(LoginRequest loginRequest);
    User findByEmail(String email);
    Boolean existEmail(String email);
    String sendToken(String email);
    User updateUser(Long userId, SignUpRequest signUpRequest);
    void deleteUser(Long userId);
}
