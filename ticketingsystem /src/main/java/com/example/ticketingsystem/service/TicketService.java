package com.example.ticketingsystem.service;

import com.example.ticketingsystem.model.Ticket;
import com.example.ticketingsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;


    // Method to get available tickets (status = "Available")
    public List<Ticket> getAvailableTickets() {
        return ticketRepository.findByStatus("Available");
    }


    // Other methods like purchaseTicket, cancelTicket, etc.

    // Method to purchase a ticket and change its status to "Booked"
    public Ticket purchaseTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus("Booked");
        return ticketRepository.save(ticket);
    }

    public Ticket cancelTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Ensure the ticket is actually "Booked" before canceling it
        if (!"Booked".equals(ticket.getStatus())) {
            throw new RuntimeException("Ticket is not booked, cannot cancel");
        }

        ticket.setStatus("Available"); // Set the ticket back to "Available"
        return ticketRepository.save(ticket);
    }


    // Method to create a new ticket
    public Ticket createTicket(Double price, String event, Integer totalTickets,
                               Integer releaseRate, Integer customerRetrievalRate, Integer maxCapacity) {
        Ticket ticket = new Ticket();
        ticket.setPrice(price);
        ticket.setEvent(event);
        ticket.setTotalTickets(totalTickets);
        ticket.setReleaseRate(releaseRate);
        ticket.setCustomerRetrievalRate(customerRetrievalRate);
        ticket.setMaxCapacity(maxCapacity);
        ticket.setStatus("Available");  // New tickets are available by default
        return ticketRepository.save(ticket);
    }

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public Ticket bookTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!"Available".equals(ticket.getStatus())) {
            throw new RuntimeException("Ticket is not available for booking");
        }

        ticket.setStatus("Booked");
        return ticketRepository.save(ticket);
    }

}