package com.hitss.academic_platform.services;

import java.util.List;
import java.util.Optional;


import com.hitss.academic_platform.entities.SchoolPeriod;

public interface SchoolPeriodService {
List<SchoolPeriod> findAll();
	
	Optional<SchoolPeriod> findById(Long id);
	
	SchoolPeriod save(SchoolPeriod schoolPeriod);
	
	Optional<SchoolPeriod> update(Long id, SchoolPeriod schoolPeriod);
	
	Optional<SchoolPeriod> delete(Long id);
}
