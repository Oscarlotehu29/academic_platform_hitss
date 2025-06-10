package com.hitss.academic_platform.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitss.academic_platform.dto.CourseDto;
import com.hitss.academic_platform.entities.Course;
import com.hitss.academic_platform.repositories.CourseRepository;
import com.hitss.academic_platform.services.CourseService;


@Service
public class CourseServiceImp implements CourseService{

	
	@Autowired
	private CourseRepository courseRepository;
	
	@Override
	public List<Course> findAll() {
		
		return courseRepository.findAll();
	}

	@Override
	public Optional<Course> findById(Long id) {
		
		return courseRepository.findById(id);
	}

	@Override
	public Course save(CourseDto course) {
		Course c = new Course();
		c.setName(course.getName());
		c.setAcademicYear(course.getAcademicYear());
		return courseRepository.save(c);
	}

	@Override
	public Optional<Course> update(Long id, CourseDto course) {
		Optional<Course> courseOpt = courseRepository.findById(id);
		
		if(courseOpt.isPresent()) 
		{
			courseOpt.get().setName(course.getName());
			courseOpt.get().setAcademicYear(course.getAcademicYear());;
			return Optional.of(courseRepository.save(courseOpt.get()));
		}
		
		return courseOpt;
	}

	
	@Override
	public Optional<Course> delete(Long id) {
		Optional<Course> sub = courseRepository.findById(id);
		sub.ifPresent(courseRepository::delete);
		return sub;
	}
	
}
