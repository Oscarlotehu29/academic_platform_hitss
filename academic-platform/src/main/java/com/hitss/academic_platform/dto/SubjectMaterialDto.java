package com.hitss.academic_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectMaterialDto {
	
	private String title;
	
	private String description;
	
	private String fileUrl;
	
	private Long subjectId;
	
	private Long teacherId;
	
}
