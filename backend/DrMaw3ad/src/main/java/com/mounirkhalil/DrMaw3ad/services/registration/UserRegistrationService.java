package com.mounirkhalil.DrMaw3ad.services.registration;

import com.mounirkhalil.DrMaw3ad.models.authRequestsModels.UserRegistrationRequest;
import com.mounirkhalil.DrMaw3ad.models.databaseModels.User;
import com.mounirkhalil.DrMaw3ad.repositories.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserRegistrationService(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Transactional
    public User registerUser(UserRegistrationRequest request, User.UserType userType) throws RuntimeException  {
        try {
            // Check if the userType is valid
            if (!User.UserType.isValid(userType)) {
                throw new IllegalArgumentException("Invalid user type");
            }

            // Check if email already used
            if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already in use");
            }

            // Create a new User
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setHashedPassword(passwordEncoder.encode(request.getPassword()));
            user.setMobile(request.getMobile());
            user.setUserType(userType);

            // Save the User
            return usersRepository.save(user);
        }catch(Exception e) {
            throw new RuntimeException("Failed to register user", e);
        }
    }
}

