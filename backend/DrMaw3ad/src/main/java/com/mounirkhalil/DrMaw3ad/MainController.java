package com.mounirkhalil.DrMaw3ad;

import com.mounirkhalil.DrMaw3ad.models.authRequestsModels.AdminRegistrationRequest;
import com.mounirkhalil.DrMaw3ad.models.authRequestsModels.DoctorRegistrationRequest;
import com.mounirkhalil.DrMaw3ad.models.authRequestsModels.PatientRegistrationRequest;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.Appointment;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.Doctor;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.GenWhpd;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.User;
import com.mounirkhalil.DrMaw3ad.models.frontendFetchModels.UserDTO;
import com.mounirkhalil.DrMaw3ad.repositories.DoctorsRepository;
import com.mounirkhalil.DrMaw3ad.repositories.GenWhpdRepository;
import com.mounirkhalil.DrMaw3ad.services.registration.AdminRegistrationService;
import com.mounirkhalil.DrMaw3ad.services.registration.PatientRegistrationService;
import com.mounirkhalil.DrMaw3ad.services.registration.DoctorRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping(path = "/api")
public class MainController {
    private final PatientRegistrationService patientRegistrationService;
    private final DoctorRegistrationService doctorRegistrationService;

    private final AdminRegistrationService adminRegistrationService;

    private final DoctorsRepository doctorsRepository;
    @Autowired
    private GenWhpdRepository genWhpdRepository;


    public MainController(PatientRegistrationService patientRegistrationService, DoctorRegistrationService doctorRegistrationService, AdminRegistrationService adminRegistrationService, DoctorsRepository doctorsRepository) {
        this.patientRegistrationService = patientRegistrationService;
        this.doctorRegistrationService = doctorRegistrationService;
        this.adminRegistrationService = adminRegistrationService;
        this.doctorsRepository = doctorsRepository;
    }

    @PostMapping(path = "/signupaspatient")
    public @ResponseBody ResponseEntity<Map<String, Object>> addNewPatient(@RequestBody PatientRegistrationRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            patientRegistrationService.registerPatient(request);
            response.put("success", true);
            response.put("message", "Patient registration successful!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Patient registration failed!");
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }


        @PostMapping(path = "/createdoctoraccount")
    public @ResponseBody ResponseEntity<Map<String, Object>> addNewDoctor(@RequestBody DoctorRegistrationRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            doctorRegistrationService.registerDoctor(request);
            response.put("success", true);
            response.put("message", "Doctor registration successful!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Doctor registration failed!");
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping(path = "/createadminaccount")
    public @ResponseBody ResponseEntity<Map<String, Object>> addNewAdmin(@RequestBody AdminRegistrationRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            adminRegistrationService.registerAdmin(request);
            response.put("success", true);
            response.put("message", "Admin registration successful!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Admin registration failed!");
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/getalldoctors")
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctorsRepository.findAll().forEach(doctors::add);
        List<Doctor> doctorsWithUserDTO = new ArrayList<>();
        for (Doctor doctor : doctors) {
            UserDTO userDTO = convertUserToUserDTO(doctor.getUser());
            doctor.setUserDTO(userDTO);  // Assuming you added setUserDTO() method to Doctor class
            doctorsWithUserDTO.add(doctor);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctorsWithUserDTO);
        response.put("status", HttpStatus.OK);
        response.put("message", "Doctors fetched successfully.");

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/getdoctordetails/{doctorId}")
    public @ResponseBody ResponseEntity<Map<String, Object>> getDoctorDetails(@PathVariable Integer doctorId) {

        Optional<Doctor> doctor = doctorsRepository.findById(doctorId);

        if (doctor.isPresent()) {
            UserDTO userDTO = convertUserToUserDTO(doctor.get().getUser());
            doctor.get().setUserDTO(userDTO);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("doctor", doctor);
        response.put("status", HttpStatus.OK);
        response.put("message", "Doctors fetched successfully.");

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/getgenwhpd/{doctorId}")
    public @ResponseBody ResponseEntity<Map<String, Object>> getGenWhpd(@PathVariable Integer doctorId) {
        Map<String, Object> response = new HashMap<>();

        Optional<Doctor> doctor = doctorsRepository.findById(doctorId);

        if (doctor.isPresent()) {
            GenWhpd genWhpd = doctor.get().getGenWhpd();
            response.put("genWhpd", genWhpd);
            response.put("status", HttpStatus.OK);
            response.put("message", "Doctor's Whpd fetched successfully.");
        } else {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "Doctor not found.");
        }

        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/getdoctorapprovedappointmentsbydate/{doctorId}/{date}")
    public @ResponseBody ResponseEntity<Map<String, Object>> getDoctorApprovedAppointmentsByDate(@PathVariable Integer doctorId, @PathVariable LocalDate date) {
        Map<String, Object> response = new HashMap<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        Optional<Doctor> doctor = doctorsRepository.findById(doctorId);

        if (doctor.isPresent()) {
            List<Appointment> doctorApprovedAppointments = doctor.get().getApprovedAppointments();

            List<String> formattedAppointments = doctorApprovedAppointments.stream()
                    .filter(appointment -> date.equals(appointment.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
                    .map(appointment -> {
                        LocalTime fromTime = appointment.getFromTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                        LocalTime toTime = appointment.getToTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                        return fromTime.format(timeFormatter) + " to " + toTime.format(timeFormatter);
                    })
                    .collect(Collectors.toList());

            response.put("doctorApprovedAppointments", formattedAppointments);
            response.put("status", HttpStatus.OK);
            response.put("message", "Doctor's approved appointments fetched successfully.");
        } else {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "Doctor not found.");
        }

        return ResponseEntity.ok(response);
    }

    public UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobile(user.getMobile());
        userDTO.setUserType(user.getUserType());
        return userDTO;
    }


}
