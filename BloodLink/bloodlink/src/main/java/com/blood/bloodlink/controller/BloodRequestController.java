package com.blood.bloodlink.controller;

import com.blood.bloodlink.entity.BloodRequest;
import com.blood.bloodlink.service.BloodRequestService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blood-requests")
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public BloodRequestController(
            BloodRequestService bloodRequestService) {

        this.bloodRequestService =
                bloodRequestService;
    }


    // ==========================================
    // CREATE BLOOD REQUEST
    // POST /api/blood-requests
    // ==========================================

    @PostMapping
    public ResponseEntity<BloodRequest> createRequest(
            @RequestBody BloodRequest bloodRequest) {

        BloodRequest savedRequest =
                bloodRequestService.createRequest(
                        bloodRequest
                );


        return new ResponseEntity<>(
                savedRequest,
                HttpStatus.CREATED
        );
    }


    // ==========================================
    // GET ALL REQUESTS
    // GET /api/blood-requests
    // ==========================================

    @GetMapping
    public ResponseEntity<List<BloodRequest>>
    getAllRequests() {

        return ResponseEntity.ok(
                bloodRequestService.getAllRequests()
        );
    }


    // ==========================================
    // GET REQUEST BY ID
    // GET /api/blood-requests/{id}
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<BloodRequest>
    getRequestById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bloodRequestService
                        .getRequestById(id)
        );
    }


    // ==========================================
    // GET BY STATUS
    // GET /api/blood-requests/status/PENDING
    // ==========================================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BloodRequest>>
    getRequestsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                bloodRequestService
                        .getRequestsByStatus(
                                status
                        )
        );
    }


    // ==========================================
    // GET BY BLOOD GROUP
    // GET /api/blood-requests/blood-group/B+
    // ==========================================

    @GetMapping("/blood-group/{bloodGroup}")
    public ResponseEntity<List<BloodRequest>>
    getRequestsByBloodGroup(
            @PathVariable String bloodGroup) {

        return ResponseEntity.ok(
                bloodRequestService
                        .getRequestsByBloodGroup(
                                bloodGroup
                        )
        );
    }


    // ==========================================
    // GET BY CITY
    // GET /api/blood-requests/city/Erode
    // ==========================================

    @GetMapping("/city/{city}")
    public ResponseEntity<List<BloodRequest>>
    getRequestsByCity(
            @PathVariable String city) {

        return ResponseEntity.ok(
                bloodRequestService
                        .getRequestsByCity(
                                city
                        )
        );
    }


    // ==========================================
    // SEARCH
    // GET /api/blood-requests/search
    // ==========================================

    @GetMapping("/search")
    public ResponseEntity<List<BloodRequest>>
    searchRequests(
            @RequestParam String bloodGroup,
            @RequestParam String city) {

        return ResponseEntity.ok(
                bloodRequestService.searchRequests(
                        bloodGroup,
                        city
                )
        );
    }


    // ==========================================
    // UPDATE STATUS
    // PUT /api/blood-requests/{id}/status
    // ==========================================

    @PutMapping("/{id}/status")
    public ResponseEntity<BloodRequest>
    updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        BloodRequest updatedRequest =
                bloodRequestService.updateStatus(
                        id,
                        status
                );


        return ResponseEntity.ok(
                updatedRequest
        );
    }


    // ==========================================
    // DELETE REQUEST
    // DELETE /api/blood-requests/{id}
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteRequest(
            @PathVariable Long id) {

        bloodRequestService.deleteRequest(
                id
        );


        return ResponseEntity.ok(
                "Blood request deleted successfully"
        );
    }
}