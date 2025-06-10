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
import com.hitss.academic_platform.dto.StudentDto;


import com.hitss.academic_platform.entities.Student;

import com.hitss.academic_platform.services.StudentService;

import jakarta.validation.Valid;


@RequestMapping("/api/students")
@RestController
public class StudentController {
	@Autowired
	private StudentService studentService;
	

	@Autowired
	private UserValidation userValidation;
	
	
	@GetMapping("/listStudents")
	private List<Student> list(){
		
		return studentService.findAll();
	}
	
	@GetMapping("/{id}/getStudentById")
	private ResponseEntity<?> getTeacherById(@PathVariable Long id){
		Optional<Student> userOpt = studentService.findById(id);
		
		if(userOpt.isPresent()) return ResponseEntity.ok(userOpt.orElseThrow());
		
		return ResponseEntity.notFound().build();
		
	}	
	
	@PostMapping("/saveStudent")
	private ResponseEntity<?> saveTeacher(@Valid @RequestBody StudentDto studentDto, 
			BindingResult result){
		try {
//			userValidation.validate(studentDto.getUserDto(), result);
			if(result.hasFieldErrors())
				return validation(result);
			return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(studentDto));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{student_id}")
	private ResponseEntity<?> update(@PathVariable Long student_id, @RequestBody StudentDto studentDto){
		
		try {
			
			return ResponseEntity.ok(studentService.update(student_id, studentDto));
			
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
