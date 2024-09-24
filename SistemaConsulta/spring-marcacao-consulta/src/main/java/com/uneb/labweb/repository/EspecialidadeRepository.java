package com.uneb.labweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uneb.labweb.model.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    
}
