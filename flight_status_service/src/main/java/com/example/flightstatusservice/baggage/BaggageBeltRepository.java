package com.example.flightstatusservice.baggage;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaggageBeltRepository extends JpaRepository<BaggageBeltEntity, UUID> {

    Optional<BaggageBeltEntity> findByBeltCode(String beltCode);
}
