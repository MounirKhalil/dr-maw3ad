package com.mounirkhalil.DrMaw3ad.services.registration;


import com.mounirkhalil.DrMaw3ad.models.databaseModels.Admin;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.User;
import com.mounirkhalil.DrMaw3ad.repositories.AdminsRepository;
import com.mounirkhalil.DrMaw3ad.models.authRequestsModels.AdminRegistrationRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminRegistrationService {
    private final UserRegistrationService userRegistrationService;
    private final AdminsRepository adminsRepository;


    public AdminRegistrationService(UserRegistrationService userRegistrationService, AdminsRepository adminsRepository) {
        this.userRegistrationService = userRegistrationService;
        this.adminsRepository = adminsRepository;

    }

    @Transactional
    public Admin registerAdmin(AdminRegistrationRequest request) throws RuntimeException {
        try {
            // Create a new User and save it
            User user = userRegistrationService.registerUser(request, User.UserType.ADMIN);

            Admin admin = new Admin();
            admin.setUser(user);
            admin.setRootAdmin(request.getRootAdmin());
            return adminsRepository.save(admin);
        } catch (Exception e) {

            throw new RuntimeException("Failed to register admin", e);

        }
    }

}
