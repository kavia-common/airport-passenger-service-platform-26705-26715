package com.example.flightstatusservice.baggage;

import com.example.flightstatusservice.api.ConflictException;
import com.example.flightstatusservice.api.NotFoundException;
import com.example.flightstatusservice.baggage.dto.BaggageBeltCreateRequest;
import com.example.flightstatusservice.baggage.dto.BaggageBeltResponse;
import com.example.flightstatusservice.baggage.dto.BaggageBeltUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/baggage-belts")
@Tag(name = "Baggage Belts", description = "CRUD endpoints for baggage belts")
public class BaggageBeltController {

    private final BaggageBeltRepository baggageBeltRepository;

    public BaggageBeltController(BaggageBeltRepository baggageBeltRepository) {
        this.baggageBeltRepository = baggageBeltRepository;
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create baggage belt", description = "Creates a baggage belt. Enforces unique beltCode.")
    public BaggageBeltResponse create(@Valid @RequestBody BaggageBeltCreateRequest request) {
        baggageBeltRepository.findByBeltCode(request.beltCode()).ifPresent(existing -> {
            throw new ConflictException("Baggage belt already exists for beltCode");
        });

        BaggageBeltEntity e = new BaggageBeltEntity();
        e.setId(UUID.randomUUID());
        e.setBeltCode(request.beltCode());
        e.setTerminal(request.terminal());
        e.setActive(request.active());

        BaggageBeltEntity saved = baggageBeltRepository.save(e);
        return toResponse(saved);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(summary = "Get baggage belt by id", description = "Returns a baggage belt by UUID.")
    public BaggageBeltResponse getById(@PathVariable UUID id) {
        BaggageBeltEntity e =
                baggageBeltRepository.findById(id).orElseThrow(() -> new NotFoundException("Baggage belt not found"));
        return toResponse(e);
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List baggage belts", description = "Lists all baggage belts.")
    public List<BaggageBeltResponse> list() {
        return baggageBeltRepository.findAll().stream().map(BaggageBeltController::toResponse).toList();
    }

    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(summary = "Update baggage belt", description = "Full update of a baggage belt.")
    public BaggageBeltResponse update(@PathVariable UUID id, @Valid @RequestBody BaggageBeltUpdateRequest request) {
        BaggageBeltEntity e =
                baggageBeltRepository.findById(id).orElseThrow(() -> new NotFoundException("Baggage belt not found"));

        baggageBeltRepository.findByBeltCode(request.beltCode()).ifPresent(other -> {
            if (!other.getId().equals(id)) {
                throw new ConflictException("Another baggage belt exists with the same beltCode");
            }
        });

        e.setBeltCode(request.beltCode());
        e.setTerminal(request.terminal());
        e.setActive(request.active());

        BaggageBeltEntity saved = baggageBeltRepository.save(e);
        return toResponse(saved);
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete baggage belt", description = "Deletes a baggage belt by UUID.")
    public void delete(@PathVariable UUID id) {
        if (!baggageBeltRepository.existsById(id)) {
            throw new NotFoundException("Baggage belt not found");
        }
        baggageBeltRepository.deleteById(id);
    }

    private static BaggageBeltResponse toResponse(BaggageBeltEntity e) {
        return new BaggageBeltResponse(
                e.getId(), e.getBeltCode(), e.getTerminal(), e.isActive(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
