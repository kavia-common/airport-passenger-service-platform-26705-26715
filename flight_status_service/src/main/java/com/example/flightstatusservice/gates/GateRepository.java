package com.example.flightstatusservice.gates;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GateRepository extends JpaRepository<GateEntity, UUID> {

    Optional<GateEntity> findByGateCode(String gateCode);
}
