package it.fpc.helpdesk.service;


import it.fpc.helpdesk.dto.*;
import it.fpc.helpdesk.entity.Technician;
import it.fpc.helpdesk.entity.Ticket;
import it.fpc.helpdesk.enums.Priority;
import it.fpc.helpdesk.enums.Status;
import it.fpc.helpdesk.exception.InvalidTicketStatusException;
import it.fpc.helpdesk.exception.ResourceNotFoundException;
import it.fpc.helpdesk.mapper.TicketMapper;
import it.fpc.helpdesk.repository.TechnicianRepository;
import it.fpc.helpdesk.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final TechnicianRepository technicianRepository;

    @Transactional
    public TicketResponse createTicket(TicketRequest request) {

        Ticket ticket = new Ticket();

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(Priority.MEDIUM);
        ticket.setStatus(Status.OPEN);

        Ticket savedTicket = ticketRepository.save(ticket);

        return ticketMapper.toResponse(savedTicket);
    }

    public List<TicketResponse> getAllTickets() {

        List<Ticket> tickets = ticketRepository.findAll();

        return ticketMapper.toResponseList(tickets);
    }

    public TicketResponse getTicketById(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ticket not found with id: " + id
                ));

        return ticketMapper.toResponse(ticket);
    }

    public List<TicketResponse> getTicketsByPriority(Priority priority) {

        List<Ticket> tickets = ticketRepository.findByPriority(priority);

        return ticketMapper.toResponseList(tickets);
    }

    public List<TicketResponse> getTicketsByStatus(Status status) {

        List<Ticket> tickets = ticketRepository.findByStatus(status);

        return ticketMapper.toResponseList(tickets);
    }

    public List<TicketResponse> getTicketsByPriorityAndStatus(
            Priority priority, Status status) {

        List<Ticket> tickets =
                ticketRepository.findByPriorityAndStatus(priority, status);

        return ticketMapper.toResponseList(tickets);
    }

    public List<TicketResponse> getTicketsByTechnician(Long technicianId) {

        if (!technicianRepository.existsById(technicianId)) {
            throw new ResourceNotFoundException(
                    "Technician not found with id: " + technicianId
            );
        }

        List<Ticket> tickets =
                ticketRepository.findByTechnicianId(technicianId);

        return ticketMapper.toResponseList(tickets);
    }


    @Transactional
    public TicketResponse assignTechnician(
            Long id, TicketAssignmentRequest request) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ticket not found with id: " + id
                ));

        Technician technician = technicianRepository.findById(
                request.getTechnicianId()
        ).orElseThrow(() -> new ResourceNotFoundException(
                "Technician not found with id: " + request.getTechnicianId()
        ));

        ticket.setTechnician(technician);

        Ticket updatedTicket = ticketRepository.save(ticket);

        return ticketMapper.toResponse(updatedTicket);
    }



    @Transactional
    public TicketResponse updateTicketStatus(
            Long id, TicketStatusRequest request) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ticket not found with id: " + id
                ));

        Status currentStatus = ticket.getStatus();
        Status newStatus = request.getStatus();

        if (currentStatus != newStatus) {

            if (currentStatus == Status.OPEN &&
                    newStatus != Status.IN_PROGRESS) {

                throw new InvalidTicketStatusException(
                        "An OPEN ticket can only move to IN_PROGRESS."
                );

            } else if (currentStatus == Status.IN_PROGRESS &&
                    newStatus != Status.CLOSED) {

                throw new InvalidTicketStatusException(
                        "An IN_PROGRESS ticket can only move to CLOSED."
                );

            } else if (currentStatus == Status.CLOSED) {

                throw new InvalidTicketStatusException(
                        "A CLOSED ticket cannot change its status."
                );
            }
        }

        ticket.setStatus(newStatus);

        Ticket updatedTicket = ticketRepository.save(ticket);

        return ticketMapper.toResponse(updatedTicket);
    }


    @Transactional
    public TicketResponse updateTicketPriority(
            Long id, TicketPriorityRequest request) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ticket not found with id: " + id
                ));

        ticket.setPriority(request.getPriority());

        Ticket updatedTicket = ticketRepository.save(ticket);

        return ticketMapper.toResponse(updatedTicket);
    }



}
