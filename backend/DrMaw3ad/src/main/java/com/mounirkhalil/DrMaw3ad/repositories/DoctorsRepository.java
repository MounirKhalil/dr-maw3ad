package com.mounirkhalil.DrMaw3ad.repositories;

import com.mounirkhalil.DrMaw3ad.models.databaseModels.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorsRepository extends CrudRepository<Doctor, Integer> {
}
