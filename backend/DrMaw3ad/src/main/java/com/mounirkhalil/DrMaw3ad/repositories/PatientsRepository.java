package com.mounirkhalil.DrMaw3ad.repositories;

import com.mounirkhalil.DrMaw3ad.models.databaseModels.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientsRepository extends CrudRepository<Patient, Integer> {

}