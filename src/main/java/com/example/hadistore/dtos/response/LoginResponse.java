package com.example.hadistore.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private List<String> roles;
    private String token;
}
