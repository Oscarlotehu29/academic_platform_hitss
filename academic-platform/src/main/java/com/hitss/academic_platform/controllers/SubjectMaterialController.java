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

import com.hitss.academic_platform.dto.SubjectMaterialDto;
import com.hitss.academic_platform.entities.SubjectMaterial;
import com.hitss.academic_platform.services.SubjectMaterialService;

import jakarta.validation.Valid;

@RequestMapping("/api/subjects-materials")
@RestController
public class SubjectMaterialController {

	@Autowired
	private SubjectMaterialService subjectMaterialService;
	
	
	@GetMapping("/{id}/getSubjectMaterialById")
	private ResponseEntity<?> getGradeById(@PathVariable Long id){
		Optional<SubjectMaterial> subjOpt = subjectMaterialService.findById(id);
		
		if(subjOpt.isPresent()) return ResponseEntity.ok(subjOpt.orElseThrow());
		
		return ResponseEntity.notFound().build();
		
	}	
	
	@PostMapping("/saveSubjectMaterial")
	private ResponseEntity<?> saveGrade(@Valid @RequestBody SubjectMaterialDto subjectMaterialDto, 
			BindingResult result){
		try {
//			userValidation.validate(teacherDto.getUserDto(), result);
//			if(result.hasFieldErrors())
//				return validation(result);
			return ResponseEntity.status(HttpStatus.CREATED).body(subjectMaterialService.save(subjectMaterialDto));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{id}")
	private ResponseEntity<?> update(@PathVariable Long id, @RequestBody SubjectMaterialDto subjectMaterialDto){
		
		try {
			
			return ResponseEntity.ok(subjectMaterialService.update(id, subjectMaterialDto));
			
		} catch (IllegalArgumentException e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<?> delete(@PathVariable Long id){
		
		try {
			return ResponseEntity.ok(subjectMaterialService.delete(id).orElseThrow());	
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
	@GetMapping("/{id}/getSubjectMaterialByTeacherId")
	private List<SubjectMaterial> getSubjectMaterialByTeacherId(@PathVariable Long id){
		
		return subjectMaterialService.findByTeacherId(id);
	}	
	
	@GetMapping("/{id}/getSubjectMaterialBySubjectId")
	private List<SubjectMaterial> getSubjectMaterialBySubjectId(@PathVariable Long id){
		
		return subjectMaterialService.findBySubjectId(id);
	}	
}
