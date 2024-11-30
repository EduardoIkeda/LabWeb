package com.uneb.labweb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.AuthRequestDTO;
import com.uneb.labweb.dto.AuthResponseDTO;
import com.uneb.labweb.dto.UserDTO;
import com.uneb.labweb.dto.mapper.UserMapper;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.model.User;
import com.uneb.labweb.repository.UserRepository;
import com.uneb.labweb.security.TokenService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(PasswordEncoder passwordEncoder, TokenService tokenService, UserRepository userRepository,
            UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public ResponseEntity login(@Valid @NotNull AuthRequestDTO body) {
        return userRepository.findBySusCardNumber(body.susCardNumber())
                .map(user -> {
                    if(passwordEncoder.matches(body.password(), user.getPassword())) {
                        String token = this.tokenService.generateToken(user);
                        return ResponseEntity.ok(new AuthResponseDTO(user.getName(), user.getRole().getValue(), token));
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
            newUser.setRole(userMapper.convertRoleValue(body.role()));
            userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new AuthResponseDTO(newUser.getName(), newUser.getRole().getValue(), token));
        }
        return ResponseEntity.badRequest().build();
    }




    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(@NotNull @Positive Long id) {   
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    // public UserDTO createUser(@Valid @NotNull UserDTO userDTO) {
    //     return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    // }

    public UserDTO updateUser(@NotNull @Positive Long id, @Valid @NotNull UserDTO userDTO) {
        return userRepository.findById(id)
                .map(recordFound -> {    
                    recordFound.setSusCardNumber(userDTO.susCardNumber());
                    recordFound.setName(userDTO.name());
                    recordFound.setCpf(userDTO.cpf());
                    recordFound.setPhone(userDTO.phone());
                    recordFound.setEmail(userDTO.email());
                    recordFound.setPassword(passwordEncoder.encode(userDTO.password()));
                    recordFound.setRole(userMapper.convertRoleValue(userDTO.role()));
                    
                    return userMapper.toDTO(userRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
        
    }

    public void deleteUser(@NotNull @Positive Long id) {
        userRepository.delete(userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
