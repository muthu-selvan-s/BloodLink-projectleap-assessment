package com.blood.bloodlink.service;

import com.blood.bloodlink.entity.BloodRequest;
import com.blood.bloodlink.repository.BloodRequestRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BloodRequestService {

    private final BloodRequestRepository bloodRequestRepository;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public BloodRequestService(
            BloodRequestRepository bloodRequestRepository) {

        this.bloodRequestRepository =
                bloodRequestRepository;
    }


    // ==========================================
    // CREATE BLOOD REQUEST
    // ==========================================

    public BloodRequest createRequest(
            BloodRequest bloodRequest) {


        // Set today's date if no date is provided

        if (bloodRequest.getRequestDate() == null) {

            bloodRequest.setRequestDate(
                    LocalDate.now()
            );
        }


        // Set PENDING status if no status is provided

        if (bloodRequest.getStatus() == null ||
                bloodRequest.getStatus().trim().isEmpty()) {

            bloodRequest.setStatus(
                    "PENDING"
            );
        }


        return bloodRequestRepository.save(
                bloodRequest
        );
    }


    // ==========================================
    // GET ALL BLOOD REQUESTS
    // ==========================================

    public List<BloodRequest> getAllRequests() {

        return bloodRequestRepository.findAll();
    }


    // ==========================================
    // GET REQUEST BY ID
    // ==========================================

    public BloodRequest getRequestById(
            Long id) {

        return bloodRequestRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Blood request not found with ID: "
                                        + id
                        )
                );
    }


    // ==========================================
    // GET REQUESTS BY STATUS
    // ==========================================

    public List<BloodRequest> getRequestsByStatus(
            String status) {

        return bloodRequestRepository
                .findByStatusIgnoreCase(
                        status
                );
    }


    // ==========================================
    // GET REQUESTS BY BLOOD GROUP
    // ==========================================

    public List<BloodRequest> getRequestsByBloodGroup(
            String bloodGroup) {

        return bloodRequestRepository
                .findByBloodGroupIgnoreCase(
                        bloodGroup
                );
    }


    // ==========================================
    // GET REQUESTS BY CITY
    // ==========================================

    public List<BloodRequest> getRequestsByCity(
            String city) {

        return bloodRequestRepository
                .findByCityIgnoreCase(
                        city
                );
    }


    // ==========================================
    // SEARCH REQUESTS
    // BLOOD GROUP + CITY
    // ==========================================

    public List<BloodRequest> searchRequests(
            String bloodGroup,
            String city) {

        return bloodRequestRepository
                .findByBloodGroupIgnoreCaseAndCityIgnoreCase(
                        bloodGroup,
                        city
                );
    }


    // ==========================================
    // UPDATE REQUEST STATUS
    // ==========================================

    public BloodRequest updateStatus(
            Long id,
            String status) {

        BloodRequest bloodRequest =
                bloodRequestRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Blood request not found with ID: "
                                                + id
                                )
                        );


        bloodRequest.setStatus(
                status
        );


        return bloodRequestRepository.save(
                bloodRequest
        );
    }


    // ==========================================
    // DELETE REQUEST
    // ==========================================

    public void deleteRequest(
            Long id) {

        if (!bloodRequestRepository.existsById(id)) {

            throw new RuntimeException(
                    "Blood request not found with ID: "
                            + id
            );
        }


        bloodRequestRepository.deleteById(
                id
        );
    }
}