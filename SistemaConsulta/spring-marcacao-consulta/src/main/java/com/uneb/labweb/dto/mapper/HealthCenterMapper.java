package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.HealthCenterDTO;
import com.uneb.labweb.model.HealthCenter;

@Component
public class HealthCenterMapper {

    public HealthCenterDTO toDTO(HealthCenter healthCenter) {
        if (healthCenter == null) {
            return null;
        }

        return new HealthCenterDTO(healthCenter.getId(), healthCenter.getName(), healthCenter.getAddress(), healthCenter.getOperatingHours(), healthCenter.getSpecialties());
    }
    
    public HealthCenter toEntity(HealthCenterDTO healthCenterDTO) {
        if (healthCenterDTO == null) {
            return null;
        }

        HealthCenter healthCenter = new HealthCenter();

        if (healthCenterDTO.id() != null) {
            healthCenter.setId(healthCenterDTO.id());
        }
        healthCenter.setName(healthCenterDTO.name());
        healthCenter.setAddress(healthCenterDTO.address());
        healthCenter.setOperatingHours(healthCenterDTO.operatingHours());
        healthCenter.setSpecialties(healthCenterDTO.specialties());
        
        return healthCenter;
    }
}
