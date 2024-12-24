package com.example.ticketingsystem.repository;

import com.example.ticketingsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Custom query to find tickets by their status
    List<Ticket> findByStatus(String status);
    Optional<Ticket> findById(Long id);

    @GetMapping("/booked")
    public default List<Ticket> getBookedTickets() {
        TicketRepository ticketService = null;
        return ticketService.getBookedTickets(); // Directly fetch booked tickets
    }
}