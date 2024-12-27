package com.uneb.labweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.dto.response.SpecialtyCountDTO;
import com.uneb.labweb.model.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    // Consulta para encontrar especialidades associadas a um posto de saúde específico
    @Query("SELECT s FROM Specialty s JOIN s.healthCenters hc WHERE hc.id = :healthCenterId")
    List<Specialty> findByHealthCenterId(@Param("healthCenterId") Long healthCenterId);

    // Consulta para encontrar especialidades associadas a um médico específico
    @Query("SELECT s FROM Specialty s JOIN s.doctors d WHERE d.id = :doctorId")
    List<Specialty> findByDoctorId(@Param("doctorId") Long doctorId);

    // Consulta para encontrar especialidades com o número de consultas realizadas em um ano específico
    @Query("SELECT new com.uneb.labweb.dto.response.SpecialtyCountDTO(s.name AS specialtyName, COUNT(a) AS appointmentsCount) " +
       "FROM Specialty s " +
       "JOIN s.appointments a " +
       "WHERE EXTRACT(YEAR FROM a.appointmentDateTime) = :year " +
       "GROUP BY s.id, s.name")
    List<SpecialtyCountDTO> findSpecialtiesWithAppointmentsCountByYear(@Param("year") Long year);
}
