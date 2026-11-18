package it.fpc.helpdesk.repository;

import it.fpc.helpdesk.entity.Ticket;
import it.fpc.helpdesk.enums.Priority;
import it.fpc.helpdesk.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByPriority(Priority priority);

    List<Ticket> findByStatus(Status status);

    List<Ticket> findByPriorityAndStatus(Priority priority, Status status);

    List<Ticket> findByTechnicianId(Long technicianId);
}
