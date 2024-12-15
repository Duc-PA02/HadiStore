package com.example.hadistore.controller;

import com.example.hadistore.dtos.request.SignUpRequest;
import com.example.hadistore.dtos.response.ResponseData;
import com.example.hadistore.entity.User;
import com.example.hadistore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.findUserByStatusTrue());
    }
    @GetMapping("email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }
    @PostMapping ResponseEntity<User> createUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(userService.createUser(signUpRequest));
    }
}
