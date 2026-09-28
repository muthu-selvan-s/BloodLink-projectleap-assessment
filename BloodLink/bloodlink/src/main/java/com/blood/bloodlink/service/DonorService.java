package com.blood.bloodlink.service;

import com.blood.bloodlink.entity.Donor;
import com.blood.bloodlink.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    // Register a new donor
    public Donor registerDonor(Donor donor) {

        donor.setAvailable(true);

        return donorRepository.save(donor);
    }

    // Get all donors
    public List<Donor> getAllDonors() {

        return donorRepository.findAll();
    }

    // Get donor by ID
    public Donor getDonorById(Long id) {

        return donorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Donor not found with ID: " + id
                        )
                );
    }

    // Delete donor
    public void deleteDonor(Long id) {

        if (!donorRepository.existsById(id)) {

            throw new RuntimeException(
                    "Donor not found with ID: " + id
            );
        }

        donorRepository.deleteById(id);
    }

    // Search donor by blood group and city
    public List<Donor> searchDonors(
            String bloodGroup,
            String city) {

        List<Donor> donors =
                donorRepository
                        .findByBloodGroup_BloodGroupIgnoreCaseAndCityIgnoreCase(
                                bloodGroup,
                                city
                        );

        return donors.stream()
                .filter(this::isCurrentlyAvailable)
                .toList();
    }

    // Check 90-day cooldown
   private boolean isCurrentlyAvailable(Donor donor) {

    if (donor.getLastDonationDate() == null) {
        return true;
    }

    LocalDate availableDate =
            donor.getLastDonationDate().plusDays(90);

    boolean available =
            !LocalDate.now().isBefore(availableDate);

    // Automatically update database status
    if (available && !donor.isAvailable()) {
        donor.setAvailable(true);
        donorRepository.save(donor);
    }

    return available;
}
    }
