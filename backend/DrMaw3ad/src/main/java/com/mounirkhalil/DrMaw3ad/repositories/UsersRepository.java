package com.mounirkhalil.DrMaw3ad.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mounirkhalil.DrMaw3ad.models.databaseModels.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}