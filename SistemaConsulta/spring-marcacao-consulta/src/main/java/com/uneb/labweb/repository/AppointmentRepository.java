package com.uneb.labweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.dto.response.TempDTO;
import com.uneb.labweb.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    @Query("SELECT new com.uneb.labweb.dto.response.TempDTO(u1.id, u2.name, s.name, hc.name, hc.address) " +
        "FROM Appointment a " +
        "JOIN a.doctor d " +
        "JOIN a.healthCenter hc " +
        "JOIN a.specialty s " +
        "JOIN a.user u1 " +
        "JOIN d.user u2 " +
        "WHERE a.id = :appointmentId")
    TempDTO getAppointmentData(@Param("appointmentId") Long appointmentId);
}
