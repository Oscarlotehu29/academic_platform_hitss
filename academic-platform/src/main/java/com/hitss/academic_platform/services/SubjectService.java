package com.hitss.academic_platform.services;

import java.util.List;
import java.util.Optional;

import com.hitss.academic_platform.dto.SubjectDto;
import com.hitss.academic_platform.entities.Subject;

public interface SubjectService {
	List<Subject> findAll();
	
	Optional<Subject> findById(Long id);
	
	Subject save(SubjectDto subjectDto);
	
	Optional<Subject> update(Long id, SubjectDto subjectDto);
	
	Optional<Subject> delete(Long id);
	
	List<Subject> findByTeacherId(Long teacherId);
	
	List<Subject> findByCourseId(Long courseId);
}
