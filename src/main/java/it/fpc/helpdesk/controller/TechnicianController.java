package it.fpc.helpdesk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.fpc.helpdesk.dto.TechnicianRequest;
import it.fpc.helpdesk.dto.TechnicianResponse;
import it.fpc.helpdesk.service.TechnicianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technicians")
@RequiredArgsConstructor
@Tag(name = "Technicians", description = "API for technician management")
public class TechnicianController {

    private final TechnicianService technicianService;

    @PostMapping
    @Operation(summary = "Create a new technician")
    public ResponseEntity<TechnicianResponse> createTechnician(
            @Valid @RequestBody TechnicianRequest request) {

        TechnicianResponse savedTechnician =
                technicianService.createTechnician(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedTechnician);
    }

    @GetMapping
    @Operation(summary = "Get all technicians")
    public ResponseEntity<List<TechnicianResponse>> getAllTechnicians() {

        List<TechnicianResponse> technicians =
                technicianService.getAllTechnicians();

        return ResponseEntity.ok(technicians);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a technician by ID")
    public ResponseEntity<TechnicianResponse> getTechnicianById(
            @PathVariable Long id) {

        TechnicianResponse technician =
                technicianService.getTechnicianById(id);

        return ResponseEntity.ok(technician);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a technician")
    public ResponseEntity<TechnicianResponse> updateTechnician(
            @PathVariable Long id,
            @Valid @RequestBody TechnicianRequest request) {

        TechnicianResponse updatedTechnician =
                technicianService.updateTechnician(id, request);

        return ResponseEntity.ok(updatedTechnician);
    }
}
