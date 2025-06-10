package com.hitss.academic_platform.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitss.academic_platform.entities.SchoolPeriod;
import com.hitss.academic_platform.repositories.SchoolPeriodRepository;
import com.hitss.academic_platform.services.SchoolPeriodService;

@Service
public class SchoolPeriodsImp implements SchoolPeriodService{

	@Autowired
	private SchoolPeriodRepository schoolPeriodRepository;
	
	@Override
	public List<SchoolPeriod> findAll() {
		
		return schoolPeriodRepository.findAll();
	}

	@Override
	public Optional<SchoolPeriod> findById(Long id) {
		
		return schoolPeriodRepository.findById(id);
	}

	@Override
	public SchoolPeriod save(SchoolPeriod schoolPeriod) {
		
		return schoolPeriodRepository.save(schoolPeriod);
	}

	@Override
	public Optional<SchoolPeriod> update(Long id, SchoolPeriod schoolPeriod) {
		
		Optional<SchoolPeriod> schPerioOpt = schoolPeriodRepository.findById(id);
		if(schPerioOpt.isPresent()) {
			schoolPeriodRepository.save(schoolPeriod);
		}
		return Optional.empty();
	}

	@Override
	public Optional<SchoolPeriod> delete(Long id) {
		Optional<SchoolPeriod> schPerioOpt = schoolPeriodRepository.findById(id);
		schPerioOpt.ifPresent(schoolPeriodRepository::delete);
		return schPerioOpt;
	}

}
