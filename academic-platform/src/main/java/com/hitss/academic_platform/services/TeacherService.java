package com.hitss.academic_platform.services;

import java.util.List;
import java.util.Optional;

import com.hitss.academic_platform.dto.TeacherDto;
import com.hitss.academic_platform.entities.Teacher;

public interface TeacherService {
	List<Teacher> findAll();
	
	Optional<Teacher> findById(Long id);
	
	Teacher save(TeacherDto teacherDto);
	
	Optional<Teacher> update(Long id, TeacherDto teacherDto);
	
	Optional<Teacher> delete(Long id);
	
}
