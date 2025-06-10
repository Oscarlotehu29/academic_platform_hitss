package com.hitss.academic_platform.services;

import java.util.List;
import java.util.Optional;

import com.hitss.academic_platform.dto.StudentDto;
import com.hitss.academic_platform.entities.Student;

public interface StudentService {
	List<Student> findAll();
	
	Optional<Student> findById(Long id);
	
	Student save(StudentDto studentDto);
	
	Optional<Student> update(Long id, StudentDto studentDto);
	
	Optional<Student> delete(Long id);
}
