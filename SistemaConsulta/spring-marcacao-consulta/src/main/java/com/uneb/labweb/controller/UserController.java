package com.uneb.labweb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uneb.labweb.dto.AuthRequestDTO;
import com.uneb.labweb.dto.TesteDTO;
import com.uneb.labweb.dto.UserDTO;
import com.uneb.labweb.model.User;
import com.uneb.labweb.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid @NotNull AuthRequestDTO body) {
        return userService.login(body);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid @NotNull UserDTO body) {
        return userService.register(body);
    }



    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    // Testando se a autenticação com Token JWT está funcionando
    @GetMapping("/teste")
    public ResponseEntity teste(){
        return ResponseEntity.ok(new TesteDTO("Usuário autenticado!"));
    }
}