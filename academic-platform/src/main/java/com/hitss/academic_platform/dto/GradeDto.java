package com.hitss.academic_platform.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GradeDto {
	
	private String comments;
	
	@Digits(integer = 2, fraction = 1)
	private BigDecimal grade;
	
	private Long studentId;
	
	private Long subjectId;
	
	private Long schoolPeriodId;
}
