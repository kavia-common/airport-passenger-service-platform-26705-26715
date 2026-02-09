package com.example.flightstatusservice.baggage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * JPA entity mapping for `baggage_belts` table (created in Flyway V3).
 */
@Entity
@Table(name = "baggage_belts")
public class BaggageBeltEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "belt_code", nullable = false, length = 20)
    private String beltCode;

    @Column(name = "terminal", length = 20)
    private String terminal;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected BaggageBeltEntity() {
        // for JPA
    }

    public UUID getId() {
        return id;
    }

    public String getBeltCode() {
        return beltCode;
    }

    public String getTerminal() {
        return terminal;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setBeltCode(String beltCode) {
        this.beltCode = beltCode;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
