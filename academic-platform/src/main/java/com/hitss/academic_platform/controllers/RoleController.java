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
import com.hitss.academic_platform.entities.Role;
import com.hitss.academic_platform.services.RoleService;

import jakarta.validation.Valid;

@RequestMapping("/api/roles")
@RestController
public class RoleController {
	
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/listRoles")
	private List<Role> list(){
		
		return roleService.findAll();
	}
	
	@GetMapping("/{id}/getRoleById")
	private ResponseEntity<?> getRoleById(@PathVariable Long id){
		Optional<Role> roleOpt = roleService.findById(id);
		
		if(roleOpt.isPresent()) return ResponseEntity.ok(roleOpt.orElseThrow());
		
		return ResponseEntity.notFound().build();
		
	}	
	
	@PostMapping("/saveRole")
	private ResponseEntity<?> saveRole(@Valid @RequestBody Role role, 
			BindingResult result){
		try {
//			userValidation.validate(teacherDto.getUserDto(), result);
//			if(result.hasFieldErrors())
//				return validation(result);
			return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(role));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{id}")
	private ResponseEntity<?> update(@PathVariable Long id, @RequestBody Role role){
		
		try {
			
			return ResponseEntity.ok(roleService.update(id, role));
			
		} catch (IllegalArgumentException e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<?> delete(@PathVariable Long id){
		
		try {
			return ResponseEntity.ok(roleService.delete(id).orElseThrow());	
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
}
