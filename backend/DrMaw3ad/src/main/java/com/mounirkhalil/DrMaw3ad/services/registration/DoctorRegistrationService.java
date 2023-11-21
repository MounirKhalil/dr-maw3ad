package com.mounirkhalil.DrMaw3ad.services.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.Doctor;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.GenWhpd;
import com.mounirkhalil.DrMaw3ad.models.authRequestsModels.DoctorRegistrationRequest;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.User;
import com.mounirkhalil.DrMaw3ad.repositories.DoctorsRepository;
import com.mounirkhalil.DrMaw3ad.repositories.GenWhpdRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class DoctorRegistrationService {
    private final UserRegistrationService userRegistrationService;
    private final DoctorsRepository doctorsRepository;
    private final GenWhpdRepository genWhpdRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public DoctorRegistrationService(UserRegistrationService userRegistrationService, DoctorsRepository doctorsRepository, GenWhpdRepository genWhpdRepository) {
        this.userRegistrationService = userRegistrationService;
        this.doctorsRepository = doctorsRepository;
        this.genWhpdRepository = genWhpdRepository;
    }


    public LocalTime convertToTimeOfMySQL(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
    }


    @Transactional
    public Doctor registerDoctor(DoctorRegistrationRequest request) throws RuntimeException {

        try {
            // Create a new User and save it
            User user = userRegistrationService.registerUser(request, User.UserType.DOCTOR);

            // Create a new Doctor
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setSpecialty(request.getSpecialty());
            doctor.setGenAppDur(request.getGenAppDur());


            String jsonString = request.getGenWhpd();
            Map<String, String> map = objectMapper.readValue(jsonString, Map.class);
            GenWhpd genWhpd = new GenWhpd();
            genWhpd.setMoFromTime(convertToTimeOfMySQL(map.get("moFromTime")));
            genWhpd.setMoToTime(convertToTimeOfMySQL(map.get("moToTime")));
            genWhpd.setTuFromTime(convertToTimeOfMySQL(map.get("tuFromTime")));
            genWhpd.setTuToTime(convertToTimeOfMySQL(map.get("tuToTime")));
            genWhpd.setWeFromTime(convertToTimeOfMySQL(map.get("weFromTime")));
            genWhpd.setWeToTime(convertToTimeOfMySQL(map.get("weToTime")));
            genWhpd.setThFromTime(convertToTimeOfMySQL(map.get("thFromTime")));
            genWhpd.setThToTime(convertToTimeOfMySQL(map.get("thToTime")));
            genWhpd.setFrFromTime(convertToTimeOfMySQL(map.get("frFromTime")));
            genWhpd.setFrToTime(convertToTimeOfMySQL(map.get("frToTime")));
            genWhpd.setDoctor(doctor);
            doctor.setGenWhpd(genWhpd);
            // Save the Doctor
            Doctor savedDoctor = doctorsRepository.save(doctor);
            // Save the GenWHpD
            genWhpdRepository.save(genWhpd);
            return savedDoctor;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to register doctor", e);
        }
    }
}
