package com.hitss.academic_platform.dto;

import java.math.BigDecimal;

import com.hitss.academic_platform.entities.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCourseDto {
	
	private BigDecimal averageCourse;
	private Course course; 
}
