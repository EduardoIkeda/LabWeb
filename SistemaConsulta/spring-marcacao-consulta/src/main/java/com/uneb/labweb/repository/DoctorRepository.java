package com.uneb.labweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    @Query("SELECT u.name " +
       "FROM Doctor d " +
       "JOIN d.user u " +
       "WHERE d.id = :doctorId")
    String getDoctorName(@Param("doctorId") Long doctorId);

    @Query("SELECT d FROM Doctor d JOIN d.healthCenters hc WHERE hc.id = :healthCenterId")
    List<Doctor> findByHealthCenterId(@Param("healthCenterId") Long healthCenterId);
}
