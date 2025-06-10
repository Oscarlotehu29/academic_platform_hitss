package com.hitss.academic_platform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.academic_platform.entities.Subject;

public interface SubjectRepository extends JpaRepository <Subject, Long>{
	List<Subject> findByTeacherId(Long teacherId);
	List<Subject> findByCourseId(Long courseId);
}
