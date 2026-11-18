package it.fpc.helpdesk.controller;

import it.fpc.helpdesk.dto.*;
import it.fpc.helpdesk.enums.Priority;
import it.fpc.helpdesk.enums.Status;
import it.fpc.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Tag(name = "Tickets", description = "API for ticket management")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @Operation(summary = "Create a new ticket")
    public ResponseEntity<TicketResponse> createTicket(
            @Valid @RequestBody TicketRequest request) {

        TicketResponse response = ticketService.createTicket(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @Operation(summary = "Get all tickets")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {

        List<TicketResponse> tickets = ticketService.getAllTickets();

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a ticket by ID")
    public ResponseEntity<TicketResponse> getTicketById(
            @PathVariable Long id) {

        TicketResponse response = ticketService.getTicketById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/priority/{priority}")
    @Operation(summary = "Get tickets by priority")
    public ResponseEntity<List<TicketResponse>> getTicketsByPriority(
            @PathVariable Priority priority) {

        List<TicketResponse> tickets =
                ticketService.getTicketsByPriority(priority);

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get tickets by status")
    public ResponseEntity<List<TicketResponse>> getTicketsByStatus(
            @PathVariable Status status) {

        List<TicketResponse> tickets =
                ticketService.getTicketsByStatus(status);

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/priority/{priority}/status/{status}")
    @Operation(summary = "Get tickets by priority and status")
    public ResponseEntity<List<TicketResponse>> getTicketsByPriorityAndStatus(
            @PathVariable Priority priority,
            @PathVariable Status status) {

        List<TicketResponse> tickets =
                ticketService.getTicketsByPriorityAndStatus(priority, status);

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/technician/{technicianId}")
    @Operation(summary = "Get tickets assigned to a technician")
    public ResponseEntity<List<TicketResponse>> getTicketsByTechnician(
            @PathVariable Long technicianId) {

        List<TicketResponse> tickets =
                ticketService.getTicketsByTechnician(technicianId);

        return ResponseEntity.ok(tickets);
    }


    @PatchMapping("/{id}/assignment")
    @Operation(summary = "Assign a technician to a ticket")
    public ResponseEntity<TicketResponse> assignTechnician(
            @PathVariable Long id,
            @Valid @RequestBody TicketAssignmentRequest request) {

        TicketResponse response =
                ticketService.assignTechnician(id, request);

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{id}/status")
    @Operation(summary = "Update ticket status")
    public ResponseEntity<TicketResponse> updateTicketStatus(
            @PathVariable Long id,
            @Valid @RequestBody TicketStatusRequest request) {

        TicketResponse response =
                ticketService.updateTicketStatus(id, request);

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{id}/priority")
    @Operation(summary = "Update ticket priority")
    public ResponseEntity<TicketResponse> updateTicketPriority(
            @PathVariable Long id,
            @Valid @RequestBody TicketPriorityRequest request) {

        TicketResponse response =
                ticketService.updateTicketPriority(id, request);

        return ResponseEntity.ok(response);
    }


}
