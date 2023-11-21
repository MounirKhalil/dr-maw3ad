package com.mounirkhalil.DrMaw3ad.repositories;

import com.mounirkhalil.DrMaw3ad.models.databaseModels.Appointment;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.Doctor;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentsRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByDoctorAndApprovedTrue(Doctor doctor);
    List<Appointment> findByDoctorAndApprovedFalse(Doctor doctor);
    List<Appointment> findByPatientAndApprovedTrue(Patient patient);
    List<Appointment> findByPatientAndApprovedFalse(Patient patient);
}
