package com.codeng.todo.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignUpDTO {

    private String name;
    private String email;
    private String username;
    private String password;
    private String role;

}
