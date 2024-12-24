package com.example.ticketingsystem.service;

import com.example.ticketingsystem.model.Customer;
import com.example.ticketingsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Get customer by email
    public Customer getCustomerByEmail(String email) {
        // Return only the first result if there are duplicates
        List<Customer> customers = customerRepository.findByEmail(email);

        if (customers.size() > 1) {
            // Log the warning or throw an exception based on your requirement
            System.out.println("Warning: Multiple customers found with email: " + email);
        }

        // Return the first customer, or null if not found
        return customers.isEmpty() ? null : customers.get(0);
    }

    // Save a new customer to the database
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
