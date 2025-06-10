package com.hitss.academic_platform.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitss.academic_platform.dto.TeacherDto;
import com.hitss.academic_platform.entities.Teacher;
import com.hitss.academic_platform.entities.User;
import com.hitss.academic_platform.repositories.TeacherRepository;
import com.hitss.academic_platform.services.TeacherService;
import com.hitss.academic_platform.services.UserService;

@Service
public class TeacherImpl implements TeacherService{
	
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<Teacher> findAll() {
		return teacherRepository.findAll();
	}

	@Override
	public Optional<Teacher> findById(Long id) {
		return teacherRepository.findById(id);
	}

	@Override
	public Teacher save(TeacherDto teacherDto) {
		
		Teacher teacher = new Teacher();
		teacher.setSubjectArea(teacherDto.getSubjectArea());
		Optional<User> userOpt = userService.findById(teacherDto.getUserId());
		if(!userOpt.isPresent()) throw new IllegalArgumentException("Error: The Teacher with the id that you specified doesn't exist.");
		
		teacher.setUser(userOpt.get());
		return teacherRepository.save(teacher);
	}

	@Override
	public Optional<Teacher> update(Long id, TeacherDto teacherDto) {
		return Optional.empty();
	}

	@Override
	public Optional<Teacher> delete(Long id) {
		Optional<Teacher> userOpt = teacherRepository.findById(id);
		userOpt.ifPresent(teacherRepository::delete);
		return userOpt;
	}

}
