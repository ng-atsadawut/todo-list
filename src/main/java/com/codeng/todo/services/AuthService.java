package com.codeng.todo.services;

import com.codeng.todo.dto.SignUpDTO;
import com.codeng.todo.models.Role;
import com.codeng.todo.models.User;
import com.codeng.todo.repository.RoleRepository;
import com.codeng.todo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> signUp(SignUpDTO signUpDTO){

        if(userRepository.existsByUsername(signUpDTO.getUsername())){
            return new ResponseEntity<>("Username is already taken !!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpDTO.getEmail())){
            return new ResponseEntity<>("Email is already taken !!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDTO.getName());
        user.setEmail(signUpDTO.getEmail());
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Role role = roleRepository.findByName(signUpDTO.getRole()).get();
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public boolean verifyUser(String usernameOrEmail, String password) {
        // Find user by username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElse(null);

        // Check if user exists and compare passwords
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}
