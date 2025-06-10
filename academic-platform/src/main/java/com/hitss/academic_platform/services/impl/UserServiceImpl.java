package com.hitss.academic_platform.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitss.academic_platform.dto.UserDto;
import com.hitss.academic_platform.entities.Role;
import com.hitss.academic_platform.entities.User;
import com.hitss.academic_platform.repositories.RoleRepository;
import com.hitss.academic_platform.repositories.UserRepository;
import com.hitss.academic_platform.services.UserService;



@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<User> findById(Long id) {

		return userRepository.findById(id);
	}

	@Transactional
	@Override
	public User save(UserDto userDto) {
		
		User user = new User();
		user.setName(userDto.getName());
		user.setFatherLastName(userDto.getFatherLastName());
		user.setMotherLastName(userDto.getMotherLastName());
		user.setBirthDate(userDto.getBirthDate());
		
		if(userRepository.existsByEmail(userDto.getEmail())) throw new IllegalArgumentException("Error: The email is already associated with an existing user.");
		
		if(userRepository.existsByUserName(userDto.getUserName())) throw new IllegalArgumentException("Error: The user name is already associated with an existing user.");

		Optional<Role> role = roleRepository.findByName(userDto.getRoleName());
		
		if(!role.isPresent()) throw new IllegalArgumentException("Error: The role you specified doesn't exist.");
		
		user.setUserName(userDto.getUserName());
		user.setEmail(userDto.getEmail());
		user.setRol(role.get());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public Optional<User> update(Long id, UserDto userDto) {
		
		Optional<User> userOpt = userRepository.findById(id);
		Optional<Role> role = roleRepository.findByName(userDto.getRoleName());
		
		if(!role.isPresent()) throw new IllegalArgumentException("Error: The role you specified doesn't exist.");
		
		if(userOpt.isPresent()) {
			User userBd = userOpt.get();
			
			userBd.setName(userDto.getName());
			
			userBd.setFatherLastName(userDto.getFatherLastName());
			userBd.setMotherLastName(userDto.getMotherLastName());
			userBd.setBirthDate(userDto.getBirthDate());
			userBd.setRol(role.get());
			userBd.setEmail(userDto.getEmail());
			userBd.setUserName(userDto.getUserName());
			userBd.setPassword(userDto.getPassword());
			return Optional.of(userRepository.save(userBd));
		}
		return userOpt;
	}

	@Transactional
	@Override
	public Optional<User> delete(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		userOpt.ifPresent(userRepository::delete);
		return userOpt;
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean existsByUserName(String userName) {
		return userRepository.existsByUserName(userName);
	}
}
