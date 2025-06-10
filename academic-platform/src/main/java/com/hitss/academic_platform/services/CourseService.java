package com.hitss.academic_platform.services;

import java.util.List;
import java.util.Optional;

import com.hitss.academic_platform.dto.CourseDto;
import com.hitss.academic_platform.entities.Course;

public interface CourseService {
	List<Course> findAll();
	
	Optional<Course> findById(Long id);
	
	Course save(CourseDto course);
	
	Optional<Course> update(Long id, CourseDto course);
	
	Optional<Course> delete(Long id);
}
