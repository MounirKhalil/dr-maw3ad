package com.mounirkhalil.DrMaw3ad.models.databaseModels;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @Column(name = "patient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "address")
    private String address;


    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;


    public Integer getPatientId() {
        return patientId;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Appointment> getApprovedAppointments() {
        return appointments.stream().filter(Appointment::isApproved).collect(Collectors.toList());
    }

    public List<Appointment> getPendingAppointments() {
        return appointments.stream().filter(appointment -> !appointment.isApproved()).collect(Collectors.toList());
    }

}
