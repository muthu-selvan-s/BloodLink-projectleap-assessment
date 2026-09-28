package com.blood.bloodlink.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "blood_requests")
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String patientName;

    @Column(nullable = false)
    private String hospitalName;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    private String bloodGroup;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String urgency;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;


    // ==========================================
    // DEFAULT CONSTRUCTOR
    // ==========================================

    public BloodRequest() {

        this.requestDate = LocalDate.now();

        this.status = "PENDING";
    }


    // ==========================================
    // GET ID
    // ==========================================

    public Long getId() {
        return id;
    }


    // ==========================================
    // PATIENT NAME
    // ==========================================

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


    // ==========================================
    // HOSPITAL NAME
    // ==========================================

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }


    // ==========================================
    // CONTACT NUMBER
    // ==========================================

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


    // ==========================================
    // BLOOD GROUP
    // ==========================================

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }


    // ==========================================
    // CITY
    // ==========================================

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    // ==========================================
    // URGENCY
    // ==========================================

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }


    // ==========================================
    // STATUS
    // ==========================================

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // ==========================================
    // REQUEST DATE
    // ==========================================

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
}