package com.oj.medicore.repository;

import com.oj.medicore.entity.MrnCounter;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MrnCounterRepository extends JpaRepository<MrnCounter,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT counter FROM MrnCounter counter WHERE counter.id = :id")
    Optional<MrnCounter> findByIdForUpdate(@Param("id") Long id);
}
