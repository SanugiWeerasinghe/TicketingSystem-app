package com.example.ticketingsystem.controller;

import com.example.ticketingsystem.model.Ticket;
import com.example.ticketingsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ticketingsystem.dto.EventDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests only from this origin
@RequestMapping("/api/ticket")
public class TicketController {


    @Autowired
    private TicketService ticketService;

    @GetMapping("/available")
    public List<EventDTO> getAvailableTickets() {
        List<Ticket> tickets = ticketService.getAvailableTickets();
        return tickets.stream()
                .collect(Collectors.groupingBy(Ticket::getEvent))
                .entrySet().stream()
                .map(entry -> {
                    String event = entry.getKey();
                    long count = entry.getValue().stream()
                            .filter(ticket -> "Available".equals(ticket.getStatus()))
                            .count();
                    double price = entry.getValue().get(0).getPrice();
                    Long id = entry.getValue().get(0).getId(); // Ensure ID is included
                    return new EventDTO(id, event, price, (int) count);
                })
                .collect(Collectors.toList());
    }


    // PUT endpoint to purchase a ticket
    @PutMapping("/purchase/{id}")
    public Ticket purchaseTicket(@PathVariable Long id) {
        return ticketService.purchaseTicket(id);  // Calls service method to purchase the ticket
    }

    @PostMapping("/create")
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket.getPrice(), ticket.getEvent(), ticket.getTotalTickets(),
                ticket.getReleaseRate(), ticket.getCustomerRetrievalRate(), ticket.getMaxCapacity());
    }

    @PutMapping("/cancel/{id}")
    public Ticket cancelTicket(@PathVariable Long id) {
        return ticketService.cancelTicket(id);
    }

}





