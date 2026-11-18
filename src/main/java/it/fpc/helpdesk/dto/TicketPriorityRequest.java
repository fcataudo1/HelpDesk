package it.fpc.helpdesk.dto;

import it.fpc.helpdesk.enums.Priority;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TicketPriorityRequest {

    @NotNull(message = "Priority is required.")
    private Priority priority;
}

