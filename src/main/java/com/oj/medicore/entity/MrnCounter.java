package com.oj.medicore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mrn_counters")
public class MrnCounter {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long nextValue;

    public MrnCounter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNextValue() {
        return nextValue;
    }

    public void setNextValue(Long nextValue) {
        this.nextValue = nextValue;
    }
}