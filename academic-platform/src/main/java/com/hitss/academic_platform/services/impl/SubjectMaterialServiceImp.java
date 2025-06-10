package com.hitss.academic_platform.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitss.academic_platform.dto.SubjectMaterialDto;
import com.hitss.academic_platform.entities.Subject;
import com.hitss.academic_platform.entities.SubjectMaterial;
import com.hitss.academic_platform.entities.Teacher;
import com.hitss.academic_platform.entities.User;
import com.hitss.academic_platform.repositories.SubjectMaterialRepository;
import com.hitss.academic_platform.repositories.SubjectRepository;
import com.hitss.academic_platform.repositories.TeacherRepository;
import com.hitss.academic_platform.repositories.UserRepository;
import com.hitss.academic_platform.services.SubjectMaterialService;
import com.hitss.academic_platform.services.utils.AuthUtils;


@Service
public class SubjectMaterialServiceImp implements SubjectMaterialService{

	@Autowired
	private SubjectMaterialRepository subjectMaterialRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthUtils authUtils;

	@Override
	public List<SubjectMaterial> findAll() {
		
		return subjectMaterialRepository.findAll();
	}

	@Override
	public Optional<SubjectMaterial> findById(Long id) {
		
		return subjectMaterialRepository.findById(id);
	}

	@Override
	public SubjectMaterial save(SubjectMaterialDto subjectMaterialDto) {
		
		Long id = 0L;
		id = subjectMaterialDto.getTeacherId();
		if(id == 0) {
			String username = authUtils.getUsername();
		    List<String> roles = authUtils.getRoles();
			
			String rol = roles.get(0);
			
			
			if(rol.equals("ROLE_TEACHER")) {
				Optional<User> user =  userRepository.findByUserName(username);
				if(user.isPresent()) id = user.get().getId();
			}
		}
		
		
		Optional<Teacher> teacherOpt = teacherRepository.findByUserId(id);
		
		if(!teacherOpt.isPresent()) throw new IllegalArgumentException("Error: The Teacher with the Id that you specified doesn't exist.");
		
		Optional<Subject> subjectOpt = subjectRepository.findById(subjectMaterialDto.getSubjectId());
		if(!subjectOpt.isPresent()) throw new IllegalArgumentException("Error: The Subject with the Id that you specified doesn't exist.");
		
		SubjectMaterial subjectMaterial = new SubjectMaterial();
		
		subjectMaterial.setTitle(subjectMaterialDto.getTitle());
		subjectMaterial.setDescription(subjectMaterialDto.getDescription());
		subjectMaterial.setFileUrl(subjectMaterialDto.getFileUrl());
		subjectMaterial.setSubject(subjectOpt.get());
		subjectMaterial.setTeacher(teacherOpt.get());
		
		return subjectMaterialRepository.save(subjectMaterial);
	}

	@Override
	public Optional<SubjectMaterial> update(Long id, SubjectMaterialDto subjectMaterialDto) {
		Optional<SubjectMaterial> subjMatOpt = subjectMaterialRepository.findById(id);
		
		if(!subjMatOpt.isPresent()) throw new IllegalArgumentException("Error: The Subject material with the Id that you specified doesn't exist.");
		
		Optional<Teacher> teacherOpt = teacherRepository.findById(subjectMaterialDto.getTeacherId());
		if(!teacherOpt.isPresent()) throw new IllegalArgumentException("Error: The Teacher with the Id that you specified doesn't exist.");
		
		Optional<Subject> subjectOpt = subjectRepository.findById(subjectMaterialDto.getSubjectId());
		if(!subjectOpt.isPresent()) throw new IllegalArgumentException("Error: The Subject with the Id that you specified doesn't exist.");
		
		subjMatOpt.get().setTeacher(teacherOpt.get());
		subjMatOpt.get().setSubject(subjectOpt.get());
		subjMatOpt.get().setTitle(subjectMaterialDto.getTitle());
		subjMatOpt.get().setDescription(subjectMaterialDto.getDescription());
		subjMatOpt.get().setFileUrl(subjectMaterialDto.getFileUrl());
		
		
		return Optional.of(subjectMaterialRepository.save(subjMatOpt.get()));
	}

	@Override
	public Optional<SubjectMaterial> delete(Long id) {
		Optional<SubjectMaterial> subjMatOpt = subjectMaterialRepository.findById(id);
		subjMatOpt.ifPresent(subjectMaterialRepository::delete);
		return subjMatOpt;
	}

	@Override
	public List<SubjectMaterial> findByTeacherId(Long teacherId) {

	    String username = authUtils.getUsername();
	    List<String> roles = authUtils.getRoles();
		
		String rol = roles.get(0);
		
		Long id = 0L;
		
		id = teacherId;
		
		if(rol.equals("ROLE_TEACHER")) {
			Optional<User> user =  userRepository.findByUserName(username);
			if(user.isPresent()) id = user.get().getId();
		}
		
		
		return subjectMaterialRepository.findByTeacherId(id);
	}

	@Override
	public List<SubjectMaterial> findBySubjectId(Long subjectId) {
		
		return subjectMaterialRepository.findBySubjectId(subjectId);
	}

}
