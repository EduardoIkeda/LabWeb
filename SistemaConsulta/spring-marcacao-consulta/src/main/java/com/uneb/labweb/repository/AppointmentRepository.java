package com.uneb.labweb.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.dto.response.PartialAppointmentDTO;
import com.uneb.labweb.model.Appointment;
import com.uneb.labweb.model.Doctor;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Consulta para obter dados parciais de uma consulta com base no seu ID
    @Query("SELECT new com.uneb.labweb.dto.response.PartialAppointmentDTO(u1.id, hc.id, s.id, u2.name, s.name, hc.name, hc.address) "
        + "FROM Appointment a "
        + "LEFT JOIN a.doctor d "
        + "LEFT JOIN a.healthCenter hc "
        + "LEFT JOIN a.specialty s "
        + "LEFT JOIN a.user u1 "
        + "LEFT JOIN d.user u2 "
        + "WHERE a.id = :appointmentId")
    PartialAppointmentDTO getAppointmentData(@Param("appointmentId") Long appointmentId);

    // Consulta para encontrar todas as consultas de uma especialidade e posto de saúde específicos
    @Query("SELECT a "
        + "FROM Appointment a "
        + "WHERE a.specialty.id = :specialtyId "
        + "AND a.healthCenter.id = :healthCenterId")
    List<Appointment> findBySpecialtyAndHealthCenter(@Param("specialtyId") Long specialtyId, @Param("healthCenterId") Long healthCenterId);

    // Consulta para encontrar todas as consultas de um usuário específico
    @Query("SELECT a "
        + "FROM Appointment a "
        + "WHERE a.user.id = :userId")
    List<Appointment> findByUserId(@Param("userId") Long userId);

    // Consulta para encontrar todas as consultas de um médico específico
    @Query("SELECT a "
       + "FROM Appointment a "
       + "JOIN a.doctor d " 
       + "WHERE d.id = :doctorId")
    List<Appointment> findByDoctorId(@Param("doctorId") Long doctorId);

    // Consulta para encontrar datas distintas de consultas de uma especialidade e posto de saúde específicos
    @Query("""
        SELECT DISTINCT CAST(a.appointmentDateTime AS date)
        FROM Appointment a
        WHERE a.specialty.id = :specialtyId AND a.healthCenter.id = :healthCenterId
        ORDER BY CAST(a.appointmentDateTime AS date)
    """)
    List<java.sql.Date> findDistinctDates(@Param("specialtyId") Long specialtyId, @Param("healthCenterId") Long healthCenterId);

    // Consulta para encontrar médicos disponíveis para consultas em uma data específica, especialidade e posto de saúde
    @Query("""
        SELECT DISTINCT d
        FROM Appointment a
        JOIN a.doctor d
        WHERE CAST(a.appointmentDateTime AS date) = :date
        AND a.specialty.id = :specialtyId
        AND a.healthCenter.id = :healthCenterId
    """)
    List<Doctor> findDoctorsByDate(@Param("date") LocalDate date, @Param("specialtyId") Long specialtyId, @Param("healthCenterId") Long healthCenterId);

    // Consulta para encontrar consultas para um médico específico em uma data, especialidade e posto de saúde específicos
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

    // Consulta para encontrar todos os anos distintos de consultas registradas
    @Query(value = "SELECT DISTINCT EXTRACT(YEAR FROM a.APPOINTMENT_DATE_TIME) FROM APPOINTMENT a ORDER BY EXTRACT(YEAR FROM a.APPOINTMENT_DATE_TIME)", nativeQuery = true)
    List<Integer> findDistinctYears();

    // Consulta para contar consultas agendadas por mês de um determinado ano
    @Query(value = "SELECT CAST(EXTRACT(MONTH FROM a.appointment_date_time) AS INTEGER), CAST(COUNT(*) AS INTEGER) " +
               "FROM appointment a " +
               "WHERE EXTRACT(YEAR FROM a.appointment_date_time) = :year " +
               "AND (a.appointment_status = 'scheduled' " +
               "OR a.appointment_status = 'attended' " +
               "OR a.appointment_status = 'missed') " +
               "GROUP BY CAST(EXTRACT(MONTH FROM a.appointment_date_time) AS INTEGER)", nativeQuery = true)
    List<Object[]> countScheduledAppointmentsMonth(@Param("year") int year);

    // Consulta para contar consultas por status e mês de um determinado ano
    @Query(value = "SELECT CAST(EXTRACT(MONTH FROM a.appointment_date_time) AS INTEGER), CAST(COUNT(*) AS INTEGER) " +
               "FROM appointment a " +
               "WHERE EXTRACT(YEAR FROM a.appointment_date_time) = :year " +
               "AND a.appointment_status = :status " +
               "GROUP BY CAST(EXTRACT(MONTH FROM a.appointment_date_time) AS INTEGER)", nativeQuery = true)
    List<Object[]> countAppointmentsByStatusAndMonth(@Param("year") int year, @Param("status") String status);

    // Consulta para contar o número de cancelamentos de consultas por mês de um determinado ano
    @Query(value = "SELECT CAST(EXTRACT(MONTH FROM a.appointment_date_time) AS INTEGER), CAST(SUM(a.cancellation_count) AS INTEGER) " +
               "FROM appointment a " +
               "WHERE EXTRACT(YEAR FROM a.appointment_date_time) = :year " +
               "GROUP BY CAST(EXTRACT(MONTH FROM a.appointment_date_time) AS INTEGER)", nativeQuery = true)
    List<Object[]> countCancelledAppointmentsByMonth(@Param("year") int year);

}
