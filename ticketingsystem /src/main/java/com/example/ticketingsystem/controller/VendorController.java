// VendorController.java
package com.example.ticketingsystem.controller;

import com.example.ticketingsystem.model.Vendor;
import com.example.ticketingsystem.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
@CrossOrigin(origins = "http://localhost:3000")  // Enable CORS for React frontend
public class VendorController {

    @Autowired
    private VendorService vendorService;

    // Vendor Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Vendor vendor) {
        Vendor foundVendor = vendorService.getVendorByEmail(vendor.getEmail());

        if (foundVendor == null) {
            return new ResponseEntity<>("Vendor not found with email: " + vendor.getEmail(), HttpStatus.NOT_FOUND);
        }

        // You may add password verification here (e.g., hashing and matching)
        return new ResponseEntity<>(foundVendor, HttpStatus.OK);
    }

    // Vendor Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Vendor vendor) {
        // Check if vendor already exists
        Vendor existingVendor = vendorService.getVendorByEmail(vendor.getEmail());
        if (existingVendor != null) {
            return new ResponseEntity<>("Email is already registered", HttpStatus.BAD_REQUEST);
        }

        try {
            Vendor savedVendor = vendorService.createVendor(vendor.getEmail(), vendor.getPassword());
            return new ResponseEntity<>(savedVendor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred during signup: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
