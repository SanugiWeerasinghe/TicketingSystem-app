package com.example.ticketingsystem.dto;

public class EventDTO {

    private Long id;
    private String event;
    private double price;
    private int availableTickets;

    // Constructor expecting Long, String, double, and int
    public EventDTO(Long id, String event, double price, int availableTickets) {
        this.id = id;
        this.event = event;
        this.price = price;
        this.availableTickets = availableTickets;
    }

    // Getters and Setters
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
}
