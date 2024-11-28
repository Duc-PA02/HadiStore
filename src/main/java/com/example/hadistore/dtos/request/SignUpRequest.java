package com.example.hadistore.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpRequest {
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String phone;
    private String address;
    private Boolean gender;
    private Boolean status;
    private String image;
    private LocalDate registerDate;
    private Integer roleId;
}
