package com.oj.medicore.mapper;

import com.oj.medicore.dto.PatientRequest;
import com.oj.medicore.dto.PatientResponse;
import com.oj.medicore.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequest request){
        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setGender(request.getGender());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setAddress(request.getAddress());

        return patient;
    }

    public PatientResponse toResponse(Patient patient){
        PatientResponse response = new PatientResponse();

        response.setId(patient.getId());
        response.setMrn(patient.getMrn());
        response.setName(patient.getName());
        response.setPhone(patient.getPhone());
        response.setEmail(patient.getEmail());
        response.setGender(patient.getGender());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setAddress(patient.getAddress());
        response.setStatus(patient.getStatus());
        response.setCreatedAt(patient.getCreatedAt());
        response.setUpdatedAt(patient.getUpdatedAt());

        return response;
    }

    public void updateEntity(PatientRequest request, Patient patient){
        patient.setName(request.getName());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setGender(request.getGender());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setAddress(request.getAddress());
    }
}
