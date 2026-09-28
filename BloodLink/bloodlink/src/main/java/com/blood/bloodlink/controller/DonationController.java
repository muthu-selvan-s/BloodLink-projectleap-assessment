package com.blood.bloodlink.controller;

import com.blood.bloodlink.entity.DonationRecord;
import com.blood.bloodlink.service.DonationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final DonationService donationService;

    public DonationController(
            DonationService donationService) {

        this.donationService = donationService;
    }

   
    @PostMapping("/{donorId}")
    public ResponseEntity<DonationRecord> recordDonation(
            @PathVariable Long donorId,
            @RequestParam LocalDate donationDate) {

        DonationRecord record =
                donationService.recordDonation(
                        donorId,
                        donationDate
                );

        return new ResponseEntity<>(
                record,
                HttpStatus.CREATED
        );
    }

    
    @GetMapping
    public ResponseEntity<List<DonationRecord>> getAllDonations() {

        return ResponseEntity.ok(
                donationService.getAllDonations()
        );
    }

   
    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<DonationRecord>> getDonorHistory(
            @PathVariable Long donorId) {

        return ResponseEntity.ok(
                donationService.getDonorHistory(donorId)
        );
    }
}