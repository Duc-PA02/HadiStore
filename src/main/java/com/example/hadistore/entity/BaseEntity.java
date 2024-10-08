package com.example.hadistore.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
