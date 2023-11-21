package com.mounirkhalil.DrMaw3ad.models.databaseModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mounirkhalil.DrMaw3ad.models.frontendFetchModels.UserDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @Column(name = "doctor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "specialty")
    private String specialty;

    @JsonIgnore
    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL)
    private GenWhpd genWhpd;


    @Column(name = "gen_app_dur")
    private Integer genAppDur;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    @JsonManagedReference
    @Transient  //  to ignore it from DB mapping
    private UserDTO userDTO;


    public Integer getDoctorId() {
        return doctorId;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public User getUser() {
        return user;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public GenWhpd getGenWhpd() {
        return genWhpd;
    }

    public void setGenWhpd(GenWhpd genWhpd) {
        this.genWhpd = genWhpd;
    }


    public Integer getGenAppDur() {
        return genAppDur;
    }

    public void setGenAppDur(Integer genAppDur) {
        this.genAppDur = genAppDur;
    }


    @JsonIgnore
    public List<Appointment> getApprovedAppointments() {
        return appointments.stream().filter(Appointment::isApproved).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Appointment> getPendingAppointments() {
        return appointments.stream().filter(appointment -> !appointment.isApproved()).collect(Collectors.toList());
    }


    public void setUserDTO(UserDTO userDTO){
        this.userDTO = userDTO;
    }
}

