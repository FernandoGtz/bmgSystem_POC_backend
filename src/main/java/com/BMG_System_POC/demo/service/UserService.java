package com.BMG_System_POC.demo.service;

import com.BMG_System_POC.demo.dto.UserCreateDTO;
import com.BMG_System_POC.demo.dto.UserResponseDTO;
import com.BMG_System_POC.demo.entity.User;
import com.BMG_System_POC.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    // Inyección de dependencias por constructor
    public UserService(UserRepository userRepository, ModelMapper modelMapper, SecurityFilterChain securityFilterChain, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        User user = modelMapper.map(userCreateDTO, User.class);
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }
}
