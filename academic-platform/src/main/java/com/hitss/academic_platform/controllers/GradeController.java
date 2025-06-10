package com.hitss.academic_platform.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitss.academic_platform.dto.GradeDto;
import com.hitss.academic_platform.entities.Grade;
import com.hitss.academic_platform.services.GradeService;

import jakarta.validation.Valid;

@RequestMapping("/api/grades")
@RestController
public class GradeController {

	@Autowired
	private GradeService gradeService;
	
	@GetMapping("/{id}/getGradeById")
	private ResponseEntity<?> getGradeById(@PathVariable Long id){
		Optional<Grade> gradeOpt = gradeService.findById(id);
		
		if(gradeOpt.isPresent()) return ResponseEntity.ok(gradeOpt.orElseThrow());
		
		return ResponseEntity.notFound().build();
		
	}	
	
	@PostMapping("/saveGrade")
	private ResponseEntity<?> saveGrade(@Valid @RequestBody GradeDto grade, 
			BindingResult result){
		try {
//			userValidation.validate(teacherDto.getUserDto(), result);
//			if(result.hasFieldErrors())
//				return validation(result);
			return ResponseEntity.status(HttpStatus.CREATED).body(gradeService.save(grade));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{id}")
	private ResponseEntity<?> update(@PathVariable Long id, @RequestBody GradeDto grade){
		
		try {
			
			return ResponseEntity.ok(gradeService.update(id, grade));
			
		} catch (IllegalArgumentException e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<?> delete(@PathVariable Long id){
		
		try {
			return ResponseEntity.ok(gradeService.delete(id).orElseThrow());	
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
	@GetMapping("/{id}/getGradeByStudentId")
	private List<Grade> getGradesByIdStudents(@PathVariable Long id){
		
		return gradeService.findByStudentId(id);
	}	
	
	@GetMapping("/{id}/getGradeBySubjectId")
	private List<Grade> getGradesBySubjectId(@PathVariable Long id){
		
		return gradeService.findBySubjectId(id);
	}	
	
}
