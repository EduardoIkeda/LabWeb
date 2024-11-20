package com.uneb.labweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.model.Penalty;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    
}
