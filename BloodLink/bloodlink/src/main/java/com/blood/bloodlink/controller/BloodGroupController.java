package com.blood.bloodlink.controller;

import com.blood.bloodlink.entity.BloodGroup;
import com.blood.bloodlink.service.BloodGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blood-groups")
public class BloodGroupController {

    private final BloodGroupService bloodGroupService;

    public BloodGroupController(
            BloodGroupService bloodGroupService) {

        this.bloodGroupService = bloodGroupService;
    }

    @GetMapping
    public ResponseEntity<List<BloodGroup>> getAllBloodGroups() {

        return ResponseEntity.ok(
                bloodGroupService.getAllBloodGroups()
        );
    }
}