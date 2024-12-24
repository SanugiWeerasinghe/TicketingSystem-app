package com.example.ticketingsystem.repository;

import com.example.ticketingsystem.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    // Find vendors by email (allowing for multiple results)
    List<Vendor> findByEmail(String email);
}
