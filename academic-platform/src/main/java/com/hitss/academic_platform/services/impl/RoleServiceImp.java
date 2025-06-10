package com.hitss.academic_platform.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitss.academic_platform.entities.Role;
import com.hitss.academic_platform.repositories.RoleRepository;
import com.hitss.academic_platform.services.RoleService;


@Service
public class RoleServiceImp implements RoleService{

	
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Optional<Role> findById(Long id) {
		
		return roleRepository.findById(id);
	}

	@Override
	public Role save(Role role) {
		Optional<Role> rolOpt = roleRepository.findByName(role.getName());
		
		if(rolOpt.isPresent()) throw new IllegalArgumentException("Error: The email is already associated with an existing user.");
		return roleRepository.save(role);
	}

	@Override
	public Optional<Role> update(Long id, Role role) {
		
		Optional<Role> rolOpt = roleRepository.findById(id);
		
		if(rolOpt.isPresent()) roleRepository.save(role);
		return rolOpt;
	}

	@Override
	public Optional<Role> delete(Long id) {
		
		Optional<Role> rolOpt = roleRepository.findById(id);
		if(rolOpt.isPresent()) {
			rolOpt.get().setIsActive(0);
			roleRepository.save(rolOpt.get());
			return rolOpt;
		}
		throw new IllegalArgumentException("Error: The user that you specified doesn't exist.");
	}

}
