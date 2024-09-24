package com.uneb.labweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uneb.labweb.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{}