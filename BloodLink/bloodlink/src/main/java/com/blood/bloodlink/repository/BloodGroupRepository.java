package com.blood.bloodlink.repository;

import com.blood.bloodlink.entity.BloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BloodGroupRepository extends JpaRepository<BloodGroup, Long> {

    Optional<BloodGroup> findByBloodGroup(String bloodGroup);
}