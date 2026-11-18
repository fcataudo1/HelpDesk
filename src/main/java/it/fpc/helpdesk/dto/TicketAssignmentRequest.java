package it.fpc.helpdesk.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TicketAssignmentRequest {

    @NotNull(message = "Technician ID is required.")
    private Long technicianId;
}
