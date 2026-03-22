package com.BMG_System_POC.demo.service;

import com.BMG_System_POC.demo.entity.User;
import com.BMG_System_POC.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    // Inyección de dependencias por constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // Próxima lógica de encriptación de contraseña aquí
        return userRepository.save(user);
    }
}
