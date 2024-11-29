package com.example.hadistore.controller;

import com.example.hadistore.dtos.response.ResponseData;
import com.example.hadistore.entity.User;
import com.example.hadistore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("email/{email}")
    public ResponseData<User> getUserByEmail(@PathVariable String email){
        return new ResponseData<>(HttpStatus.OK, "Successfully", userService.findByEmail(email));
    }
}
