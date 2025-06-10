package com.hitss.academic_platform.services;

import java.util.List;
import java.util.Optional;

import com.hitss.academic_platform.dto.UserDto;
import com.hitss.academic_platform.entities.User;

public interface UserService {

	List<User> findAll();
	
	Optional<User> findById(Long id);
	
	User save(UserDto userDto);
	
	Optional<User> update(Long id, UserDto userDto);
	
	Optional<User> delete(Long id);
	
	boolean existsByEmail(String email);
	
	boolean existsByUserName(String userName);
}
