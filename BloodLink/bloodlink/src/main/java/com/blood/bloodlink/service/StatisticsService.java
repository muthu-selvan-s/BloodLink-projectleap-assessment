package com.blood.bloodlink.service;

import com.blood.bloodlink.entity.BloodGroup;
import com.blood.bloodlink.entity.Donor;
import com.blood.bloodlink.repository.BloodGroupRepository;
import com.blood.bloodlink.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final BloodGroupRepository bloodGroupRepository;
    private final DonorRepository donorRepository;

    public StatisticsService(
            BloodGroupRepository bloodGroupRepository,
            DonorRepository donorRepository) {

        this.bloodGroupRepository = bloodGroupRepository;
        this.donorRepository = donorRepository;
    }

    // ==========================================
    // 1. BLOOD GROUP STATISTICS
    // ==========================================

    public Map<String, Long> getBloodGroupStatistics() {

        List<BloodGroup> bloodGroups =
                bloodGroupRepository.findAll();

        List<Donor> donors =
                donorRepository.findAll();

        Map<String, Long> statistics =
                new LinkedHashMap<>();

        for (BloodGroup bloodGroup : bloodGroups) {

            long count = donors.stream()
                    .filter(donor ->
                            donor.getBloodGroup() != null &&
                            donor.getBloodGroup()
                                    .getBloodGroup()
                                    .equalsIgnoreCase(
                                            bloodGroup.getBloodGroup()
                                    )
                    )
                    .filter(this::isCurrentlyAvailable)
                    .count();

            statistics.put(
                    bloodGroup.getBloodGroup(),
                    count
            );
        }

        return statistics;
    }


    // ==========================================
    // 2. TOTAL DONOR STATISTICS
    // ==========================================

    public Map<String, Long> getSummaryStatistics() {

        List<Donor> donors =
                donorRepository.findAll();

        long totalDonors = donors.size();

        long availableDonors = donors.stream()
                .filter(this::isCurrentlyAvailable)
                .count();

        long unavailableDonors =
                totalDonors - availableDonors;

        Map<String, Long> summary =
                new LinkedHashMap<>();

        summary.put(
                "totalDonors",
                totalDonors
        );

        summary.put(
                "availableDonors",
                availableDonors
        );

        summary.put(
                "unavailableDonors",
                unavailableDonors
        );

        return summary;
    }


    // ==========================================
    // 3. CITY-WISE STATISTICS
    // ==========================================

    public Map<String, Long> getCityStatistics() {

        List<Donor> donors =
                donorRepository.findAll();

        Map<String, Long> statistics =
                new LinkedHashMap<>();

        for (Donor donor : donors) {

            if (isCurrentlyAvailable(donor)) {

                String city = donor.getCity();

                statistics.put(
                        city,
                        statistics.getOrDefault(city, 0L) + 1
                );
            }
        }

        return statistics;
    }

    // ==========================================
// 4. COMPLETE DASHBOARD STATISTICS
// ==========================================

public Map<String, Object> getDashboardStatistics() {

    Map<String, Object> dashboard =
            new LinkedHashMap<>();

    // Summary
    dashboard.put(
            "summary",
            getSummaryStatistics()
    );

    // Blood group statistics
    dashboard.put(
            "bloodGroups",
            getBloodGroupStatistics()
    );

    // City statistics
    dashboard.put(
            "cities",
            getCityStatistics()
    );

    return dashboard;
}


    // ==========================================
    // CHECK DONOR AVAILABILITY
    // ==========================================

    private boolean isCurrentlyAvailable(Donor donor) {

        if (donor.getLastDonationDate() == null) {
            return donor.isAvailable();
        }

        LocalDate availableDate =
                donor.getLastDonationDate().plusDays(90);

        return !LocalDate.now().isBefore(
                availableDate
        );
    }
}