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

    @Query("SELECT s FROM Specialty s JOIN s.healthCenters hc WHERE hc.id = :healthCenterId")
    List<Specialty> findByHealthCenterId(@Param("healthCenterId") Long healthCenterId);

    @Query("SELECT new com.uneb.labweb.dto.response.SpecialtyCountDTO(s.name AS specialtyName, COUNT(a) AS appointmentsCount) " +
       "FROM Specialty s " +
       "JOIN s.appointments a " +
       "WHERE EXTRACT(YEAR FROM a.appointmentDateTime) = :year " +
       "GROUP BY s.id, s.name")
    List<SpecialtyCountDTO> findSpecialtiesWithAppointmentsCountByYear(@Param("year") Long year);
}
