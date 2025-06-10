package com.hitss.academic_platform.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.academic_platform.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	boolean existsByName(String name);
	
	Optional<Role> findByName(String name);
}
