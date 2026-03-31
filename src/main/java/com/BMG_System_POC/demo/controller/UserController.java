package com.BMG_System_POC.demo.controller;

import com.BMG_System_POC.demo.dto.UserCreateDTO;
import com.BMG_System_POC.demo.dto.UserResponseDTO;
import com.BMG_System_POC.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO response = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/test")
    public ResponseEntity<String> test(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName());
    }
}
