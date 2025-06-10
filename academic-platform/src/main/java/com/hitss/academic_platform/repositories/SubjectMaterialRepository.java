package com.hitss.academic_platform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.academic_platform.entities.SubjectMaterial;

public interface SubjectMaterialRepository extends JpaRepository<SubjectMaterial, Long>{
	List<SubjectMaterial> findByTeacherId(Long teacherId);
	List<SubjectMaterial> findBySubjectId(Long subjectId);

}
