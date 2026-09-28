package com.blood.bloodlink.repository;

import com.blood.bloodlink.entity.DonationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRecordRepository
        extends JpaRepository<DonationRecord, Long> {

    List<DonationRecord> findByDonorId(Long donorId);
}