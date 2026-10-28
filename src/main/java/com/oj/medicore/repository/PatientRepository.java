package com.oj.medicore.repository;

import com.oj.medicore.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    boolean existsByNameIgnoreCaseAndDateOfBirth(
            String name,
            LocalDate dateOfBirth
    );

    boolean existsByNameIgnoreCaseAndDateOfBirthAndIdNot(
            String name,
            LocalDate dateOfBirth,
            Long id
    );

    Page<Patient> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
