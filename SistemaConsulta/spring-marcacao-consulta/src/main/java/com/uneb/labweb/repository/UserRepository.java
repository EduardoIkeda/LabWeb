package com.uneb.labweb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    // Método para encontrar um usuário pelo número do cartão SUS
    Optional<User> findBySusCardNumber(String susCardNumber);
}