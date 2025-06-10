package com.hitss.academic_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

	private String name;
	private Long teacherId;
	private Long courseId;
	
}
