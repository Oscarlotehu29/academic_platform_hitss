package com.hitss.academic_platform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.academic_platform.entities.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long>{
	List<Grade> findByStudentId(Long studentId);
	List<Grade> findBySubjectId(Long subjectId);
	List<Grade> findBySchoolPeriodId(Long schoolPeriodId);
	List<Grade> findBySubjectCourseId(Long courseId);
}
