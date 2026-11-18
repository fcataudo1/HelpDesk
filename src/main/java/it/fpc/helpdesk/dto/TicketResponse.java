package it.fpc.helpdesk.dto;

import it.fpc.helpdesk.enums.Priority;
import it.fpc.helpdesk.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TicketResponse {

    private Long id;

    private String title;

    private String description;

    private Priority priority;

    private Status status;

    private LocalDateTime createdAt;

    private TechnicianResponse technician;
}
