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
import com.hitss.academic_platform.dto.TeacherDto;
import com.hitss.academic_platform.entities.Teacher;
import com.hitss.academic_platform.services.TeacherService;

import jakarta.validation.Valid;

@RequestMapping("/api/teachers")
@RestController
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	

	@Autowired
	private UserValidation userValidation;
	
	
	
	@GetMapping("/listTeachers")
	private List<Teacher> list(){
		
		return teacherService.findAll();
	}
	
	@GetMapping("/{id}/getTeacherById")
	private ResponseEntity<?> getTeacherById(@PathVariable Long id){
		Optional<Teacher> userOpt = teacherService.findById(id);
		
		if(userOpt.isPresent()) return ResponseEntity.ok(userOpt.orElseThrow());
		
		return ResponseEntity.notFound().build();
		
	}	
	
	@PostMapping("/saveTeacher")
	private ResponseEntity<?> saveTeacher(@Valid @RequestBody TeacherDto teacherDto, 
			BindingResult result){
		try {
//			userValidation.validate(teacherDto.getUserDto(), result);
//			if(result.hasFieldErrors())
//				return validation(result);
			return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.save(teacherDto));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{teacher_id}")
	private ResponseEntity<?> update(@PathVariable Long teacher_id, @RequestBody TeacherDto user){
		
		try {
			
			return ResponseEntity.ok(teacherService.update(teacher_id, user));
			
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
