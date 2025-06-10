package com.hitss.academic_platform.dto;

import java.math.BigDecimal;

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
	
	private BigDecimal averageSubject;
	
	private Subject subject;
}
