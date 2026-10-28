package com.oj.medicore.service;
import com.oj.medicore.dto.PatientRequest;
import com.oj.medicore.dto.PatientResponse;
import com.oj.medicore.entity.Patient;
import com.oj.medicore.enums.PatientStatus;
import com.oj.medicore.exception.PatientNotFoundException;
import com.oj.medicore.mapper.PatientMapper;
import com.oj.medicore.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class PatientService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "name",
            "mrn",
            "createdAt"
    );

    private final PatientRepository patientRepository;
    private final MrnGeneratorService mrnGeneratorService;
    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository,
                          MrnGeneratorService mrnGeneratorService,
                          PatientMapper patientMapper){
        this.patientRepository=patientRepository;
        this.mrnGeneratorService=mrnGeneratorService;
        this.patientMapper=patientMapper;
    }


//    Register Patient
    @Transactional
    public PatientResponse registerPatient(PatientRequest request){
        Patient patient = patientMapper.toEntity(request);

        String normalizedName = request.getName().trim().replaceAll("\\s+", " ");
        patient.setName(normalizedName);
        if (patientRepository.existsByNameIgnoreCaseAndDateOfBirth(
                normalizedName,
                request.getDateOfBirth())){
            throw new IllegalArgumentException("A patient with this name and date of birth already exists");
        }

        patient.setMrn(mrnGeneratorService.generateMrn());
        patient.setStatus(PatientStatus.ACTIVE);

        Patient savedPatient = patientRepository.save(patient);

        return patientMapper.toResponse(savedPatient);
    }



//  Get all Patients
    public Page<PatientResponse> getAllPatients(int page, int size, String direction, String sortBy) {

        if (page < 0){
            throw new IllegalArgumentException("Page Number Cannot be Negative");
        }

        if(size < 1 || size >100){
            throw new IllegalArgumentException("Page size must be between 1 and 100");
        }

        if(!ALLOWED_SORT_FIELDS.contains(sortBy)){
            throw new IllegalArgumentException("Invalid Sort Field "+sortBy);
        }

        if(!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")){
            throw new IllegalArgumentException("Invalid Direction");
        }

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Patient> patients = patientRepository.findAll(pageable);
        return patients.map(patientMapper::toResponse);
    }


//    get Patient By Id
    public PatientResponse getPatientById(Long id){
        Patient patient= patientRepository.findById(id)
                .orElseThrow(
                        ()->new PatientNotFoundException("Patient Not Found with id :- "+id));

        return patientMapper.toResponse(patient);
    }


//    Update Patient By Id
    @Transactional
    public PatientResponse updatePatientById(PatientRequest request, Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(
                        ()->new PatientNotFoundException("Patient Not Found with id :- "+id));

        String normalizedName = request.getName().trim().replaceAll("\\s+"," ");

        if (patientRepository.existsByNameIgnoreCaseAndDateOfBirthAndIdNot(
                normalizedName,
                request.getDateOfBirth(),
                id)){
            throw new IllegalArgumentException("Another patient with this name and date of birth already exists");
        }

        patientMapper.updateEntity(request, patient);
        patient.setName(normalizedName);
        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.toResponse(updatedPatient);
    }


    public Page<PatientResponse> searchPatientsByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Patient> patients = patientRepository.findByNameContainingIgnoreCase(name, pageable);
        return patients.map(patientMapper::toResponse);
    }
}
