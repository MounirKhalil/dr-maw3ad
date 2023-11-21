package com.mounirkhalil.DrMaw3ad.models.databaseModels;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "gen_WHpD")
public class GenWhpd {
    @Id
    @Column(name = "doctor_id")
    private Integer doctorId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @Column(columnDefinition = "TIME")
    private LocalTime moFromTime;
    @Column(columnDefinition = "TIME")
    private LocalTime moToTime;
    @Column(columnDefinition = "TIME")
    private LocalTime tuFromTime;
    @Column(columnDefinition = "TIME")
    private LocalTime tuToTime;
    @Column(columnDefinition = "TIME")
    private LocalTime weFromTime;
    @Column(columnDefinition = "TIME")
    private LocalTime weToTime;
    @Column(columnDefinition = "TIME")
    private LocalTime thFromTime;
    @Column(columnDefinition = "TIME")
    private LocalTime thToTime;
    @Column(columnDefinition = "TIME")
    private LocalTime frFromTime;
    @Column(columnDefinition = "TIME")
    private LocalTime frToTime;

    public LocalTime getMoFromTime() {
        return moFromTime;
    }

    public void setMoFromTime(LocalTime moFromTime) {
        this.moFromTime = moFromTime;
    }

    public LocalTime getMoToTime() {
        return moToTime;
    }

    public void setMoToTime(LocalTime moToTime) {
        this.moToTime = moToTime;
    }

    public LocalTime getTuFromTime() {
        return tuFromTime;
    }

    public void setTuFromTime(LocalTime tuFromTime) {
        this.tuFromTime = tuFromTime;
    }

    public LocalTime getTuToTime() {
        return tuToTime;
    }

    public void setTuToTime(LocalTime tuToTime) {
        this.tuToTime = tuToTime;
    }

    public LocalTime getWeFromTime() {
        return weFromTime;
    }

    public void setWeFromTime(LocalTime weFromTime) {
        this.weFromTime = weFromTime;
    }

    public LocalTime getWeToTime() {
        return weToTime;
    }

    public void setWeToTime(LocalTime weToTime) {
        this.weToTime = weToTime;
    }

    public LocalTime getThFromTime() {
        return thFromTime;
    }

    public void setThFromTime(LocalTime thFromTime) {
        this.thFromTime = thFromTime;
    }

    public LocalTime getThToTime() {
        return thToTime;
    }

    public void setThToTime(LocalTime thToTime) {
        this.thToTime = thToTime;
    }

    public LocalTime getFrFromTime() {
        return frFromTime;
    }

    public void setFrFromTime(LocalTime frFromTime) {
        this.frFromTime = frFromTime;
    }

    public LocalTime getFrToTime() {
        return frToTime;
    }

    public void setFrToTime(LocalTime frToTime) {
        this.frToTime = frToTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
