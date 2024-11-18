package com.uneb.labweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uneb.labweb.dto.AuthRequestDTO;
import com.uneb.labweb.dto.AuthResponseDTO;
import com.uneb.labweb.dto.UserDTO;
import com.uneb.labweb.model.User;
import com.uneb.labweb.repository.UserRepository;
import com.uneb.labweb.security.TokenService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public ResponseEntity login(@Valid @NotNull AuthRequestDTO body) {
        return userRepository.findBySusCardNumber(body.susCardNumber())
                .map(user -> {
                    if(passwordEncoder.matches(body.password(), user.getPassword())) {
                        String token = this.tokenService.generateToken(user);
                        return ResponseEntity.ok(new AuthResponseDTO(user.getName(), token));
                    }
                    
                    return ResponseEntity.badRequest().build();
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public ResponseEntity register(@Valid @NotNull UserDTO body) {
        Optional<User> user = userRepository.findBySusCardNumber(body.susCardNumber());

        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setSusCardNumber(body.susCardNumber());
            newUser.setName(body.name());
            newUser.setCpf(body.cpf());            
            newUser.setPhone(body.phone());
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new AuthResponseDTO(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }




    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
