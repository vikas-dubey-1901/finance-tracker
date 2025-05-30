package com.vikas.financetracker.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AuthRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    private String email;  // required for registration only

}

