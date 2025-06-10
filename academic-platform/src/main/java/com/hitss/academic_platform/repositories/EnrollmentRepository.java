package com.hitss.academic_platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.academic_platform.entities.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{

}
