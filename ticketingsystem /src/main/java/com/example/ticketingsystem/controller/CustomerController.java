package com.example.ticketingsystem.controller;

import com.example.ticketingsystem.model.Customer;
import com.example.ticketingsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:3000")  // Enable CORS for React frontend (if needed)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer customer) {
        // Find customer by email
        Customer foundCustomer = customerService.getCustomerByEmail(customer.getEmail());

        if (foundCustomer == null) {
            // Handle customer not found
            return new ResponseEntity<>("Customer not found with email: " + customer.getEmail(), HttpStatus.NOT_FOUND);
        }

        // Return found customer
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Customer customer) {
        // Check if the email is already registered
        Customer existingCustomer = customerService.getCustomerByEmail(customer.getEmail());

        if (existingCustomer != null) {
            // Return error response with status 400
            return new ResponseEntity<>("Email is already registered: " + customer.getEmail(), HttpStatus.BAD_REQUEST);
        }

        // Save the new customer
        try {
            Customer savedCustomer = customerService.saveCustomer(customer);
            // Return the saved customer
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle any unexpected errors (e.g., database issues)
            return new ResponseEntity<>("Error occurred while creating customer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}