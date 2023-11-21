package com.mounirkhalil.DrMaw3ad.services;

import com.mounirkhalil.DrMaw3ad.models.databaseModels.Appointment;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.Doctor;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.Patient;
import com.mounirkhalil.DrMaw3ad.repositories.AppointmentsRepository;
import com.mounirkhalil.DrMaw3ad.repositories.DoctorsRepository;
import com.mounirkhalil.DrMaw3ad.repositories.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentsServices {

    private final AppointmentsRepository appointmentsRepository;
    private final DoctorsRepository doctorsRepository;
    private final PatientsRepository patientsRepository;

    @Autowired
    public AppointmentsServices(AppointmentsRepository appointmentsRepository, DoctorsRepository doctorsRepository, PatientsRepository patientsRepository) {
        this.appointmentsRepository = appointmentsRepository;
        this.doctorsRepository = doctorsRepository;
        this.patientsRepository = patientsRepository;
    }

    public List<Appointment> getDoctorApprovedAppointments(Integer doctorId) {
        Doctor doctor = doctorsRepository.findById(doctorId).orElseThrow();
        return appointmentsRepository.findByDoctorAndApprovedTrue(doctor);
    }

    public List<Appointment> getDoctorPendingAppointments(Integer doctorId) {
        Doctor doctor = doctorsRepository.findById(doctorId).orElseThrow();
        return appointmentsRepository.findByDoctorAndApprovedFalse(doctor);
    }

    public List<Appointment> getPatientApprovedAppointments(Integer patientId) {
        Patient patient = patientsRepository.findById(patientId).orElseThrow();
        return appointmentsRepository.findByPatientAndApprovedTrue(patient);
    }

    public List<Appointment> getPatientPendingAppointments(Integer patientId) {
        Patient patient = patientsRepository.findById(patientId).orElseThrow();
        return appointmentsRepository.findByPatientAndApprovedFalse(patient);
    }

    public void addAppointment(Appointment appointment) {
        appointmentsRepository.save(appointment);
    }

    public void deleteAppointment(Integer id) {
        appointmentsRepository.deleteById(id);
    }

    public void approveAppointment(Integer id) {
        Appointment appointment = appointmentsRepository.findById(id).orElseThrow();
        appointment.setApproved(true);
        appointmentsRepository.save(appointment);
    }
}
