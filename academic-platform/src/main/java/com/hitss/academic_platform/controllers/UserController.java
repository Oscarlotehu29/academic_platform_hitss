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
import com.hitss.academic_platform.dto.UserDto;
import com.hitss.academic_platform.entities.User;
import com.hitss.academic_platform.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidation userValidation;
	
	@GetMapping("/listUsers")
	private List<User> list(){
		
		return userService.findAll();
	}
	
	@GetMapping("/{id}/getUserById")
	private ResponseEntity<?> getUserById(@PathVariable Long id){
		Optional<User> userOpt = userService.findById(id);
		
		if(userOpt.isPresent()) return ResponseEntity.ok(userOpt.orElseThrow());
		
		return ResponseEntity.notFound().build();
		
	}	
	
	@PostMapping("/saveUser")
	private ResponseEntity<?> saveUser(@Valid @RequestBody UserDto user, 
			BindingResult result){
		try {
			userValidation.validate(user, result);
			if(result.hasFieldErrors())
				return validation(result);
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	@PostMapping("/register")
	private ResponseEntity<?> registerUser(@Valid @RequestBody UserDto user, 
			BindingResult result){
		
		return saveUser(user, result);

	}

	@PutMapping("/{user_id}")
	private ResponseEntity<?> update(@PathVariable Long user_id, @RequestBody UserDto user){
		
		Optional<User> userOpt = userService.findById(user_id);
		
		if(userOpt.isPresent()) return ResponseEntity.ok(userService.update(user_id, user));
		
		return ResponseEntity.notFound().build();
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
