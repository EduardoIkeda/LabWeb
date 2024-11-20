package com.uneb.labweb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uneb.labweb.dto.AuthRequestDTO;
import com.uneb.labweb.dto.TesteDTO;
import com.uneb.labweb.dto.UserDTO;
import com.uneb.labweb.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/teste")
    public TesteDTO teste(){
        return new TesteDTO("Usuário autenticado!");
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid @NotNull AuthRequestDTO authDTO) {
        return userService.login(authDTO);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid @NotNull UserDTO userDTO) {
        return userService.register(userDTO);
    }



    @GetMapping
    public List<UserDTO> findAllUsers() {
        return userService.findAllUsers();  
    }

    @GetMapping("/{id}")
    public UserDTO findUserById(@PathVariable @NotNull @Positive Long id) {
        return userService.findUserById(id);
    }

    // @PostMapping
    // @ResponseStatus(code = HttpStatus.CREATED)
    // public UserDTO createUser(@RequestBody @Valid @NotNull UserDTO userDTO) {
    //     return userService.createUser(userDTO);
    // }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @NotNull @Positive Long id) {
        userService.deleteUser(id);
    }
}