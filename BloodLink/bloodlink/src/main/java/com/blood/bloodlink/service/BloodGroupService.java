package com.blood.bloodlink.service;

import com.blood.bloodlink.entity.BloodGroup;
import com.blood.bloodlink.repository.BloodGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodGroupService {

    private final BloodGroupRepository bloodGroupRepository;

    public BloodGroupService(
            BloodGroupRepository bloodGroupRepository) {

        this.bloodGroupRepository = bloodGroupRepository;
    }

    public List<BloodGroup> getAllBloodGroups() {

        return bloodGroupRepository.findAll();
    }
}