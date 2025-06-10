package com.hitss.academic_platform.services;

import java.util.List;
import java.util.Optional;

import com.hitss.academic_platform.dto.GradeDto;
import com.hitss.academic_platform.entities.Grade;

public interface GradeService {

	List<Grade> findAll();
	
	Optional<Grade> findById(Long id);
	
	Grade save(GradeDto gradeDto);
	
	Optional<Grade> update(Long id, GradeDto gradeDto);
	
	Optional<Grade> delete(Long id);
	
	List<Grade> findByStudentId(Long studentId);
	
	List<Grade> findBySubjectId(Long subjectId);
	
	List<Grade> findBySchoolPeriodId(Long schoolPeriodId);
}
