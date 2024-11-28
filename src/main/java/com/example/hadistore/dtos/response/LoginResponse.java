package com.example.hadistore.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String token;
}
