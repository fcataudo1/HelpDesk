package it.fpc.helpdesk.dto;

import it.fpc.helpdesk.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TicketStatusRequest {

    @NotNull(message = "Status is required.")
    private Status status;
}
