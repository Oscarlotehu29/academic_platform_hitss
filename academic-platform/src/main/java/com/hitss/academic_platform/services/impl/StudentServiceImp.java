package com.hitss.academic_platform.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitss.academic_platform.dto.StudentDto;
import com.hitss.academic_platform.entities.Student;
import com.hitss.academic_platform.entities.User;
import com.hitss.academic_platform.repositories.StudentRepository;
import com.hitss.academic_platform.services.StudentService;
import com.hitss.academic_platform.services.UserService;

@Service
public class StudentServiceImp implements StudentService{

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<Student> findAll() {
		
		return studentRepository.findAll();
	}

	@Override
	public Optional<Student> findById(Long id) {
		
		return studentRepository.findById(id);
	}

	@Override
	public Student save(StudentDto studentDto) {
		
		Student student = new Student();
		student.setStudentEnrollment(studentDto.getStudentEnrollment());
		Optional<User> userOpt = userService.findById(studentDto.getUserId());
		if(!userOpt.isPresent()) throw new IllegalArgumentException("Error: The user with the id that you specified doesn't exist.");
		student.setUser(userOpt.get());
		return studentRepository.save(student);
	}

	@Override
	public Optional<Student> update(Long id, StudentDto studentDto) {
		
		Optional<Student> stuOpt = studentRepository.findById(id);
		Optional<User> userOpt = userService.findById(studentDto.getUserId());
		
		if(userOpt.isPresent()) {
			Student userBd = stuOpt.get();
			if(!userOpt.isPresent()) {
				throw new IllegalArgumentException("Error: The user that you specified doesn't exist.");
			}
			userBd.setUser(userOpt.get());
			return Optional.of(studentRepository.save(userBd));
		}
		return stuOpt;
	}

	@Override
	public Optional<Student> delete(Long id) {
		Optional<Student> userOpt = studentRepository.findById(id);
		userOpt.ifPresent(studentRepository::delete);
		return userOpt;
	}

}
