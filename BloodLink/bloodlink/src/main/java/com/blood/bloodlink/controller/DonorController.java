package com.blood.bloodlink.controller;

import com.blood.bloodlink.entity.Donor;
import com.blood.bloodlink.service.DonorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    
    @PostMapping
    public ResponseEntity<Donor> registerDonor(
            @Valid @RequestBody Donor donor) {

        Donor savedDonor = donorService.registerDonor(donor);

        return new ResponseEntity<>(
                savedDonor,
                HttpStatus.CREATED
        );
    }

    
    @GetMapping
    public ResponseEntity<List<Donor>> getAllDonors() {

        return ResponseEntity.ok(
                donorService.getAllDonors()
        );
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                donorService.getDonorById(id)
        );
    }

    
    @GetMapping("/search")
    public ResponseEntity<List<Donor>> searchDonors(
            @RequestParam String bloodGroup,
            @RequestParam String city) {

        return ResponseEntity.ok(
                donorService.searchDonors(
                        bloodGroup,
                        city
                )
        );
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDonor(
            @PathVariable Long id) {

        donorService.deleteDonor(id);

        return ResponseEntity.ok(
                "Donor deleted successfully"
        );
    }
}