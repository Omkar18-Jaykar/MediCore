package com.oj.medicore.controller;
import com.oj.medicore.dto.PatientRequest;
import com.oj.medicore.dto.PatientResponse;
import com.oj.medicore.entity.Patient;
import com.oj.medicore.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService=patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> registerPatient(@Valid @RequestBody PatientRequest request){
        PatientResponse savedPatient = patientService.registerPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getAllPatients(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(defaultValue = "asc") String direction,
                                                                @RequestParam(defaultValue = "id") String sortBy){
        Page<PatientResponse> patients = patientService.getAllPatients(page, size, direction, sortBy);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id){
        PatientResponse savedPatient = patientService.getPatientById(id);
        return ResponseEntity.ok(savedPatient);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatientById(@Valid @RequestBody PatientRequest request,
                                                             @PathVariable Long id){
        PatientResponse savedPatient = patientService.updatePatientById(request, id);
        return ResponseEntity.ok(savedPatient);
    }


    @GetMapping("/searchPatientsByName")
    public ResponseEntity<Page<PatientResponse>> searchPatientsByName(@RequestParam String name,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size){
        Page<PatientResponse> patients = patientService.searchPatientsByName(name, page, size);
        return ResponseEntity.ok(patients);
    }
}
