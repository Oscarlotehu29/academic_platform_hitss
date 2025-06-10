package com.hitss.academic_platform.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitss.academic_platform.dto.GradeDto;
import com.hitss.academic_platform.entities.Grade;
import com.hitss.academic_platform.entities.SchoolPeriod;
import com.hitss.academic_platform.entities.Student;
import com.hitss.academic_platform.entities.Subject;
import com.hitss.academic_platform.repositories.GradeRepository;
import com.hitss.academic_platform.repositories.SchoolPeriodRepository;
import com.hitss.academic_platform.repositories.StudentRepository;
import com.hitss.academic_platform.repositories.SubjectRepository;
import com.hitss.academic_platform.services.GradeService;

@Service
public class GradeServiceImp implements GradeService{

	@Autowired
	private GradeRepository gradeRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private SchoolPeriodRepository schoolPeriodRepository;
	
	@Override
	public List<Grade> findAll() {
		return gradeRepository.findAll();
	}

	@Override
	public Optional<Grade> findById(Long id) {
		return gradeRepository.findById(id);
	}

	@Override
	public Grade save(GradeDto gradeDto) {
		
		Grade grade = new Grade();
		Optional<Student> studentOpt = studentRepository.findById(gradeDto.getStudentId());
		if(!studentOpt.isPresent()) throw new IllegalArgumentException("Error: The Student with the Id that you specified doesn't exist.");
		
		Optional<Subject> subjectOpt = subjectRepository.findById(gradeDto.getSubjectId());
		if(!subjectOpt.isPresent()) throw new IllegalArgumentException("Error: The Subject with the Id that you specified doesn't exist.");

		Optional<SchoolPeriod> schollPeriodOpt = schoolPeriodRepository.findById(gradeDto.getSchoolPeriodId());
		if(!schollPeriodOpt.isPresent()) throw new IllegalArgumentException("Error: The Subject with the Id that you specified doesn't exist.");

		grade.setComments(gradeDto.getComments());
		grade.setGrade(gradeDto.getGrade());
		grade.setStudent(studentOpt.get());
		grade.setSubject(subjectOpt.get());
		grade.setSchoolPeriod(schollPeriodOpt.get());
		
		return gradeRepository.save(grade);
	}

	@Override
	public Optional<Grade> update(Long id, GradeDto gradeDto) {
		Optional<Grade> gradeOpt = gradeRepository.findById(id);
		
		if(!gradeOpt.isPresent()) throw new IllegalArgumentException("Error: The Grade with the Id that you specified doesn't exist.");
		
		Optional<Student> studentOpt = studentRepository.findById(gradeDto.getStudentId());
		if(!studentOpt.isPresent()) throw new IllegalArgumentException("Error: The Student with the Id that you specified doesn't exist.");
		
		Optional<Subject> subjectOpt = subjectRepository.findById(gradeDto.getSubjectId());
		if(!subjectOpt.isPresent()) throw new IllegalArgumentException("Error: The Subject with the Id that you specified doesn't exist.");

		Optional<SchoolPeriod> schollPeriodOpt = schoolPeriodRepository.findById(gradeDto.getSchoolPeriodId());
		if(!schollPeriodOpt.isPresent()) throw new IllegalArgumentException("Error: The Subject with the Id that you specified doesn't exist.");
		
		gradeOpt.get().setGrade(gradeDto.getGrade());
		gradeOpt.get().setComments(gradeDto.getComments());
		gradeOpt.get().setStudent(studentOpt.get());
		gradeOpt.get().setSubject(subjectOpt.get());
		gradeOpt.get().setSchoolPeriod(schollPeriodOpt.get());
		
		
		return Optional.of(gradeRepository.save(gradeOpt.get()));
	}

	@Override
	public Optional<Grade> delete(Long id) {
		
		Optional<Grade> gradeOpt = gradeRepository.findById(id);
		
		if(!gradeOpt.isPresent()) throw new IllegalArgumentException("Error: The Grade with the Id that you specified doesn't exist.");
		
		gradeOpt.get().setIsActive(0);
		
		
		return Optional.of(gradeRepository.save(gradeOpt.get()));
	}

	@Override
	public List<Grade> findByStudentId(Long studentId) {
		
		return gradeRepository.findByStudentId(studentId);
	}

	@Override
	public List<Grade> findBySubjectId(Long subjectId) {
		
		return gradeRepository.findBySubjectId(subjectId);
	}

	@Override
	public List<Grade> findBySchoolPeriodId(Long schoolPeriodId) {
		
		return gradeRepository.findBySchoolPeriodId(schoolPeriodId);
	}



}
