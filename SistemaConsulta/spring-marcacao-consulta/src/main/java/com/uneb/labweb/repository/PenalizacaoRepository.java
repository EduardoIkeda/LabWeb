package com.uneb.labweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uneb.labweb.model.Penalizacao;

public interface PenalizacaoRepository extends JpaRepository<Penalizacao, Long> {
    
}
