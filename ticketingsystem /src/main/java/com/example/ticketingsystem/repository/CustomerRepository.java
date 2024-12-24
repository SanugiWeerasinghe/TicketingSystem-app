package com.example.ticketingsystem.repository;

import com.example.ticketingsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Return a List instead of a single entity
    List<Customer> findByEmail(String email);
}
