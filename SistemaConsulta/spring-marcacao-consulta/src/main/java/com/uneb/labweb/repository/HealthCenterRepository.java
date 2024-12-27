package com.uneb.labweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.model.HealthCenter;

@Repository
public interface HealthCenterRepository extends JpaRepository<HealthCenter, Long> {

    // Consulta para encontrar postos de saúde associados a uma especialidade específica
    @Query("SELECT hc FROM HealthCenter hc JOIN hc.specialties s WHERE s.id = :specialtyId")
    List<HealthCenter> findBySpecialtyId(@Param("specialtyId") Long specialtyId);
}
