package it.fpc.helpdesk.mapper;

import it.fpc.helpdesk.dto.TechnicianResponse;
import it.fpc.helpdesk.dto.TicketResponse;
import it.fpc.helpdesk.entity.Technician;
import it.fpc.helpdesk.entity.Ticket;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TicketMapper {

    public TicketResponse toResponse(Ticket ticket) {

        TechnicianResponse technicianResponse = null;

        if (ticket.getTechnician() != null) {

            Technician technician = ticket.getTechnician();

            technicianResponse = new TechnicianResponse(
                    technician.getId(),
                    technician.getFirstName(),
                    technician.getLastName(),
                    technician.getEmail(),
                    technician.getDepartment()
            );
        }

        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getPriority(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                technicianResponse
        );
    }

    public List<TicketResponse> toResponseList(List<Ticket> tickets) {

        List<TicketResponse> response = new ArrayList<>();

        for (Ticket ticket : tickets) {
            response.add(toResponse(ticket));
        }

        return response;
    }
}