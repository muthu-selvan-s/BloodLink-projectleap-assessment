package com.blood.bloodlink.repository;

import com.blood.bloodlink.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    List<Donor> findByCityIgnoreCase(String city);

    List<Donor> findByBloodGroup_BloodGroupIgnoreCase(String bloodGroup);

    List<Donor> findByBloodGroup_BloodGroupIgnoreCaseAndCityIgnoreCase(
            String bloodGroup,
            String city
    );
}