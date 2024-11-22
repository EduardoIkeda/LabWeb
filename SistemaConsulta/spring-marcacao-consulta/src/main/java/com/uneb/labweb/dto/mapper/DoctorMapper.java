package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.DoctorDTO;
import com.uneb.labweb.model.Doctor;

@Component
public class DoctorMapper {
    public DoctorDTO toDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        return new DoctorDTO(doctor.getId(), doctor.getCrm());
    }

    public Doctor toEntity(DoctorDTO doctorDTO) {
        if (doctorDTO == null) {
            return null;
        }

        Doctor doctor = new Doctor();

        if (doctorDTO.id() != null) {
            doctor.setId(doctorDTO.id());
        }
        doctor.setCrm(doctorDTO.crm());
        
        return doctor;
    }
}
