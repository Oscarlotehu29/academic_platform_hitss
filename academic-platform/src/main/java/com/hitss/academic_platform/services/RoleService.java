package com.hitss.academic_platform.services;

import java.util.List;
import java.util.Optional;

import com.hitss.academic_platform.entities.Role;

public interface RoleService {

	List<Role> findAll();
	
	Optional<Role> findById(Long id);
	
	Role save(Role role);
	
	Optional<Role> update(Long id, Role role);
	
	Optional<Role> delete(Long id);
}
