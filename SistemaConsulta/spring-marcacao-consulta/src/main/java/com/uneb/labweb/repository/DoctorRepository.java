package com.uneb.labweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Consulta para obter o nome do médico baseado no seu ID
    @Query("SELECT u.name " +
       "FROM Doctor d " +
       "JOIN d.user u " +
       "WHERE d.id = :doctorId")
    String getDoctorName(@Param("doctorId") Long doctorId);

    // Consulta para encontrar todos os médicos associados a um posto de saúde específico
    @Query("SELECT d FROM Doctor d JOIN d.healthCenters hc WHERE hc.id = :healthCenterId")
    List<Doctor> findByHealthCenterId(@Param("healthCenterId") Long healthCenterId);

    // Consulta para buscar o ID do médico associado a um determinado usuário
    @Query("SELECT d.id " +
       "FROM User u " +
       "JOIN u.doctor d " +
       "WHERE u.id = :userId")
    Long findDoctorIdByUserId(@Param("userId") Long userId);
}
