package com.codeng.todo.controllers;

import com.codeng.todo.dto.LoginDTO;
import com.codeng.todo.dto.SignUpDTO;
import com.codeng.todo.models.Role;
import com.codeng.todo.models.User;
import com.codeng.todo.repository.RoleRepository;
import com.codeng.todo.repository.UserRepository;
import com.codeng.todo.services.AuthService;
import com.codeng.todo.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUpDTO){
        return authService.signUp(signUpDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        System.out.println("Username: " + loginDTO.getUsernameOrEmail());
        System.out.println("Password: " + loginDTO.getPassword());
        boolean isAuthenticated = authService.verifyUser(loginDTO.getUsernameOrEmail(), loginDTO.getPassword());
        if(!isAuthenticated){
            return new ResponseEntity<>("Username or Password is incorrect !!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tokenService.tokenize(loginDTO), HttpStatus.OK);
    }

}
