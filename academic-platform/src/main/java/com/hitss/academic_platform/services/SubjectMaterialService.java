package com.hitss.academic_platform.services;

import java.util.List;
import java.util.Optional;

import com.hitss.academic_platform.dto.SubjectMaterialDto;
import com.hitss.academic_platform.entities.SubjectMaterial;

public interface SubjectMaterialService {
	List<SubjectMaterial> findAll();
	
	Optional<SubjectMaterial> findById(Long id);
	
	SubjectMaterial save(SubjectMaterialDto subjectMaterialDto);
	
	Optional<SubjectMaterial> update(Long id, SubjectMaterialDto subjectMaterialDto);
	
	Optional<SubjectMaterial> delete(Long id);
	
	List<SubjectMaterial> findByTeacherId(Long teacherId);
	
	List<SubjectMaterial> findBySubjectId(Long subjectId);
}
