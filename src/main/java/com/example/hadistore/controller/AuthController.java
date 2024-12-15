package com.example.hadistore.controller;

import com.example.hadistore.dtos.request.LoginRequest;
import com.example.hadistore.dtos.request.SignUpRequest;
import com.example.hadistore.dtos.response.LoginResponse;
import com.example.hadistore.dtos.response.ResponseData;
import com.example.hadistore.entity.User;
import com.example.hadistore.service.SendMailService;
import com.example.hadistore.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {
    UserService userService;
    SendMailService sendMailService;
    @PostMapping("signup")
    public ResponseData<?> signup(@Valid @RequestBody SignUpRequest signupRequest){
        userService.createUser(signupRequest);
        return new ResponseData<>(HttpStatus.OK, "Create user successfully");
    }
    @PostMapping("login")
    public ResponseData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseData<>(HttpStatus.OK, "Login successfully", userService.login(loginRequest));
    }
    @PostMapping("send-mail/otp")
    public ResponseEntity<Integer> sendOpt(@RequestBody String email) {
        int random_otp = (int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
        if (userService.existEmail(email)) {
            return ResponseEntity.notFound().build();
        }
        sendMailService.sendOtp(email, random_otp, "Xác nhận tài khoản!");
        return ResponseEntity.ok(random_otp);
    }
    @PostMapping("send-mail-forgot-password")
    public ResponseData<String> sendToken(@RequestBody String email){
        return new ResponseData<>(HttpStatus.NO_CONTENT, userService.sendToken(email));
    }
}
