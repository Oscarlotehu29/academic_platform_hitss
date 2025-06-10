package com.hitss.academic_platform.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitss.academic_platform.dto.ReportCourseDto;
import com.hitss.academic_platform.dto.ReportSubjectDto;
import com.hitss.academic_platform.entities.Course;
import com.hitss.academic_platform.entities.Grade;
import com.hitss.academic_platform.entities.Subject;
import com.hitss.academic_platform.repositories.CourseRepository;
import com.hitss.academic_platform.repositories.GradeRepository;
import com.hitss.academic_platform.repositories.SubjectRepository;
import com.hitss.academic_platform.services.ReportsService;

@Service
public class ReportServiceImp implements ReportsService{

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private GradeRepository gradeRepository;

	@Override
	public ReportCourseDto getReportCourse(Long id) {
		ReportCourseDto r = new ReportCourseDto();
		Optional<Course> course = courseRepository.findById(id);
		
		if(!course.isPresent()) throw new IllegalArgumentException("Error: The Course with the Id that you specified doesn't exist.");
		
		r.setCourse(course.get());
		
		List<Grade> grades = gradeRepository.findBySubjectCourseId(id);
		
		BigDecimal average = grades.stream()
				.map(Grade::getGrade)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		average = average.divide(BigDecimal.valueOf(grades.size()), 2, RoundingMode.HALF_UP);
		
		r.setAverageCourse(average);
		
		return r;
	}

	@Override
	public ReportSubjectDto getReportSubjectDto(Long id) {
		ReportSubjectDto reportSubjectDto = new ReportSubjectDto();
		
		Optional<Subject> subject = subjectRepository.findById(id);
		
		if(!subject.isPresent()) throw new IllegalArgumentException("Error: The Subject with the Id that you specified doesn't exist.");
		
		reportSubjectDto.setSubject(subject.get());
		List<Grade> grades = gradeRepository.findBySubjectId(id);
		
		BigDecimal average = grades.stream().map(Grade::getGrade).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		average = average.divide(BigDecimal.valueOf(grades.size()), 2, RoundingMode.HALF_UP);
		reportSubjectDto.setAverageSubject(average);
		return reportSubjectDto;
	}
	
	
	
}
