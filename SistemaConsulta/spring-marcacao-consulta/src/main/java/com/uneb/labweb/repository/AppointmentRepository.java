package com.uneb.labweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.dto.response.TempDTO;
import com.uneb.labweb.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT new com.uneb.labweb.dto.response.TempDTO(u1.id, u2.name, s.name, hc.name, hc.address) " 
        + "FROM Appointment a "
        + "LEFT JOIN a.doctor d "
        + "LEFT JOIN a.healthCenter hc "
        + "LEFT JOIN a.specialty s "
        + "LEFT JOIN a.user u1 "
        + "LEFT JOIN d.user u2 "
        + "WHERE a.id = :appointmentId")
    TempDTO getAppointmentData(@Param("appointmentId") Long appointmentId);


    @Query("SELECT a "
        + "FROM Appointment a "
        + "WHERE a.specialty.id = :specialtyId "
        + "AND a.healthCenter.id = :healthCenterId")
    List<Appointment> findBySpecialtyAndHealthCenter(@Param("specialtyId") Long specialtyId, @Param("healthCenterId") Long healthCenterId);
}
