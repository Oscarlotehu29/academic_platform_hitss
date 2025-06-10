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
import com.hitss.academic_platform.dto.CourseDto;
import com.hitss.academic_platform.entities.Course;
import com.hitss.academic_platform.services.CourseService;

import jakarta.validation.Valid;

@RequestMapping("/api/courses")
@RestController
public class CourseController {
	@Autowired
	private CourseService courseService;
	

	@Autowired
	private UserValidation userValidation;
	
	
	
	@GetMapping("/listCourses")
	private List<Course> list(){
		
		return courseService.findAll();
	}
	
	@GetMapping("/{id}/getCourseById")
	private ResponseEntity<?> getCourseById(@PathVariable Long id){
		Optional<Course> schoolPeriod = courseService.findById(id);
		
		if(schoolPeriod.isPresent()) return ResponseEntity.ok(schoolPeriod.orElseThrow());
		
		return ResponseEntity.notFound().build();
		
	}	
	
	@PostMapping("/saveCourse")
	private ResponseEntity<?> saveCourse(@Valid @RequestBody CourseDto course, 
			BindingResult result){
		try {
//			userValidation.validate(s, result);
//			if(result.hasFieldErrors())
//				return validation(result);
			return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{id}")
	private ResponseEntity<?> update(@PathVariable Long id, @RequestBody CourseDto course){
		
		try {
			
			return ResponseEntity.ok(courseService.update(id, course));
			
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
