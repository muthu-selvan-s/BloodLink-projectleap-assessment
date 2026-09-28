package com.blood.bloodlink.repository;

import com.blood.bloodlink.entity.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloodRequestRepository
        extends JpaRepository<BloodRequest, Long> {


    // ========================================
    // FIND REQUESTS BY STATUS
    // ========================================

    List<BloodRequest> findByStatusIgnoreCase(
            String status
    );


    // ========================================
    // FIND REQUESTS BY BLOOD GROUP
    // ========================================

    List<BloodRequest> findByBloodGroupIgnoreCase(
            String bloodGroup
    );


    // ========================================
    // FIND REQUESTS BY CITY
    // ========================================

    List<BloodRequest> findByCityIgnoreCase(
            String city
    );


    // ========================================
    // FIND BY BLOOD GROUP + CITY
    // ========================================

    List<BloodRequest>
    findByBloodGroupIgnoreCaseAndCityIgnoreCase(
            String bloodGroup,
            String city
    );
}