package com.mounirkhalil.DrMaw3ad.services.registration;

import com.mounirkhalil.DrMaw3ad.models.databaseModels.Patient;
import com.mounirkhalil.DrMaw3ad.models.authRequestsModels.PatientRegistrationRequest;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.User;
import com.mounirkhalil.DrMaw3ad.repositories.PatientsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.Map;

@Service
public class PatientRegistrationService {
    private final UserRegistrationService userRegistrationService;
    private final PatientsRepository patientsRepository;

    public PatientRegistrationService(UserRegistrationService userRegistrationService, PatientsRepository patientsRepository) {
        this.userRegistrationService = userRegistrationService;
        this.patientsRepository = patientsRepository;
    }

    @Transactional
    public Map<String, Object> registerPatient(PatientRegistrationRequest request) throws RuntimeException {
        try {
            // Create a new User
            // Create a new User and save it
            User user = userRegistrationService.registerUser(request, User.UserType.PATIENT);

            Patient patient = new Patient();
            patient.setUser(user);
            patient.setAddress(request.getAddress());
            Patient savedPatient = patientsRepository.save(patient);
            String jwtToken = "thisIsAToken";

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtToken);

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to register patient", e);
        }
    }

}
