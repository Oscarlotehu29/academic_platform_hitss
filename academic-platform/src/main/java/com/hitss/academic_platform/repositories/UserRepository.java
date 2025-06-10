package com.hitss.academic_platform.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.academic_platform.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);
	
	boolean existsByUserName(String userName);
	
	Optional<User> findByUserName(String username);
}
