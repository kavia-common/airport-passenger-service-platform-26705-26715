package com.example.flightstatusservice.gates;

import com.example.flightstatusservice.api.ConflictException;
import com.example.flightstatusservice.api.NotFoundException;
import com.example.flightstatusservice.gates.dto.GateCreateRequest;
import com.example.flightstatusservice.gates.dto.GateResponse;
import com.example.flightstatusservice.gates.dto.GateUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gates")
@Tag(name = "Gates", description = "CRUD endpoints for airport gates")
public class GateController {

    private final GateRepository gateRepository;

    public GateController(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create gate", description = "Creates a gate. Enforces unique gateCode.")
    public GateResponse create(@Valid @RequestBody GateCreateRequest request) {
        gateRepository.findByGateCode(request.gateCode()).ifPresent(existing -> {
            throw new ConflictException("Gate already exists for gateCode");
        });

        GateEntity e = new GateEntity();
        e.setId(UUID.randomUUID());
        e.setGateCode(request.gateCode());
        e.setTerminal(request.terminal());
        e.setActive(request.active());

        GateEntity saved = gateRepository.save(e);
        return toResponse(saved);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(summary = "Get gate by id", description = "Returns a gate by UUID.")
    public GateResponse getById(@PathVariable UUID id) {
        GateEntity e = gateRepository.findById(id).orElseThrow(() -> new NotFoundException("Gate not found"));
        return toResponse(e);
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List gates", description = "Lists all gates.")
    public List<GateResponse> list() {
        return gateRepository.findAll().stream().map(GateController::toResponse).toList();
    }

    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(summary = "Update gate", description = "Full update of a gate.")
    public GateResponse update(@PathVariable UUID id, @Valid @RequestBody GateUpdateRequest request) {
        GateEntity e = gateRepository.findById(id).orElseThrow(() -> new NotFoundException("Gate not found"));

        gateRepository.findByGateCode(request.gateCode()).ifPresent(other -> {
            if (!other.getId().equals(id)) {
                throw new ConflictException("Another gate exists with the same gateCode");
            }
        });

        e.setGateCode(request.gateCode());
        e.setTerminal(request.terminal());
        e.setActive(request.active());

        GateEntity saved = gateRepository.save(e);
        return toResponse(saved);
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete gate", description = "Deletes a gate by UUID.")
    public void delete(@PathVariable UUID id) {
        if (!gateRepository.existsById(id)) {
            throw new NotFoundException("Gate not found");
        }
        gateRepository.deleteById(id);
    }

    private static GateResponse toResponse(GateEntity e) {
        return new GateResponse(e.getId(), e.getGateCode(), e.getTerminal(), e.isActive(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
