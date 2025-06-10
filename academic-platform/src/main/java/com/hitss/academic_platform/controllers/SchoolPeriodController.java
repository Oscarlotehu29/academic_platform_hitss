package com.hitss.academic_platform.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitss.academic_platform.Validation.UserValidation;
import com.hitss.academic_platform.entities.SchoolPeriod;
import com.hitss.academic_platform.services.SchoolPeriodService;

import jakarta.validation.Valid;


@RequestMapping("/api/schoolPeriods")
@RestController
public class SchoolPeriodController {
	
	@Autowired
	private SchoolPeriodService schoolPeriodService;
	

	@Autowired
	private UserValidation userValidation;
	
	
	
	@GetMapping("/listSchoolPeriods")
	private List<SchoolPeriod> list(){
		
		return schoolPeriodService.findAll();
	}
	
	@GetMapping("/{id}/getSchoolPeriodById")
	private ResponseEntity<?> getSchoolPeriodById(@PathVariable Long id){
		Optional<SchoolPeriod> schoolPeriod = schoolPeriodService.findById(id);
		
		if(schoolPeriod.isPresent()) return ResponseEntity.ok(schoolPeriod.orElseThrow());
		
		return ResponseEntity.notFound().build();
		
	}	
	
	@PostMapping("/saveSchoolPeriod")
	private ResponseEntity<?> saveSchoolPeriod(@Valid @RequestBody SchoolPeriod schoolPeriod, 
			BindingResult result){
		try {
//			userValidation.validate(s, result);
//			if(result.hasFieldErrors())
//				return validation(result);
			return ResponseEntity.status(HttpStatus.CREATED).body(schoolPeriodService.save(schoolPeriod));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{id}")
	private ResponseEntity<?> update(@PathVariable Long id, @RequestBody SchoolPeriod schoolPeriod){
		
		try {
			
			return ResponseEntity.ok(schoolPeriodService.update(id, schoolPeriod));
			
		} catch (IllegalArgumentException e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	private ResponseEntity<?> validation(BindingResult result){
		Map<String, String> errors = new HashMap<>();
		
		result.getFieldErrors().forEach(
				error -> errors.put(error.getField(), "The field " + error.getField() + " " + error.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}
	
    @InitBinder("user")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidation);
    }
}
