package com.uneb.labweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.model.HealthCenter;

@Repository
public interface HealthCenterRepository extends JpaRepository<HealthCenter, Long> {
    
}
