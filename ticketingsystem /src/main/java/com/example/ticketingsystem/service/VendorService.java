// VendorService.java
package com.example.ticketingsystem.service;

import com.example.ticketingsystem.model.Vendor;
import com.example.ticketingsystem.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor getVendorByEmail(String email) {
        List<Vendor> vendors = vendorRepository.findByEmail(email);

        if (vendors.size() > 1) {
            System.out.println("Warning: Multiple vendors found with email: " + email);
        }

        return vendors.isEmpty() ? null : vendors.get(0);
    }

    public Vendor createVendor(String email, String password) {
        Vendor vendor = new Vendor();
        vendor.setEmail(email);
        vendor.setPassword(password);  // Ideally, password should be hashed
        return vendorRepository.save(vendor);
    }
}
