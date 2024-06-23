package com.codeng.todo.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String usernameOrEmail;
    private String password;
}
