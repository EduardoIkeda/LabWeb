package com.uneb.labweb.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.dto.response.TempDTO;
import com.uneb.labweb.model.Appointment;
import com.uneb.labweb.model.Doctor;

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

    @Query("SELECT a "
        + "FROM Appointment a "
        + "WHERE a.user.id = :userId")
    List<Appointment> findByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT DISTINCT CAST(a.appointmentDateTime AS date)
        FROM Appointment a
        WHERE a.specialty.id = :specialtyId AND a.healthCenter.id = :healthCenterId
        ORDER BY CAST(a.appointmentDateTime AS date)
    """)
    List<java.sql.Date> findDistinctDates(@Param("specialtyId") Long specialtyId, @Param("healthCenterId") Long healthCenterId);

    @Query("""
        SELECT DISTINCT d
        FROM Appointment a
        JOIN a.doctor d
        WHERE CAST(a.appointmentDateTime AS date) = :date
        AND a.specialty.id = :specialtyId
        AND a.healthCenter.id = :healthCenterId
    """)
    List<Doctor> findDoctorsByDate(@Param("date") LocalDate date, @Param("specialtyId") Long specialtyId, @Param("healthCenterId") Long healthCenterId);

    @Query("""
        SELECT a
        FROM Appointment a
        WHERE CAST(a.appointmentDateTime AS date) = :date
        AND a.doctor.id = :doctorId
        AND a.specialty.id = :specialtyId
        AND a.healthCenter.id = :healthCenterId
        ORDER BY a.appointmentDateTime
    """)
    List<Appointment> findByDateAndDoctor(
            @Param("date") LocalDate date,
            @Param("doctorId") Long doctorId,
            @Param("specialtyId") Long specialtyId,
            @Param("healthCenterId") Long healthCenterId
    );
}
