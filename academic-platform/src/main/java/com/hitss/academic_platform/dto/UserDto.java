package com.hitss.academic_platform.dto;

import java.time.LocalDate;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private String name;
	
	private String fatherLastName;
	
	private String motherLastName;
	
	private String userName;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	private String email;
		
	private String roleName;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
}
