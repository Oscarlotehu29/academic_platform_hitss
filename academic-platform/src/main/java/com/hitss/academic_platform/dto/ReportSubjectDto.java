package com.hitss.academic_platform.dto;

import com.hitss.academic_platform.entities.Subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportSubjectDto {
	
	private float averageSubject;
	
	private Subject subject;
}
