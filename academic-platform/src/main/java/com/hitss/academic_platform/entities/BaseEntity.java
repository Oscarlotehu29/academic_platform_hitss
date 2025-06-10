package com.hitss.academic_platform.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "id_user_created")
	private Long idUserCreated;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "id_user_updated")
	private Long idUserUpdated;
	
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@CreatedDate
	@Column(name="create_at")
	private LocalDate createAt;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@LastModifiedDate
	@Column(name="update_at")
	private LocalDate updateAt;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name="is_active")
	private Integer isActive = 1;
}
