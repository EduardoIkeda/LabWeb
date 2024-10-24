package com.uneb.labweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.model.Penalizacao;

@Repository
public interface PenalizacaoRepository extends JpaRepository<Penalizacao, Long> {
    
}
