package com.blood.bloodlink.service;

import com.blood.bloodlink.entity.DonationRecord;
import com.blood.bloodlink.entity.Donor;
import com.blood.bloodlink.repository.DonationRecordRepository;
import com.blood.bloodlink.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DonationService {

    private final DonationRecordRepository donationRecordRepository;
    private final DonorRepository donorRepository;

    public DonationService(
            DonationRecordRepository donationRecordRepository,
            DonorRepository donorRepository) {

        this.donationRecordRepository = donationRecordRepository;
        this.donorRepository = donorRepository;
    }

    // Record a blood donation
    public DonationRecord recordDonation(
            Long donorId,
            LocalDate donationDate) {

        // 1. Find donor
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Donor not found with ID: " + donorId
                        )
                );

        // 2. Donation date cannot be in the future
        if (donationDate.isAfter(LocalDate.now())) {
            throw new RuntimeException(
                    "Donation date cannot be in the future"
            );
        }

        // 3. Check 90-day waiting period
        if (donor.getLastDonationDate() != null) {

            LocalDate nextEligibleDate =
                    donor.getLastDonationDate().plusDays(90);

            if (donationDate.isBefore(nextEligibleDate)) {

                throw new RuntimeException(
                        "Donor is not eligible. " +
                        "Next donation date is: " +
                        nextEligibleDate
                );
            }
        }

        // 4. Create donation record
        DonationRecord record = new DonationRecord();

        record.setDonor(donor);
        record.setDonationDate(donationDate);

        // 5. Update donor
        donor.setLastDonationDate(donationDate);
        donor.setAvailable(false);

        donorRepository.save(donor);

        // 6. Save donation record
        return donationRecordRepository.save(record);
    }

    // Get all donation records
    public List<DonationRecord> getAllDonations() {

        return donationRecordRepository.findAll();
    }

    // Get donation history of a donor
    public List<DonationRecord> getDonorHistory(Long donorId) {

        if (!donorRepository.existsById(donorId)) {

            throw new RuntimeException(
                    "Donor not found with ID: " + donorId
            );
        }

        return donationRecordRepository.findByDonorId(donorId);
    }
}