package com.hitss.academic_platform.services.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.hitss.academic_platform.dto.SubjectDto;
import com.hitss.academic_platform.entities.Course;
import com.hitss.academic_platform.entities.Subject;
import com.hitss.academic_platform.entities.Teacher;
import com.hitss.academic_platform.entities.User;
import com.hitss.academic_platform.repositories.CourseRepository;
import com.hitss.academic_platform.repositories.SubjectRepository;
import com.hitss.academic_platform.repositories.TeacherRepository;
import com.hitss.academic_platform.repositories.UserRepository;
import com.hitss.academic_platform.services.SubjectService;
import com.hitss.academic_platform.services.utils.AuthUtils;

@Service
public class SubjectServiceImp implements SubjectService{

	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private AuthUtils authUtils;
	
	@Override
	public List<Subject> findAll() {
		// TODO Auto-generated method stub
		return subjectRepository.findAll();
	}

	@Override
	public Optional<Subject> findById(Long id) {
		
		return subjectRepository.findById(id);
	}

	@Override
	public Subject save(SubjectDto subjectDto) {
		Subject subject = new Subject();
		Optional<Teacher> teachOpt = teacherRepository.findById(subjectDto.getTeacherId());
		Optional<Course> courseOpt = courseRepository.findById(subjectDto.getCourseId());
		
		if(!teachOpt.isPresent()) throw new IllegalArgumentException("Error: The Teacher with the id that you specified doesn't exist.");
		if(!courseOpt.isPresent()) throw new IllegalArgumentException("Error: The Course with the id that you specified doesn't exist.");

		subject.setName(subjectDto.getName());
		subject.setCourse(courseOpt.get());
		subject.setTeacher(teachOpt.get());
		return subjectRepository.save(subject);
	}

	@Override
	public Optional<Subject> update(Long id, SubjectDto subjectDto) {
		
		Optional<Subject> subOpt = subjectRepository.findById(id);
		Optional<Teacher> teachOpt = teacherRepository.findById(subjectDto.getTeacherId());
		Optional<Course> courseOpt = courseRepository.findById(subjectDto.getCourseId());
		
		if(!teachOpt.isPresent()) throw new IllegalArgumentException("Error: The Teacher with the id that you specified doesn't exist.");
		if(!courseOpt.isPresent()) throw new IllegalArgumentException("Error: The Course with the id that you specified doesn't exist.");
		
		if(!subOpt.isPresent()) throw new IllegalArgumentException("Error: The Subject with the id that you specified doesn't exist.");;
		
		subOpt.get().setName(subjectDto.getName());
		subOpt.get().setCourse(courseOpt.get());
		
		subOpt.get().setTeacher(teachOpt.get());
		return Optional.of(subjectRepository.save(subOpt.get()));
	}

	@Override
	public Optional<Subject> delete(Long id) {
		Optional<Subject> sub = subjectRepository.findById(id);
		sub.ifPresent(subjectRepository::delete);
		return sub;
	}

	@Override
	public List<Subject> findByTeacherId(Long teacherId) {

	    String username = authUtils.getUsername();
	    List<String> roles = authUtils.getRoles();
		
		String rol = roles.get(0);
		
		Long id = 0L;
		
		id = teacherId;
		
		if(rol.equals("ROLE_TEACHER")) {
			Optional<User> user =  userRepository.findByUserName(username);
			if(user.isPresent()) id = user.get().getId();
		}
		
		return subjectRepository.findByTeacherId(id);
	}

	@Override
	public List<Subject> findByCourseId(Long courseId) {
		
		return subjectRepository.findByCourseId(courseId);
	}

}
