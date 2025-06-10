package com.hitss.academic_platform.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.academic_platform.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{

	Optional<Teacher> findByUserId(Long userId);
}
