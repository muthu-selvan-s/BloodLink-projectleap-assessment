package com.blood.bloodlink.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "blood_groups")
public class BloodGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bloodGroup;

    public BloodGroup() {
    }

    public BloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Long getId() {
        return id;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}