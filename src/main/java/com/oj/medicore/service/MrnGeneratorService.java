package com.oj.medicore.service;

import com.oj.medicore.entity.MrnCounter;
import com.oj.medicore.repository.MrnCounterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class MrnGeneratorService {

    private final MrnCounterRepository mrnCounterRepository;

    public MrnGeneratorService(MrnCounterRepository mrnCounterRepository) {
        this.mrnCounterRepository=mrnCounterRepository;
    }

    @Transactional
    public String generateMrn(){
        MrnCounter counter = mrnCounterRepository
                .findByIdForUpdate(1L)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "MRN counter row is not initialized"
                        )
                );

        Long currentValue = counter.getNextValue();

        counter.setNextValue(currentValue+1);
        mrnCounterRepository.save(counter);
        return String.format("MED-%06d", currentValue);
    }
}
